package com.around.me.authorization.core.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet Utils
 */
@Component
public class HttpServletUtils {

    /**
     * get request
     *
     * @return
     */
    public final static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    /**
     * get response
     *
     * @return
     */
    public final static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * get parameter value
     *
     * @param paramKey
     * @return
     */
    public final static String getParameter(String paramKey) {
        String value = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(paramKey);

        return StringUtils.isEmpty(value) ? "" : value;
    }

    /**
     * get remote addr
     *
     * @return
     */
    public final static String getRemoteAddr() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
    }
}
