package com.around.me.authorization.api.v1.authorization.service;

import com.around.me.authorization.api.v1.authorization.dto.UserParamDto;
import com.around.me.authorization.core.dto.Response;
import com.around.me.authorization.core.support.ResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final ResourceClient resourceClient;

    public Response<String> user(UserParamDto userParamDto) {
        Response<String> data = resourceClient.getForResponse("http://127.0.0.1:8081/user/api/v1/user", userParamDto, String.class);

        return data;
    }
}
