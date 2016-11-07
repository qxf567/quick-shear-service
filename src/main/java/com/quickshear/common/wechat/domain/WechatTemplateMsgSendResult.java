package com.quickshear.common.wechat.domain;

/**
 * 微信模板消息发送结果
 * 1.发送成功：success值为true，msg的值为模板消息的msgid；<br>
 * 2.发送失败：success值为false，msg的值为模板消息发送失败的错误原因。<br>
 * 
 * @author liuyh
 */
public class WechatTemplateMsgSendResult extends AbstractResult {
  
  private Integer errorType;//错误类型

  public Integer getErrorType() {
    return errorType;
  }

  public void setErrorType(Integer errorType) {
    this.errorType = errorType;
  }
  
  public WechatTemplateMsgSendResult() {
    super();
  }
  
  public WechatTemplateMsgSendResult(boolean success) {
    super(success);
  }

}
