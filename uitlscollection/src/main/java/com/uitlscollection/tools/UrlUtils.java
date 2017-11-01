package com.uitlscollection.tools;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Mr_yang on 2017/9/26.
 */

public class UrlUtils {
    /**
     * 转码
     *
     * @param paramString
     * @return
     */
    public static String encodedURL(String paramString) {
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {

        }
        return "";
    }

    /**
     * 解码
     *
     * @param paramString
     * @return
     */
    public static String decodedtoURL(String paramString) {
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
        }
        return "";
    }
}
