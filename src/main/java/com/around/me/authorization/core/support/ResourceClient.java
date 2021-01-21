package com.around.me.authorization.core.support;

import com.around.me.authorization.core.constants.HeaderConstants;
import com.around.me.authorization.core.context.ResponseContextHolder;
import com.around.me.authorization.core.dto.Response;
import com.around.me.authorization.core.utils.HttpServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ResourceClient {

    private final RestTemplate restTemplate ;

    /**
     * RestTemplate get for Data
     *
     * @param <T>
     * @param uri
     * @param parameter
     * @param httpHeaders
     * @param clazz
     * @return
     */
    public <T> Object getForData(String uri, Object parameter, HttpHeaders httpHeaders, Class<T> clazz) {
        Object data = this.excueteForData(uri, HttpMethod.GET, parameter, httpHeaders, clazz);

        return data != null ? (T) data : new Object();
    }

    /**
     * RestTemplate get
     *
     * @param uri
     * @param parameter
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object getForData(String uri, Object parameter, Class<T> clazz) {
        Object data = this.getForData(uri, parameter, null, clazz);

        return data != null ? (T) data : new Object();
    }

    /**
     * RestTemplate get
     *
     * @param uri
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object getForData(String uri, HttpHeaders httpHeaders, Class<T> clazz) {
        Object data = this.excueteForData(uri, HttpMethod.GET, null, httpHeaders, clazz);

        return data != null ? (T) data : new Object();
    }

    /**
     * RestTemplate get
     *
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object getForData(String uri, Class<T> clazz) {
        return this.excueteForData(uri, HttpMethod.GET, null, null, clazz);
    }

    /**
     * RestTemplate post
     *
     * @param uri
     * @param parameter
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object postForData(String uri, Object parameter, HttpHeaders httpHeaders, Class<T> clazz) {
        Object data = this.excueteForData(uri, HttpMethod.POST, parameter, httpHeaders, clazz);

        return data != null ? (T) data : new Object();
    }

    /**
     * RestTemplate post
     *
     * @param uri
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object postForData(String uri, HttpHeaders httpHeaders, Class<T> clazz) {
        Object data = this.excueteForData(uri, HttpMethod.POST, null, httpHeaders, clazz);

        return data != null ? (T) data : new Object();
    }

    /**
     * RestTemplate post
     *
     * @param uri
     * @param parameter
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object postForData(String uri, Object parameter, Class<T> clazz) {
        Object data = this.excueteForData(uri, HttpMethod.POST, parameter, null, clazz);

        return data != null ? (T) data : new Object();
    }

    /**
     * RestTemplate post
     *
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object postForData(String uri, Class<T> clazz) {
        Object data = this.excueteForData(uri, HttpMethod.POST, null, null, clazz);

        return data != null ? (T) data : new Object();
    }

   // =========

    /**
     * RestTemplate get
     *
     * @param uri
     * @param parameter
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> getForResponse(String uri, Object parameter, HttpHeaders httpHeaders, Class<T> clazz) {
        return this.excuete(uri, HttpMethod.GET, parameter, httpHeaders, clazz);
    }

    /**
     * RestTemplate get
     *
     * @param uri
     * @param parameter
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> getForResponse(String uri, Object parameter, Class<T> clazz) {
        return this.getForResponse(uri, parameter, null, clazz);
    }

    /**
     * RestTemplate get
     *
     * @param uri
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> getForResponse(String uri, HttpHeaders httpHeaders, Class<T> clazz) {
        return this.excuete(uri, HttpMethod.GET, null, httpHeaders, clazz);
    }


    /**
     * RestTemplate get
     *
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> getForResponse(String uri, Class<T> clazz) {
        return this.excuete(uri, HttpMethod.GET, null, null, clazz);
    }



    /**
     * RestTemplate post
     *
     * @param uri
     * @param parameter
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> postForResponse(String uri, Object parameter, HttpHeaders httpHeaders, Class<T> clazz) {
        return this.excuete(uri, HttpMethod.POST, parameter, httpHeaders, clazz);
    }

    /**
     * RestTemplate post
     *
     * @param uri
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> postForResponse(String uri, HttpHeaders httpHeaders, Class<T> clazz) {
        return this.excuete(uri, HttpMethod.POST, null, httpHeaders, clazz);
    }

    /**
     * RestTemplate post
     *
     * @param uri
     * @param parameter
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> postForResponse(String uri, Object parameter, Class<T> clazz) {
        return this.excuete(uri, HttpMethod.POST, parameter, null, clazz);
    }

    /**
     * RestTemplate post
     *
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Response<T> postForResponse(String uri, Class<T> clazz) {
        return this.excuete(uri, HttpMethod.POST, null, null, clazz);
    }

    /**
     * RestTemplate Excuete
     *
     * @param uri
     * @param method
     * @param parameter
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    @SneakyThrows
    private <T> Response<T> excuete(String uri, HttpMethod method, Object parameter, HttpHeaders httpHeaders, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();

        if (parameter != null) {
            Map<String, Object> map = objectMapper.convertValue(parameter, Map.class);

            paramMap.setAll(map);

            // GET 방식은 경우 URI에 Query String  추가
            if (StringUtils.equals(method.toString(), HttpMethod.GET.toString())) {
                uri = uri + this.urlEncodeUTF8(map);
            }
        }

        ResponseEntity<Response> response = null;

//        HttpEntity request = this.getHttpEntity(paramMap, httpHeaders);
/*
        if (StringUtils.equals(method.toString(), HttpMethod.GET.toString())) {
            response = restTemplate.getForEntity(uri, request, Response.class);
        } else if (StringUtils.equals(method.toString(), HttpMethod.POST.toString())) {
            response = restTemplate.postForEntity(uri, request, Response.class);
        }*/



        HttpEntity httpEntity = this.getHttpEntity(paramMap, httpHeaders);


        ResponseEntity<Response<T>> responseEntity =
                restTemplate.exchange(uri, method, httpEntity
                        , new ParameterizedTypeReference<Response<T>>() {
                            public Type getType() {
                                return new ResourceParameterizedTypeImpl((ParameterizedType) super.getType(), new Type[] {clazz});
                            }
                        }, paramMap);

        return responseEntity.getBody();
    }

    /**
     * RestTemplate Excuete
     *
     * @param uri
     * @param method
     * @param parameter
     * @param httpHeaders
     * @param clazz
     * @param <T>
     * @return
     */
    @SneakyThrows
    private <T> Object excueteForData(String uri, HttpMethod method, Object parameter, HttpHeaders httpHeaders, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();

        if (parameter != null) {
            Map<String, Object> map = objectMapper.convertValue(parameter, Map.class);

            paramMap.setAll(map);

            // GET 방식은 경우 URI에 Query String  추가
            if (StringUtils.equals(method.toString(), HttpMethod.GET.toString())) {
                uri = uri + this.urlEncodeUTF8(map);
            }
        }


//        HttpEntity request = this.getHttpEntity(paramMap, httpHeaders);
/*
        if (StringUtils.equals(method.toString(), HttpMethod.GET.toString())) {
            response = restTemplate.getForEntity(uri, request, Response.class);
        } else if (StringUtils.equals(method.toString(), HttpMethod.POST.toString())) {
            response = restTemplate.postForEntity(uri, request, Response.class);
        }*/



        HttpEntity httpEntity = this.getHttpEntity(paramMap, httpHeaders);


        ResponseEntity<T> responseEntity =
                restTemplate.exchange(uri, method, httpEntity
                        , new ParameterizedTypeReference<T>() {}, paramMap);

        return responseEntity.getBody();
    }


    /**
     * Get Header
     *
     * @return
     */
    private HttpHeaders getHeader() {
        return HttpHeaderBuilder.builder()
                .authorization(ResponseContextHolder.xAuthToken())
                .build();
    }

    /**
     * Get HttpEntity made by HttpHeaders
     *
     * @param httpHeaders
     * @return
     */
    private HttpEntity<MultiValueMap<String, Object>> getHttpEntity(HttpHeaders httpHeaders) {

        if (httpHeaders == null) {
            httpHeaders = this.getHeader();
        } else {
            this.setHeaderOfResponseContextHolder(httpHeaders);
        }

        return new HttpEntity<MultiValueMap<String, Object>>(httpHeaders);
    }

    /**
     * Get HttpEntity made by HttpHeaders
     *
     * @param httpHeaders
     * @return
     */
    private HttpEntity getHttpEntity(Object body, HttpHeaders httpHeaders) {

        if (httpHeaders == null) {
            httpHeaders = this.getHeader();
        } else {
            this.setHeaderOfResponseContextHolder(httpHeaders);
        }

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity(body, httpHeaders);
    }

    /**
     * Get HttpEntity (included token header)
     *
     * @return
     */
    private HttpEntity<String> getHttpEntity() {
        return new HttpEntity<String>(this.getHeader());
    }

    private String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    private String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }


    /**
     * Header 값 설정 (ResponseContextHolder 의 값 헤더 설정)
     *
     * @param httpHeaders
     */
    private void setHeaderOfResponseContextHolder(HttpHeaders httpHeaders) {
        HttpServletRequest request = HttpServletUtils.getRequest();

//        // Access Token
//        if (StringUtils.isEmpty(request.getHeader(HeaderConstants.AUTHORIZATION))) {
            httpHeaders.set(HeaderConstants.X_AUTH_TOKEN, ResponseContextHolder.xAuthToken());
//        }
    }
}
