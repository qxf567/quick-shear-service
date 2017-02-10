package com.quickshear.common.wechat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.quickshear.common.config.ShearConfig;
import com.quickshear.common.lru.LRUCache;
import com.quickshear.common.util.JsonUtil;
import com.quickshear.common.wechat.WechatConstat;
import com.quickshear.common.wechat.domain.AccessToken;

/**
 *
 */
@Component
public class WechatUtil {
    private static Logger log = Logger.getLogger(WechatUtil.class);
    @Autowired
    private ShearConfig config;

    private LRUCache cache;

    /**
     * 获取access_token 首先从session中获取，如果没有，则从微信获取，并放入session中
     * 
     * @return
     */
    public AccessToken getAccessToken() {
	AccessToken result = null;
	Date dateNow = new Date();
	Long stampNow = dateNow.getTime();
	if (cache == null) {
	    cache = new LRUCache(100);
	    result = this.getAccessTokenFromWexin();
	    cache.set("wechat_token", result.getToken());
	    cache.set("wechat_token_stamp", String.valueOf(stampNow));
	} else {
	    String token = cache.get("wechat_token");
	    String stampOld = cache.get("wechat_token_stamp");
	    if (StringUtils.isNotBlank(token)
		    && StringUtils.isNotBlank(stampOld)
		    && (stampNow - Long.valueOf(stampOld)) < 3600000) {//过期时间3600秒
		result = new AccessToken();
		result.setToken(token);
	    } else {
		 result = this.getAccessTokenFromWexin();
		 cache.set("wechat_token", result.getToken());
		 cache.set("wechat_token_stamp", String.valueOf(stampNow));
	    }
	}
	log.info("获取accessToken:" + JsonUtil.toJson(result));
	return result;
    }

    /**
     * 获取access_token PS:重新生成token短时间内上一个token仍然可用，故无需考虑同步问题
     * 
     * @return
     */
    private AccessToken getAccessTokenFromWexin() {
	AccessToken accessToken = new AccessToken();

	String requestUrl = WechatConstat.access_token_url.replace("APPID", config.getAppId()).replace("APPSECRET",
		config.getAppSecret());
	String httpResult = httpRequest(requestUrl, "GET", null);
	ObjectMapper objectMapper = new ObjectMapper();
	Map<String, Object> objMap = null;
	// 如果请求成功
	if ("" != httpResult) {
	    try {
		objMap = objectMapper.readValue(httpResult, Map.class);
		accessToken.setToken(objMap.get("access_token").toString());
		accessToken.setExpiresIn(Integer.valueOf(objMap.get("expires_in").toString()));
		accessToken.setTokenTimeLong(System.currentTimeMillis());

		log.info("重新生成accessToken了:" + objectMapper.writeValueAsString(accessToken));
	    } catch (JsonParseException e) {
		e.printStackTrace();
		log.error("getAccessTokenFromWexin()", e);
	    } catch (JsonMappingException e) {
		e.printStackTrace();
		log.error("getAccessTokenFromWexin()", e);
	    } catch (IOException e) {
		e.printStackTrace();
		log.error("getAccessTokenFromWexin()", e);
	    } catch (Exception e) {
		e.printStackTrace();
		// 获取token失败
		log.error("获取token失败 errcode:{" + objMap.get("errcode") + "} errmsg:{" + objMap.get("errmsg") + "}", e);
	    }
	}

	return accessToken;

    }

    /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    private static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
	StringBuffer buffer = new StringBuffer();
	try {
	    // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	    TrustManager[] tm = { new MyX509TrustManager() };
	    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	    sslContext.init(null, tm, new java.security.SecureRandom());
	    // 从上述SSLContext对象中得到SSLSocketFactory对象
	    SSLSocketFactory ssf = sslContext.getSocketFactory();

	    URL url = new URL(requestUrl);
	    HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
	    httpUrlConn.setSSLSocketFactory(ssf);

	    httpUrlConn.setDoOutput(true);
	    httpUrlConn.setDoInput(true);
	    httpUrlConn.setUseCaches(false);
	    // 设置请求方式（GET/POST）
	    httpUrlConn.setRequestMethod(requestMethod);

	    if ("GET".equalsIgnoreCase(requestMethod))
		httpUrlConn.connect();

	    // 当有数据需要提交时
	    if (null != outputStr) {
		OutputStream outputStream = httpUrlConn.getOutputStream();
		// 注意编码格式，防止中文乱码
		outputStream.write(outputStr.getBytes("UTF-8"));
		outputStream.close();
	    }

	    // 将返回的输入流转换成字符串
	    InputStream inputStream = httpUrlConn.getInputStream();
	    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

	    String str = null;
	    while ((str = bufferedReader.readLine()) != null) {
		buffer.append(str);
	    }
	    bufferedReader.close();
	    inputStreamReader.close();
	    // 释放资源
	    inputStream.close();
	    inputStream = null;
	    httpUrlConn.disconnect();
	} catch (ConnectException ce) {
	    ce.printStackTrace();
	    log.error("Weixin server connection timed out.");
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error("https request error:{}", e);
	}
	return buffer.toString();
    }
}
