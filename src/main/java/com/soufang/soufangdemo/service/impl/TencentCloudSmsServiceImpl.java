package com.soufang.soufangdemo.service.impl;

import com.soufang.soufangdemo.base.BusinessException;
import com.soufang.soufangdemo.base.TencentCloudSmsProperties;
import com.soufang.soufangdemo.service.SmsService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 使用腾讯云实现的验证码服务
 */
@Service
@ConditionalOnProperty(prefix = "app.sms", name = "provider", havingValue = "tencent")
public class TencentCloudSmsServiceImpl implements SmsService {
    private static final String DEFAULT_REGION = "ap-guangzhou";
    private static final String DEFAULT_PHONE_PREFIX = "+86";
    private static final Logger LOGGER = LoggerFactory.getLogger(TencentCloudSmsServiceImpl.class);

    private final TencentCloudSmsProperties properties;

    public TencentCloudSmsServiceImpl(TencentCloudSmsProperties properties) {
        this.properties = properties;
    }

    @Override
    public void sendVerifyCode(String phone, String code) {
        Credential cred = new Credential(properties.getSecretId(), properties.getSecretKey());
        SmsClient client = new SmsClient(cred, DEFAULT_REGION);
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(properties.getSdkAppId());
        req.setSignName(properties.getSignName());
        req.setTemplateId(properties.getTemplateId());

        String[] templateParamSet = {code};
        req.setTemplateParamSet(templateParamSet);

        String[] phoneNumberSet = {DEFAULT_PHONE_PREFIX + phone};
        req.setPhoneNumberSet(phoneNumberSet);

        try {
            SendSmsResponse resp = client.SendSms(req);
            LOGGER.debug("send sms code to phone: {}, resp: {}", phone, SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            throw new BusinessException("发送验证码错误", e);
        }
    }


}
