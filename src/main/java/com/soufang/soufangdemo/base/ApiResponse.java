package com.soufang.soufangdemo.base;

public class ApiResponse {
    private int code = Status.SUCCESS.getCode();
    private String message = Status.SUCCESS.getMessage();
    private Object data;

    public static ApiResponse success() {
        return new ApiResponse();
    }

    public static ApiResponse success(Object data) {
        ApiResponse resp = new ApiResponse();
        resp.data = data;
        return resp;
    }

    public static ApiResponse error(Status status) {
        ApiResponse resp = new ApiResponse();
        resp.code = status.getCode();
        resp.message = status.getMessage();
        return resp;
    }

    public static ApiResponse error(int code, String message) {
        ApiResponse resp = new ApiResponse();
        resp.code = code;
        resp.message = message;
        return resp;
    }

    public static ApiResponse error(Status status, Object data) {
        ApiResponse resp = new ApiResponse();
        resp.code = status.getCode();
        resp.message = status.getMessage();
        resp.data = data;
        return resp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
