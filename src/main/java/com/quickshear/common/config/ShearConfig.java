package com.quickshear.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @since v
 */
@Component
public class ShearConfig {

    // 系统模式：test、live
    @Value("${shear.env}")
    private String env;

    @Value("${shear.env}")
    private String appId;
    

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
    
}
