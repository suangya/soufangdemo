package com.soufang.soufangdemo.service.impl;

import com.soufang.soufangdemo.service.SmsService;
import com.soufang.soufangdemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private SmsService smsService;

    public UserServiceImpl(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public void sendSmsVerify(HttpServletRequest request, String phone) throws Exception {
        String code = generateCode();
        HttpSession session = request.getSession();
        session.setAttribute("phone",phone);
        session.setAttribute("code",code);
        session.setAttribute("codeTime", System.currentTimeMillis());
        smsService.sendVerifyCode(phone,code);
    }

    private String generateCode(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
