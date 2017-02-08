package com.quickshear.service.sms;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

/** 利用本地文件实现记录openid读取的位置记录 */
@Service
public class StorageService {

    public void set(String key,String value,HttpServletResponse response) {
	Cookie cookie = new Cookie(key, value);
	cookie.setMaxAge(60 * 60 * 24 * 30); // 设置生存期为1小时
//	cookie.setDomain("m.qiansishun.com"); // 子域，在这个子域下才可以访问该Cookie
	response.addCookie(cookie);
    }

    public String get(HttpServletRequest request,String key) {

	Cookie[] cookies = request.getCookies();
	if(cookies == null || key == null){
	    return null;
	}
	for(Cookie cookie:cookies){
	    if(key.equals(cookie.getName())){
		return cookie.getValue();
	    }
	}
	return null;
    }


}