package com.abasecode.opencode.base.safe.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
public class CodeDesensitizeUtils {
    private static final int CN_ID_LEN=18;
    public static String desensitize(int first, int last, String value){
        if(StringUtils.isBlank(value)){
            return "";
        }
        int len = value.length();
        if(len<= first + last){
            return value;
        }
        String f = value.substring(0,first);
        String l =value.substring(len-last);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len-first-last; i++) {
            sb.append("*");
        }
        return f + sb.toString() + l;
    }

    public static String desensitizeBirthday(String value){
        if(StringUtils.isBlank(value)){
            return "";
        }
        String pattern = "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$";
        boolean r = value.matches(pattern);
        if(r){
            int len = value.length();
            if(len== 8 ){
                String a=value.substring(0,2);
                String b=value.substring(4,6);
                return a+"**"+b+"**";
            } else {
                int t1 = value.indexOf("-");
                int t2 = value.indexOf("/");
                int t3 = value.indexOf(".");
                String a=value.substring(0,2);
                String b=value.substring(5,7);
                if(t1==4){
                    return a+"**-"+b+"-**";
                }
                if(t2==4){
                    return a+"**/"+b+"/**";
                }
                if(t3==4){
                    return a+"**."+b+".**";
                }
            }
            return value;
        }
        return value;
    }

    public static String desensitizeCnIdNo(String value){
        if(StringUtils.isBlank(value)){
            return "";
        }
        int len = value.length();
        if(len!=CN_ID_LEN){
            return value;
        }
        String a=value.substring(0,4);
        String b=value.substring(6,8);
        String c=value.substring(10,12);
        String d=value.substring(16,18);
        return a+"**"+b+"**"+c+"****"+d;
    }
}
