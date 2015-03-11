package com.gqshao.script;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestJexl {
    public static void main(String[] args) {
        /**
         * 新建或取回JexlEngine
         */

        JexlEngine jexl = new JexlEngine();

        /**
         * 初始化一个JexlContext对象，它代表一个执行JEXL表达式的上下文环境
         */
        JexlContext context = new MapContext();

        // 准备并设置JEXL表达式上下文环境
        Pojo pojo = new Pojo("Pojo", 123, true, new Date());
        List<String> list = Lists.newArrayList("a1", "", "c3");
        Map<String, Object> map = Maps.newHashMap();
        map.put("key", "value");
        map.put("pojo", pojo);


        context.set("pojo", pojo);
        context.set("list", list);
        context.set("map", map);


        // 准备表达式
        List<String> jexlExps = Lists.newArrayList("pojo.str", "pojo.inte", "pojo.bool", "pojo.date"
                , "size(list)", "list[0].length()", "list[0].toUpperCase()", "empty(list[1])"
                , "map['key']", "map['pojo'].str");

        // 执行表达式
        for (String jexlExp : jexlExps) {
            // 建立表达式对象 Create expression object
            Expression expression = jexl.createExpression(jexlExp);
            // 传入上下文环境并执行
            Object obj = expression.evaluate(context);
            System.out.println(expression.getExpression() + " = " + obj);
        }

    }
}

