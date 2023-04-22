package com.soufang.soufangdemo.service.impl;

import com.aliyun.credentials.Client;
import com.aliyun.credentials.Credential;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.soufang.soufangdemo.base.AliyunSmsProperties;
import com.soufang.soufangdemo.base.BusinessException;
import com.soufang.soufangdemo.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "app.sms", name = "provider", havingValue = "aliyun")
public class AliyunSmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);

    private final AliyunSmsProperties aliyunSmsProperties;

    public AliyunSmsServiceImpl(AliyunSmsProperties aliyunSmsProperties) {
        this.aliyunSmsProperties = aliyunSmsProperties;
    }

    @Override
    public void sendVerifyCode(String phone, String code) throws Exception {
        Client credentialClient= new Client();
        com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(
                new Config().setCredential(credentialClient)
                        .setEndpoint("dysmsapi.aliyuncs.com")
        );
        SendSmsRequest request = new SendSmsRequest()
                .setSignName(aliyunSmsProperties.getSignName())
                .setTemplateCode(aliyunSmsProperties.getTemplateCode())
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\""+code+"\"}");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            /*SendSmsResponse response = client.sendSms(request);*/
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(request, runtime);
            LOGGER.debug("send sms code to phone:{}, code:{}",phone,client.sendSmsWithOptions(request, runtime));
        } catch (TeaException error) {
            throw new BusinessException("发送验证码错误", error);
            // 如有需要，请打印 error
            //com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            throw new BusinessException("发送验证码错误", _error);
            //TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            //com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }

}
