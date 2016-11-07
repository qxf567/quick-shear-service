package com.quickshear.common.wechat.domain;

import java.io.Serializable;

/**
 * @author liuyh
 * @date 2015-9-11
 *
 */
public class AccessToken implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 3934610494519220575L;

  // 获取到的凭证
  private String token;

  // 凭证有效时间，单位：秒
  private int expiresIn;

  // 上次获得Token的毫秒值
  private long tokenTimeLong;

  private String  jsapiTicket;
  
  public String getJsapiTicket() {
    return jsapiTicket;
  }

  public void setJsapiTicket(String jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public final int getExpiresLong() {
    return 10 * 60 * 1000; // 重新获取时间10分钟
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  public long getTokenTimeLong() {
    return tokenTimeLong;
  }

  public void setTokenTimeLong(long tokenTimeLong) {
    this.tokenTimeLong = tokenTimeLong;
  }

  public boolean isTokenExpired() {
    return System.currentTimeMillis() - getTokenTimeLong() >= getExpiresLong();
  }
}
