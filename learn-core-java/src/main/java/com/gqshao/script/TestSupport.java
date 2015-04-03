package com.gqshao.script;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

public class TestSupport {
    public static void main(String[] args) {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        List<ScriptEngineFactory> engineFactories = scriptEngineManager.getEngineFactories();
        if (engineFactories.size() == 0) {
            System.out.println("本JVM尚不支持任何脚本引擎");
            return;
        }

        System.out.println("本JVM支持的脚本引擎有:");
        for (ScriptEngineFactory engineFactory : engineFactories) {
            System.out.println("引擎名称:" + engineFactory.getEngineName());
            System.out.println("\t可被ScriptEngineManager识别的名称:" + engineFactory.getNames());
            System.out.println("\t该引擎支持的脚本语言名称:" + engineFactory.getLanguageName());
            System.out.println("\t是否线程安全:" + engineFactory.getParameter("THREADING"));
        }
    }
}