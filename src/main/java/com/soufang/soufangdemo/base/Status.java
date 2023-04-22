package com.soufang.soufangdemo.base;

import org.springframework.http.HttpStatus;

public enum Status {
    SUCCESS(0, "SUCCESS"),
    UNKNOWN(1, "Internal Error"),
    FORBIDDEN(403, "Forbidden"),
    PARAMS_ERROR(HttpStatus.BAD_REQUEST.value(), "InvalidParams"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    ERROR_USERNAME_OR_PASSWORD(1000, "错误的账号名或密码");

    private int code;
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
