package com.xjc.tool.computing;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @Author jiachenxu
 * @Date 2021/9/24
 * @Descripetion ComputingEngine
 */
public class ComputingEngine {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        String result = "1+2/5+5";
        String eval = scriptEngine.eval(result).toString();
        System.out.println(eval);
    }
}
