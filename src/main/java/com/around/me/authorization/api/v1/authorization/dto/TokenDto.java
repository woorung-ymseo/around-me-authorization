package com.around.me.authorization.api.v1.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

    private String accessToken;

    private String refreshToken;

    public void setToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
