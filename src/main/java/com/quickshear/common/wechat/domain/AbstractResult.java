package com.quickshear.common.wechat.domain;

public class AbstractResult {
  protected boolean success;
  protected String msg;

  public AbstractResult(boolean success) {
    super();
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public AbstractResult() {
    super();
  }
}
