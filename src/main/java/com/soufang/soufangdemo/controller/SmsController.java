package com.soufang.soufangdemo.controller;

import com.soufang.soufangdemo.base.ApiResponse;
import com.soufang.soufangdemo.service.RedisService;
import com.soufang.soufangdemo.service.SmsService;
import com.soufang.soufangdemo.service.UserService;
import com.soufang.soufangdemo.service.impl.AliyunSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/sms")
public class SmsController {
    private final UserService userService;

    public SmsController( UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sms")
    public ApiResponse sendSms(HttpServletRequest request, @RequestParam("phone") String phone) throws Exception {
        /*
         * 1.生成四位数验证码
         * 2.存储验证码（十分钟有效）
         * 3.发送验证码
         */
        userService.sendSmsVerify(request,phone);
        return ApiResponse.success(phone);
    }

}
