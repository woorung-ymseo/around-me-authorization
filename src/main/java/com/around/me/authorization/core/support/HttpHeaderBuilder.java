package com.around.me.authorization.core.support;


import com.around.me.authorization.core.constants.HeaderConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

public class HttpHeaderBuilder {

    private String xAuthToken;

    public HttpHeaderBuilder authorization(String xAuthToken) {
        this.xAuthToken = xAuthToken;

        return this;
    }

    public HttpHeaders build() {
        HttpHeaders header = new HttpHeaders();

        if (StringUtils.isNoneEmpty(this.xAuthToken)) {
            header.set(HeaderConstants.X_AUTH_TOKEN, this.xAuthToken);
        }

        return header;
    }

    public static HttpHeaderBuilder builder() {
        return new HttpHeaderBuilder();
    }
}
