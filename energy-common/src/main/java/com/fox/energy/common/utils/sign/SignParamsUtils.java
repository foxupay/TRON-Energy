package com.fox.energy.common.utils.sign;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class SignParamsUtils {

    public static <T> String getParams(T request) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : new TreeMap<>(new JSONObject(request)).entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StrUtil.isEmpty(key) || StrUtil.isEmptyIfStr(value)) {
                continue;
            }
            if (value instanceof Double) {
                value = String.valueOf(value).replaceAll("\\.?0*$", "");
            }
            sb.append(key).append("=").append(value).append("&");
        }
        return sb.toString();
    }

}
