package com.around.me.authorization.api.v1.authorization.util;

import com.around.me.authorization.core.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {

    public Cookie createCookie(String cookieName, String value) {
        Cookie token = new Cookie(cookieName, value);
        token.setHttpOnly(true);
        token.setMaxAge((int) JwtTokenProvider.TOKEN_VALID_TIME);
        token.setPath("/");

        return token;
    }

    public Cookie getCookie(HttpServletRequest request, String cookieName) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {

            return null;
        }

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }

        return null;
    }
}
