package com.massagebase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:WuShuang
 * @date:2020/6/10
 * @ver:1.0
 **/
public class CustomOperatorImpl implements CustomOperator {

    @Override
    public String abs(String expression,String req) {
        String result = "";
        Pattern p = Pattern.compile("(?<="+req+"\\()[^\\)]+");
        Matcher m = p.matcher(expression);
        while (m.find()) {
            result = m.group();
        }
        String replace = "";
        //判断是否为数字
            if(isNumericzidai(result)){
                int abs = Math.abs(Integer.parseInt(result));
                replace = expression.replace(req +"("+ result + ")", String.valueOf(abs));
            }else {
                throw  new RuntimeException("输入非法字符");
            }
        return replace;
    }

    public static boolean isNumericzidai(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


}
