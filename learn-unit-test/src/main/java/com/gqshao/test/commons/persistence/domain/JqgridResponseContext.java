package com.gqshao.test.commons.persistence.domain;

import java.util.HashMap;
import java.util.Map;

public class JqgridResponseContext {
    private static ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>();

    private JqgridResponseContext() {

    }

    public static <T> JqgridResponse<T> getJqgridResponse(Class<T> clazz) {
        JqgridResponse<T> jqgridResponse = (JqgridResponse<T>) JqgridResponseContext.get("jqgridResponse");
        if (jqgridResponse == null) {
            jqgridResponse = new JqgridResponse<T>();
            JqgridResponseContext.set("jqgridResponse", jqgridResponse);
        }
        return jqgridResponse;
    }

    protected static Object get(String key) {
        Map<String, Object> map = context.get();
        if (null == map) {
            map = new HashMap<String, Object>();
            context.set(map);
        }
        return map.get(key);
    }

    protected static void set(String key, Object value) {
        Map<String, Object> map = context.get();
        map.put(key, value);
    }

}
