package com.quickshear.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShearConfig {

    // 系统模式：test、live
    @Value("${shear.env}")
    private String env;

    @Value("${wechat.appid}")
    private String appId;
    
    @Value("${wechat.appsecret}")
    private String appSecret;
    //文件存储路径
    @Value("${fastdfs.server.host}")
    private String fastdfsServerHost;
    
    public String getEnv() {
	return env;
    }

    public void setEnv(String env) {
	this.env = env;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

	public String getFastdfsServerHost() {
		return fastdfsServerHost;
	}

	public void setFastdfsServerHost(String fastdfsServerHost) {
		this.fastdfsServerHost = fastdfsServerHost;
	}
}
