package com.soufang.soufangdemo.service;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    void sendSmsVerify(HttpServletRequest request, String phone) throws Exception;
}
