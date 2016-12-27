package com.quickshear.common.wechat;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import com.quickshear.common.wechat.domain.AccessToken;
import com.quickshear.common.wechat.utils.HttpClientUtil;
import com.quickshear.common.wechat.utils.WechatUtil;

/**
 * 
 * 获取微信用户信息
 *
 */
@Component
public class WechatUserInfoManager {

  private static Logger log = Logger.getLogger(WechatUserInfoManager.class);
  @Resource
  private HttpClientUtil httpClientUtil;
  @Resource
  private WechatUtil wechatUtil;

  public String getWechatOpenIdByPageAccess(String appId, String appSecret, String code) {
    String openId = "";
    // 1、微信授权回调地址
    String finalUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId
        + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
    String resultFromOAuth2 = "";
    log.debug("-----------------WechatUserInfoManager.getWechatOpenIdByPageAccess------------");
    log.debug("finalUrl=" + finalUrl);
    resultFromOAuth2 = httpClientUtil.sendRequest(finalUrl);
    log.debug("resultFromOAuth2=" + resultFromOAuth2);

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> objMap;
    try {
      objMap = objectMapper.readValue(resultFromOAuth2, Map.class);
      openId = objMap.get("openid");
      log.debug("-------------------openId=" + openId);
    } catch (JsonParseException e) {
      e.printStackTrace();
      log.error("getWechatOpenIdByPageAccess(", e);
    } catch (JsonMappingException e) {
      e.printStackTrace();
      log.error("getWechatOpenIdByPageAccess(", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("getWechatOpenIdByPageAccess(", e);
    }
    return openId;
  }

  /**获取微信用户头像*/
  public String getWechatAvatarByPageAccess(String openid) {
    String avatarUrl = "";
    AccessToken accessToken = wechatUtil.getAccessToken();
    String finalUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
        + accessToken.getToken() + "&openid=" + openid + "&lang=zh_CN";
    String resultFromOAuth2 = "";
    resultFromOAuth2 = httpClientUtil.sendRequest(finalUrl);

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> objMap;
    try {
      objMap = objectMapper.readValue(resultFromOAuth2, Map.class);
      avatarUrl = objMap.get("headimgurl");
    } catch (JsonParseException e) {
      e.printStackTrace();
      log.error("getWechatOpenIdByPageAccess(", e);
    } catch (JsonMappingException e) {
      e.printStackTrace();
      log.error("getWechatOpenIdByPageAccess(", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("getWechatOpenIdByPageAccess(", e);
    }
    return avatarUrl;
  }
  
    /** 获取微信用户所有信息 */
  public Map<String, Object> getWechatUserInfoByPageAccess(String openid) {
    String avatarUrl = "";
    AccessToken accessToken = wechatUtil.getAccessToken();
    String finalUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
        + accessToken.getToken() + "&openid=" + openid + "&lang=zh_CN";
    String resultFromOAuth2 = "";
    resultFromOAuth2 = httpClientUtil.sendRequest(finalUrl);

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> objMap = null;
    try {
      objMap = objectMapper.readValue(resultFromOAuth2, Map.class);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("getWechatOpenIdByPageAccess(", e);
    }
    return objMap;
  }
}
