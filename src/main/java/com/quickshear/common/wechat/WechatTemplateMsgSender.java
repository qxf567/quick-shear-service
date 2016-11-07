package com.quickshear.common.wechat;

import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quickshear.common.wechat.domain.AccessToken;
import com.quickshear.common.wechat.domain.WechatTemplateMsg;
import com.quickshear.common.wechat.domain.WechatTemplateMsgSendResult;
import com.quickshear.common.wechat.domain.WechatTemplateOrderStatusMsg;
import com.quickshear.common.wechat.utils.HttpClientUtil;
import com.quickshear.common.wechat.utils.WechatUtil;


/**
 * 微信模板消息发送实现
 * 
 * @author liuyh
 */
@Component
public class WechatTemplateMsgSender {

  private static Logger log = Logger.getLogger(WechatTemplateMsgSender.class);

  @Autowired
  private HttpClientUtil httpClientUtil;
  @Autowired
  private WechatUtil wechatUtil;

  public final static String WECHAT_TEMPLATE_URL =
      "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

  /**
   * 模板消息发送成功的状态值
   */
  private static final int SUCCESS_CODE = 0;

  /**
   * 发送WechatTemplateOrderStatusMsg消息
   * 
   * @param msgJson json格式的微信模板消息,格式可以调用WechatTemplateOrderStatusMsg的getMsg()方法生成
   * @return
   */
  public WechatTemplateMsgSendResult sendTemplateMsg(String msgJson) {
    if (StringUtils.isBlank(msgJson)) {
      WechatTemplateMsgSendResult errorRs = new WechatTemplateMsgSendResult();
      errorRs.setSuccess(false);
      return errorRs;
    }
    log.info("msgJson=" + msgJson);
    // 从数据库中获取token
    AccessToken token = wechatUtil.getAccessToken();

    // 发送模板消息
    WechatTemplateMsgSendResult result = doSend(token.getToken(), msgJson);
    if (!result.isSuccess()) {
      // 如果发送失败了，重新获取token再发送一次
      return doSend(token.getToken(), msgJson);
    }
    return result;
  }

  /**
   * 发送WechatTemplateOrderStatusMsg消息
   * 
   * @param msg
   * @return
   */
  public WechatTemplateMsgSendResult sendTemplateMsg(WechatTemplateOrderStatusMsg msg) {
    log.info("sendTemplateMsg()" + msg.toString());
    // 从数据库中获取token
    AccessToken token = wechatUtil.getAccessToken();

    NameValuePair[] param = msg.toNameValuePairArray();

    ObjectMapper objectMapper = new ObjectMapper();
    String rsParam = null;
    try {
      rsParam = objectMapper.writeValueAsString(msg.getMsg());
    } catch (Exception e) {
      e.printStackTrace();
      log.error("sendTemplateMsg(）", e);
    }

    // 发送模板消息
    // WechatTemplateMsgSendResult result = doSend(token.getToken(), param);
    WechatTemplateMsgSendResult result = doSend(token.getToken(), rsParam);
    if (!result.isSuccess()) {
      // 如果发送失败了，重新获取token再发送一次
      return doSend(token.getToken(), param);
    }
    return result;
  }


  /**
   * 发送WechatTemplateMsg消息
   * 
   * @param msg
   * @return
   */
  public WechatTemplateMsgSendResult sendTemplateMsg(WechatTemplateMsg msg) {
    // 从数据库中获取token
    WechatUtil wechatUtil = new WechatUtil();
    AccessToken token = wechatUtil.getAccessToken();

    NameValuePair[] param = msg.toNameValuePairArray();

    ObjectMapper objectMapper = new ObjectMapper();
    String rsParam = null;
    try {
      rsParam = objectMapper.writeValueAsString(msg);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // 发送模板消息
    // WechatTemplateMsgSendResult result = doSend(token.getToken(), param);
    WechatTemplateMsgSendResult result = doSend(token.getToken(), rsParam);
    if (!result.isSuccess()) {
      // 如果发送失败了，重新获取token再发送一次
      return doSend(token.getToken(), param);
    }
    return result;
  }

  private WechatTemplateMsgSendResult doSend(String token, NameValuePair[] param) {
    WechatTemplateMsgSendResult result = new WechatTemplateMsgSendResult();
    String sendUri = WECHAT_TEMPLATE_URL + token;

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String sendResult = httpClientUtil.sendRequestAsPost(sendUri, param);
      Map<String, Object> sendResultMap = objectMapper.readValue(sendResult, Map.class);
      int code = Integer.valueOf(sendResultMap.get("errcode").toString());
      if (SUCCESS_CODE == code) {
        result.setSuccess(true);
        result.setMsg(sendResultMap.get("msgid").toString());
      } else {
        result.setSuccess(false);
        result.setErrorType(code);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error("doSend()", e);
    } finally {

    }

    return result;
  }

  private WechatTemplateMsgSendResult doSend(String token, String param) {
    WechatTemplateMsgSendResult result = new WechatTemplateMsgSendResult();
    String sendUri = WECHAT_TEMPLATE_URL + token;

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String sendResult = httpClientUtil.sendRequestAsPost(sendUri, param);
      Map<String, Object> sendResultMap = objectMapper.readValue(sendResult, Map.class);
      int code = Integer.valueOf(sendResultMap.get("errcode").toString());
      if (SUCCESS_CODE == code) {
        result.setSuccess(true);
        result.setMsg(sendResultMap.get("msgid").toString());
      } else {
        result.setSuccess(false);
        result.setErrorType(code);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error("doSend()", e);
    } finally {

    }

    return result;
  }

}
