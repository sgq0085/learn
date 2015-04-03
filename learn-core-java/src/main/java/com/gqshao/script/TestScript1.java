package com.gqshao.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class TestScript1 {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
        String exp = "c=a+b;if(c<0){'负数'}else if(c==0){'零'}else{c};";
        scriptEngine.put("a", Math.random() * (-10));
        scriptEngine.put("b", Math.random() * 10);
        Object result = scriptEngine.eval(exp);
        System.out.println(exp + " === " + result);
    }
}