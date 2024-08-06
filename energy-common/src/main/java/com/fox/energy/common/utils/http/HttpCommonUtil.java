package com.fox.energy.common.utils.http;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fox.energy.common.utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class HttpCommonUtil {

    public static String getParams(Map<? extends String, ?> param) {
        TreeMap<String, Object> params = new TreeMap<>(param);
        List<String> paramList = new ArrayList<>();
        for (String key : params.keySet()) {
            if ("signature".equals(key)) {
                continue;
            }
            Object value = params.get(key);
            if (value == null || "".equals(value)) {
                continue;
            }
            if (value instanceof Map || value instanceof List) {
                paramList.add(String.format("%s=%s", key, JSONUtil.toJsonStr(value)));
                continue;
            }
            if (value instanceof Double) {
                paramList.add(String.format("%s=%s", key, doubleConvertLong(((Double) value).doubleValue())));
                continue;
            }
            paramList.add(String.format("%s=%s", key, value));
        }
        return String.join("&", paramList);
    }

    public static String getParamsAll(Map<? extends String, ?> param) {
        TreeMap<String, Object> params = new TreeMap<>(param);
        List<String> paramList = new ArrayList<>();
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value == null || "".equals(value)) {
                continue;
            }
            if (value instanceof Map || value instanceof List) {
                paramList.add(String.format("%s=%s", key, JSONUtil.toJsonStr(value)));
                continue;
            }
            if (value instanceof Double) {
                paramList.add(String.format("%s=%s", key, doubleConvertLong((Double) value)));
                continue;
            }
            paramList.add(String.format("%s=%s", key, value));
        }
        return String.join("&", paramList);
    }

    public static String doubleConvertLong(double d) {
        if (d == (long) d)
            return String.format("%d", new Object[]{(long) d});
        return String.format("%s", new Object[]{d});
    }

    /**
     * 获取key=value数据
     *
     * @return
     */
    public static String getDataFromRequest() {
        Map<String, String[]> params = ServletUtils.getRequest().getParameterMap();
        String queryString = "";
        for (String keyS : params.keySet()) {
            String[] values = params.get(keyS);
            for (int i = 0; i < values.length; i++) {
                String value = new String(values[i].getBytes());
                queryString += keyS + "=" + value + "&";
            }
        }
        if (queryString.length() > 0) {
            queryString = queryString.substring(0, queryString.length() - 1);
        }
        return queryString;
    }

    public static String getJSONDataFromRequest() {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = ServletUtils.getRequest().getReader();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

    public static JSONObject getJSONByKeyValue(String param) {
        JSONObject jsonObject = new JSONObject();
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            boolean isNor = !"=".equals(params[i].substring(params[i].length() - 1));
            boolean isLast = p.length == 2 && "=".equals(params[i].substring(params[i].length() - 1));
            if (p.length == 2 && isNor) {
                jsonObject.set(p[0], p[1]);
            } else if (p.length >= 2 || isLast) {
                jsonObject.set(p[0], params[i].substring(params[i].indexOf("=") + 1));
            }
        }
        return jsonObject;
    }


    public static Map<String, String> getMapFromRequest() {
        Map<String, String> data = new HashMap();
        Map<String, String[]> params = ServletUtils.getRequest().getParameterMap();
        for (String keyS : params.keySet()) {
            String[] values = params.get(keyS);
            for (int i = 0; i < values.length; i++) {
                String value = new String(values[i].getBytes());
                data.put(keyS, value);
            }
        }
        return data;
    }


}
