package com.quickshear.common.sms;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

/**
 * 发短信服务
 * 
 * https://help.aliyun.com/document_detail/29459.html?spm=5176.doc35376.6.588.aNb4tf
 */
@Service
public class MessageService {

    /**
     * 发信短信验证码,返回四位验证码
     */
    public String sendRandomCode(String phone) {
	String code = null;
	try {
	    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI2ySw5qXKk9C1",
		    "BUu7ifM10cQeth6mhMJ8PdVXpllDsb ");
	    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
	    IAcsClient client = new DefaultAcsClient(profile);
	    SingleSendSmsRequest request = new SingleSendSmsRequest();
	    request.setSignName("仟丝顺");
	    request.setTemplateCode("SMS_32505240");
	    code = getRandomCode(4);
	    request.setParamString("{code:" + code + "}");
	    request.setRecNum(phone);
	    SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
	    System.out.println(httpResponse);
	} catch (ServerException e) {
	    e.printStackTrace();
	} catch (ClientException e) {
	    e.printStackTrace();
	}
	return code;
    }

    /**
     * 随机产生密码
     * 
     * @param length
     *            生产的长度
     */
    private String getRandomCode(int length) {
	String str = "0123456789";
	Random random = new Random();
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < length; i++) {
	    int number = random.nextInt(10);
	    sb.append(str.charAt(number));
	}
	return sb.toString();
    }
}
