package com.quickshear.common.wechat.domain;

import java.io.Serializable;

/**
 * 微信公众号配置信息类
 * 
 * @author liuyh
 * @date 2015-9-11
 *
 */
public class WechatConfig implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4098059352785986673L;

  private String wechatToken;

  private String wechatHost;
  
  private String wechatAuthorizeUrl;
  
  private String wechatMsgTemplateId;

  /**
   * 保存在缓存中的token的key
   */
  private String wechatTokenCacheKey;

  public String getWechatToken() {
    return wechatToken;
  }

  public void setWechatToken(String wechatToken) {
    this.wechatToken = wechatToken;
  }

  public String getWechatHost() {
    return wechatHost;
  }

  public void setWechatHost(String wechatHost) {
    this.wechatHost = wechatHost;
  }

  public String getWechatTokenCacheKey() {
    return wechatTokenCacheKey;
  }

  public void setWechatTokenCacheKey(String wechatTokenCacheKey) {
    this.wechatTokenCacheKey = wechatTokenCacheKey;
  }

  public String getWechatAuthorizeUrl() {
    return wechatAuthorizeUrl;
  }

  public void setWechatAuthorizeUrl(String wechatAuthorizeUrl) {
    this.wechatAuthorizeUrl = wechatAuthorizeUrl;
  }

  public String getWechatMsgTemplateId() {
    return wechatMsgTemplateId;
  }

  public void setWechatMsgTemplateId(String wechatMsgTemplateId) {
    this.wechatMsgTemplateId = wechatMsgTemplateId;
  }

}
