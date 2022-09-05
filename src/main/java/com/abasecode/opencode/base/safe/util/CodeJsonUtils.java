package com.abasecode.opencode.base.safe.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
@Slf4j
public class CodeJsonUtils {
    public static boolean hasJsonString(String jsonStr) {
        try {
            Object object = JSON.parse(jsonStr);
            if (object instanceof JSONObject) {
                return true;
            } else {
                return object instanceof JSONArray;
            }
        } catch (Exception e) {
            log.info("the target is not json object!");
        }
        return false;
    }
}
