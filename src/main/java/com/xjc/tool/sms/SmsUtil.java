package com.xjc.tool.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Component;

/**
 * @Author jiachenxu
 * @Date 2021/12/2
 * @Descripetion
 */
@Component
public class SmsUtil {

    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";
    private static final String accessKeyId = "accessKeyId";
    private static final String accessKeySecret = "accessKeySecret";

    public static SmsDTO send(String telephone, String code) throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);


        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(telephone);
        request.setSignName("你的短信签名");
        request.setTemplateCode("你的短信模板");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
            System.out.println("短信发送成功！");
        } else {
            System.out.println("短信发送失败！");
        }
        return new SmsDTO(telephone, sendSmsResponse.getRequestId(), sendSmsResponse.getCode());
    }

}
