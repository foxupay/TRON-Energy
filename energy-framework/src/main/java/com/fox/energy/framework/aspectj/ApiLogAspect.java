package com.fox.energy.framework.aspectj;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fox.energy.common.annotation.APILog;
import com.fox.energy.common.constant.CacheConstants;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.redis.RedisCache;
import com.fox.energy.common.enums.HttpMethod;
import com.fox.energy.common.utils.LocalUserID;
import com.fox.energy.common.utils.ServletUtils;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.common.utils.ip.IpUtils;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.fox.energy.framework.config.ServerConfig;
import com.fox.energy.system.domain.SysOperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Component
@Aspect
@Order(2)
public class ApiLogAspect {
    private static final Logger log = LoggerFactory.getLogger(ApiLogAspect.class);

    private static final String TRACE_ID = "TRACE_ID";

    private static final ThreadLocal<Long> TIME_THREADLOCAL = (ThreadLocal<Long>) new NamedThreadLocal("Cost Time");

    private static final ThreadLocal<String> TRACE_ID_THREADLOCAL = (ThreadLocal<String>) new NamedThreadLocal("TraceId");

    @Resource
    private RedisCache redisCache;

    @Before("@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, APILog controllerLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
        String tid = IdUtils.fastSimpleUUID();
        MDC.put("TRACE_ID", tid);
        TRACE_ID_THREADLOCAL.set(tid);
    }

    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, APILog controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
        if (jsonResult instanceof AjaxResult) {
            AjaxResult ajaxResult = (AjaxResult) jsonResult;
            ajaxResult.put("trace_id", TRACE_ID_THREADLOCAL.get());
        }
        MDC.remove("TRACE_ID");
    }

    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, APILog controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
        MDC.remove("TRACE_ID");
    }

    protected void handleLog(JoinPoint joinPoint, APILog controllerLog, Exception e, Object jsonResult) {
        try {
            SysOperLog operLog = new SysOperLog();
            operLog.setOperIp(IpUtils.getIpAddr());
            operLog.setOperUrl(ServerConfig.getURL(ServletUtils.getRequest()));
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            operLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get().longValue());
            try {
                String lang = new JSONObject(operLog.getOperParam()).getStr("lang");
                if (StringUtils.isNotEmpty(lang)) {
                    redisCache.setCacheObject(CacheConstants.APP_CLIENT_LANG.key() + LocalUserID.get(), lang);
                }
            } catch (Exception ignored) {
            }
            log.info("-------------------------------------------------------------------");
            log.info("[TIT] >>> {} ", operLog.getTitle());
            log.info("[MTD] >>> {} ", operLog.getMethod());
            log.info("[URL] >>> {}", operLog.getOperUrl());
            log.info("[IP]  >>> {}", operLog.getOperIp());
            log.info("[REQ] >>> {}", operLog.getOperParam());
            log.info("[RES] >>> {}", operLog.getJsonResult());
            log.info("[CTM] >>> {}", operLog.getCostTime());
            log.info("-------------------------------------------------------------------");
        } catch (Exception exp) {
            log.error("异常信息:{}", exp);
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    public void getControllerMethodDescription(JoinPoint joinPoint, APILog apiLog, SysOperLog operLog, Object jsonResult) throws Exception {
        operLog.setTitle(apiLog.title());
        setRequestValue(joinPoint, operLog);
        if (StringUtils.isNotNull(jsonResult)) {
            operLog.setJsonResult(JSONUtil.toJsonStr(jsonResult));
        }
    }

    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) {
        Map<?, ?> paramsMap = ServletUtils.getParamMap(ServletUtils.getRequest());
        String requestMethod = operLog.getRequestMethod();
        if (paramsMap.isEmpty() && (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(params);
        } else {
            operLog.setOperParam(JSONUtil.toJsonStr(paramsMap));
        }
    }

    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o))
                    try {
                        String jsonObj = JSONUtil.toJsonStr(o);
                        params = params + jsonObj.toString() + " ";
                    } catch (Exception exception) {
                    }
            }
        return params.trim();
    }

    public boolean isFilterObject(Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            Iterator iterator = collection.iterator();
            if (iterator.hasNext()) {
                Object value = iterator.next();
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            Iterator iterator = map.entrySet().iterator();
            if (iterator.hasNext()) {
                Object value = iterator.next();
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return (o instanceof MultipartFile || o instanceof javax.servlet.http.HttpServletRequest || o instanceof javax.servlet.http.HttpServletResponse || o instanceof org.springframework.validation.BindingResult);
    }
}
