package com.around.me.authorization.core.context;

import com.around.me.authorization.core.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class ResponseContextHolder {

    private final RestTemplate restTemplate;

    private static User user;

    private static String xAuthToken;

    /**
     * Get User
     *
     * @return
     */
    public static User user() {
        return ResponseContextHolder.user;
    }

    /**
     * Set User
     *
     * @param user
     */
    public static void user(User user) {
        ResponseContextHolder.user = user;
    }

    /**
     * Set AccessToken
     *
     * @param accessToken
     */
    public static void xAuthToken(String accessToken) {
        ResponseContextHolder.xAuthToken = accessToken;
    }

    /**
     * Get AccessToken
     *
     * @return
     */
    public static String xAuthToken() {
        return ResponseContextHolder.xAuthToken;
    }
}
