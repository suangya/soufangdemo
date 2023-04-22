package com.soufang.soufangdemo.service.impl;

import com.soufang.soufangdemo.service.SmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "app.sms", name = "provider", havingValue = "none", matchIfMissing = true)
public class NoneSmsServiceImpl implements SmsService {
    @Override
    public void sendVerifyCode(String phone, String cide) throws Exception {

    }
}
