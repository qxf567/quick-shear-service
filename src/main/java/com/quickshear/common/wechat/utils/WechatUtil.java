package com.quickshear.common.wechat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.quickshear.common.config.ShearConfig;
import com.quickshear.common.wechat.domain.AccessToken;


/**
 * @author liuyh
 * @date 2015-9-11
 *
 */
@Component
public class WechatUtil {
  private static Logger log = Logger.getLogger(WechatUtil.class);

  // 获取access_token的接口地址（GET） 限200（次/天）
  public final static String access_token_url =
      "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
  @Autowired
  private ShearConfig config;

  /**
   * 获取access_token 首先从session中获取，如果没有，则从微信获取，并放入session中
   *
   * @return
   */
  public AccessToken getAccessToken() {
    AccessToken result = new AccessToken();
//    // 从缓存中获取
//    String token = (String) redisTemplate.opsForValue().get(wechatConfig.getWechatTokenCacheKey());
//    log.debug("token=" + token);
//
//    if (StringUtils.isBlank(token)) {
//      // 从微信中获取accessToken
//      result = this.getAccessTokenFromWexin();
//      if (result != null) {
//        log.debug(redisTemplate.opsForValue().get("result.getToken()=" + result.getToken()));
//        // 存入session
//        redisTemplate.opsForValue().set(wechatConfig.getWechatTokenCacheKey(), result.getToken(),
//            result.getExpiresIn() - 1800, TimeUnit.SECONDS);
//
//        log.debug(redisTemplate.opsForValue()
//            .get("wechatConfig.getWechatTokenCacheKey()=" + wechatConfig.getWechatTokenCacheKey()));
//      }
//    } else {
//      result.setToken(token);
//    }
    // 从微信中获取accessToken
    result = this.getAccessTokenFromWexin();
    // log.info("获取accessToken:" + JSON.toJSONString(accessToken));
    return result;
  }

  /**
   * 获取access_token PS:重新生成token短时间内上一个token仍然可用，故无需考虑同步问题
   *
   * @return
   */
  private AccessToken getAccessTokenFromWexin() {
    AccessToken accessToken = new AccessToken();

    String requestUrl = access_token_url.replace("APPID", config.getAppId())
        .replace("APPSECRET", config.getAppSecret());
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

        log.info("重新生成accessToken:" + objectMapper.writeValueAsString(accessToken));
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
        log.error("获取token失败 errcode:{" + objMap.get("errcode") + "} errmsg:{"
            + objMap.get("errmsg") + "}", e);
      }
    }

    return accessToken;

  }


  /**
   * 发起https请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param requestMethod 请求方式（GET、POST）
   * @param outputStr 提交的数据
   * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
   */
  private static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
    StringBuffer buffer = new StringBuffer();
    try {
      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      TrustManager[] tm = {new MyX509TrustManager()};
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
