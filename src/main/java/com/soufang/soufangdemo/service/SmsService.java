package com.soufang.soufangdemo.service;

public interface SmsService {
    void sendVerifyCode(String phone, String code) throws Exception;
}
