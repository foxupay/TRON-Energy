package com.fox.energy.common.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class JsonPathHelper {

    private static final String EMPTY_STRING = null;
    private static final String ALL = "*";


    public static Object getValuesFromJsonPath(String jsonPath, String jsonString) {
        return getValuesFromJsonPath(jsonPath, JSONUtil.parseObj(jsonString));
    }

    public static Object getValuesFromJsonPath(String jsonPath, JSONObject jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String[] pathParts = jsonPath.split("\\.");
        List<Object> results = new ArrayList<>();
        traverseJsonPath(jsonObject, pathParts, 0, results);
        if (results.isEmpty()) {
            return null;
        }
        if (results.size() == 1) {
            return results.get(0);
        }
        return results;
    }

    private static void traverseJsonPath(Object currentObject, String[] pathParts, int index, List<Object> results) {
        if (index == pathParts.length) {
            results.add(currentObject);
            return;
        }

        String part = pathParts[index];
        if (part.contains("[") && part.contains("]")) {
            String arrayKey = part.substring(0, part.indexOf('['));
            String arrayIndexStr = part.substring(part.indexOf('[') + 1, part.indexOf(']'));
            JSONArray jsonArray = ((JSONObject) currentObject).getJSONArray(arrayKey);
            if (ALL.equals(arrayIndexStr)) {
                for (Object o : jsonArray) {
                    traverseJsonPath(o, pathParts, index + 1, results);
                }
            } else {
                int arrayIndex = Integer.parseInt(arrayIndexStr);
                if (arrayIndex > jsonArray.size()) {
                    results.add(EMPTY_STRING);
                    return;
                }
                traverseJsonPath(jsonArray.get(arrayIndex), pathParts, index + 1, results);
            }
        } else {
            traverseJsonPath(((JSONObject) currentObject).get(part), pathParts, index + 1, results);
        }
    }


}