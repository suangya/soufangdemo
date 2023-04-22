package com.soufang.soufangdemo;

import com.aliyun.credentials.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.soufang.soufangdemo.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendNoteUtil {


    public static void testSendCode(String phone,String code) throws Exception {
        com.aliyun.credentials.Client credentialClient = new com.aliyun.credentials.Client();
        com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(
                new Config()
                        .setEndpoint("dysmsapi.aliyuncs.com")
                        .setCredential(credentialClient)
        );
        SendSmsRequest request = new SendSmsRequest()
                .setSignName("苏子昂的博客")
                .setTemplateCode("SMS_276366002")
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\""+code+"\"}");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(request, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }


    @Autowired
    private RedisService redisService;
    @Test
    public void test() throws Exception {
        testSendCode("18963778762","1234");
        System.out.println(redisService.get("18963778762"));
    }
}
