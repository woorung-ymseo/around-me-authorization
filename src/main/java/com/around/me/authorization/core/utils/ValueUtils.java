package com.around.me.authorization.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ValueUtils {

    /**
     * String to int
     *
     * @param val
     * @return
     */
    public static final int getInt(String val) {
        val = StringUtils.isEmpty(val) ? "0" : val;

        return Integer.parseInt(val);
    }

    /**
     * null to ""
     *
     * @param val
     * @return
     */
    public static final String getString(String val) {
        return StringUtils.isEmpty(val) ? "" : val;
    }

    /**
     * UTF-8 Encoding
     *
     * @param val
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final String encodeUTF8(String val) throws UnsupportedEncodingException {
        val = ValueUtils.getString(val);

        return URLEncoder.encode(val, StandardCharsets.UTF_8.name());
    }

}
