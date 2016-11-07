package com.quickshear.common.wechat.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 微信模板消息
 * 
 * @author liuyh
 *
 */
public class WechatTemplateMsg implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3498466419520152473L;

  private static final Logger logger = Logger.getLogger(WechatTemplateMsg.class);

  private String touser;
  private String template_id;
  private String url;
  private String topcolor = "#FF0000";
  private Map<String,TemplateData> data = new HashMap<String, TemplateData>();
  
  
  public NameValuePair[] toNameValuePairArray() {
    NameValuePair[] param =
        {new NameValuePair("touser", this.touser), new NameValuePair("template_id", this.template_id),
            new NameValuePair("url", this.url), new NameValuePair("topcolor", this.topcolor),
            new NameValuePair("data", this.formatData2String())};
    return param;
  }

  /**
   * 转换为对象
   * @return
   */
  private String formatData2String() {
    ObjectMapper objectMapper = new ObjectMapper();
    String rs = null;
    try {
      rs = objectMapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error("formatData2String()", e);
    }
    return rs;
  }

  
  static class TemplateData {
    private String value;
    private String color;
    
    public TemplateData(String value,String color){
      this.value = value;
      this.color = color;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }
  }
  
  public String getTouser() {
    return touser;
  }

  public void setTouser(String touser) {
    this.touser = touser;
  }

  public String getTemplate_id() {
    return template_id;
  }

  public void setTemplate_id(String template_id) {
    this.template_id = template_id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTopcolor() {
    return topcolor;
  }

  public void setTopcolor(String topcolor) {
    this.topcolor = topcolor;
  }

  public Map<String, TemplateData> getData() {
    return data;
  }

  public void setData(Map<String, TemplateData> data) {
    this.data = data;
  }

}
