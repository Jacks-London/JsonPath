package com.jayway.jsonpath.internal.function;

import com.jayway.jsonpath.Function;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.PathRef;
import net.minidev.json.JSONArray;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides the length of a JSONArray Object
 *
 * Created by mattg on 6/26/15.
 */
public class Length implements Function {

    @Override
    public Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx) {
        if (model instanceof JSONArray) {
            JSONArray array = (JSONArray)model;
            return Integer.valueOf(array.size());
        }
        else if (model instanceof Map) {
            return Integer.valueOf(((Map) model).size());
        }
        return null;
    }
}