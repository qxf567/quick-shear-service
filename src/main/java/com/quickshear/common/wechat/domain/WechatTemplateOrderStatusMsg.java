package com.quickshear.common.wechat.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import com.quickshear.common.wechat.domain.WechatTemplateMsg.TemplateData;

/**
 * 订单变化微信消息模板消息类
 * 
 */
public class WechatTemplateOrderStatusMsg implements Serializable{

	
  private static final long serialVersionUID = 3562327981738738782L;

  private WechatTemplateMsg msg;
	
  private Map<String, TemplateData> data;

  private String defaultTopColor = "#FF0000";
  private String defaultItemColor = "#173177";

  private TemplateData firstTemplate;
  private TemplateData keyword1Template;
  private TemplateData keyword2Template;
  private TemplateData keyword3Template;
  private TemplateData keyword4Template;
  private TemplateData remarkTemplate;

  public WechatTemplateOrderStatusMsg(String templateId){
    this.initial();
    msg.setTemplate_id(templateId);
  }
  
  public WechatTemplateOrderStatusMsg(){
    this.initial();
  }
  
  /**
   * 初始化
   */
  private void initial() {
    msg = new WechatTemplateMsg();
    msg.setTemplate_id("C96smq2eb2iHCoxeaLBy_3EOMiTy1Pg5zLm0P3kIkbY");
    msg.setTopcolor(this.defaultTopColor);

    firstTemplate = new TemplateData("", this.defaultItemColor);
    keyword1Template = new TemplateData("", this.defaultItemColor);
    keyword2Template = new TemplateData("", this.defaultItemColor);
    keyword3Template = new TemplateData("", this.defaultItemColor);
    keyword4Template = new TemplateData("", this.defaultItemColor);
    remarkTemplate = new TemplateData("", this.defaultItemColor);

    data = new HashMap<String, TemplateData>();
    data.put("first", firstTemplate);
    data.put("keyword1", keyword1Template);
    data.put("keyword2", keyword2Template);
    data.put("keyword3", keyword3Template);
    data.put("keyword4", keyword4Template);
    data.put("remark", remarkTemplate);
    msg.setData(data);
  }

  /**
   * 转换成NameValuePair[]，为httpClient提供数据
   * 
   * @return
   */
  public NameValuePair[] toNameValuePairArray() {
    return msg.toNameValuePairArray();
  }

  public void setTemplate_id(String template_id) {
    msg.setTemplate_id(template_id);
  }
  
  /**
   * 设置接收人openid
   * 
   * @param touser
   */
  public void setTouser(String touser) {
    msg.setTouser(touser);
  }

  /**
   * 设置url
   * 
   * @param url
   */
  public void setUrl(String url) {
    msg.setUrl(url);
  }

  public void setFirstValue(String value) {
    firstTemplate.setValue(value);
  }

  public void setKeyword1Value(String value) {
    keyword1Template.setValue(value);
  }

  public void setKeyword2Value(String value) {
    keyword2Template.setValue(value);
  }

  public void setKeyword3Value(String value) {
    keyword3Template.setValue(value);
  }

  public void setKeyword4Value(String value) {
    keyword4Template.setValue(value);
  }

  public void setRemarkValue(String value) {
    remarkTemplate.setValue(value);
  }

  public WechatTemplateMsg getMsg(){
    return this.msg;
  }
}
