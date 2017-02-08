package com.quickshear.service.sms;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** 利用本地文件实现记录openid读取的位置记录 */
@Service
public class StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);
    
    public void set(String value,HttpServletResponse response) {
	
	LOGGER.info("debug set cookie:"+value);
	
	Cookie cookie = new Cookie("openid", value);
	cookie.setMaxAge(60 * 60 * 24 * 30); // 设置生存期为1小时
	cookie.setDomain("m.qiansishun.com"); // 子域，在这个子域下才可以访问该Cookie
	response.addCookie(cookie);
    }

    public String get(HttpServletRequest request) {

	Cookie[] cookies = request.getCookies();
	if(cookies == null){
	    return null;
	}
	for(Cookie cookie:cookies){
	    if("openid".equals(cookie.getName())){
		return cookie.getValue();
	    }
	}
	return null;
    }


}