package com.around.me.authorization.api.v1.authorization.controller;

import com.around.me.authorization.api.v1.authorization.dto.TokenDto;
import com.around.me.authorization.api.v1.authorization.dto.UserParamDto;
import com.around.me.authorization.api.v1.authorization.service.AuthenticationService;
import com.around.me.authorization.api.v1.authorization.util.CookieUtil;
import com.around.me.authorization.api.v1.authorization.util.RedisUtil;
import com.around.me.authorization.core.annotaion.version.RestMappingV1;
import com.around.me.authorization.core.dto.Response;
import com.around.me.authorization.core.security.JwtTokenProvider;
import com.around.me.authorization.core.support.ResourceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(tags = "인가")
@Slf4j
@RequiredArgsConstructor
@RestMappingV1
class AuthorizationController {

    private final ResourceClient resourceClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final AuthenticationService authenticationService;

    @SneakyThrows
    @ApiOperation(value = "인가")
    @PostMapping(value = "/authorization")
    Response<TokenDto> authorization(@RequestBody String paramStr, @ApiIgnore Errors errors, HttpServletResponse response) {

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> map = objectMapper.readValue(paramStr, Map.class);

        List<String> list = (List<String>) map.get("userEmail");

        UserParamDto userParamDto = new UserParamDto();

        userParamDto.setUserEmail(list.get(0));

        String accessToken = jwtTokenProvider.generateToken(userParamDto);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userParamDto);

        TokenDto tokenDto = new TokenDto();

        tokenDto.setToken(accessToken);

//        Cookie accesTokenCookie = cookieUtil.createCookie(jwtTokenProvider.ACCESS_TOKEN_NAME, accessToken);
//        Cookie refreshTokenCookie = cookieUtil.createCookie(jwtTokenProvider.REFRESH_TOKEN_NAME, refreshToken);

        redisUtil.setDataExpire(userParamDto.getUserEmail(), refreshToken, JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
/*        response.addCookie(accesTokenCookie);
        response.addCookie(refreshTokenCookie);*/

        return Response.ok(tokenDto);
    }
}
//