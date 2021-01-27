package tools;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcExp {


    public static boolean calcOneBool(String exp) throws Exception {
        String[] splited = exp.replaceAll("\\s+", "").split("<=|>=|==|!=|>|<");
        if (splited.length != 2) {
            throw new Exception("value error, contain more on one boolean: " + exp);
        }
        boolean res = false;
        if (exp.contains("<=")) {
            res = calc(splited[0]) <= calc(splited[1]);
        } else if (exp.contains("<")) {
            res = calc(splited[0]) < calc(splited[1]);
        } else if (exp.contains(">=")) {
            res = calc(splited[0]) >= calc(splited[1]);
        } else if (exp.contains(">")) {
            res = calc(splited[0]) > calc(splited[1]);
        } else if (exp.contains("==")) {
            res = calc(splited[0]) == calc(splited[1]);
        } else if (exp.contains("!=")) {
            res = calc(splited[0]) != calc(splited[1]);
        }

        return res;
    }

    public static float calc(String str) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String res = "";
        try {
            res = engine.eval(str).toString();
        } catch (ScriptException e) {
        }
        return Float.parseFloat(res);
    }
}
