package com.around.me.authorization.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class Response<T> {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("content")
    private T content;

    protected Response(int status, String message, T content) {
        this.status = status;
        this.message = message;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Response<T> of(int status, String message, T content) {

        return new Response<>(status, message, content);
    }

    public static <T> Response<T> of(int status, String message) {

        return of(status, message, null);
    }

    public static <T> Response<T> ok(String message, T content) {

        return of(HttpStatus.OK.value(), message, content);
    }

    public static <T> Response<T> ok(T content) {

        return of(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), content);
    }

    public static <T> Response<T> badRequest(T content) {

        return badRequest(HttpStatus.BAD_REQUEST.getReasonPhrase(), content);
    }

    public static <T> Response<T> badRequest(String message, T content) {

        return of(HttpStatus.BAD_REQUEST.value(), message, content);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public T getContent() {
        return content;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setContent(T content) {
        this.content = content;
    }

}
