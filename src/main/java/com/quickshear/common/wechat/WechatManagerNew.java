package com.quickshear.common.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.quickshear.common.util.JsonUtil;
import com.quickshear.common.wechat.domain.Article;
import com.quickshear.common.wechat.domain.Message;
import com.quickshear.common.wechat.domain.Reply;
import com.quickshear.common.wechat.pay.TenpayConfig;
import com.quickshear.common.wechat.utils.MyX509TrustManager;
import com.quickshear.common.wechat.utils.WechatUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

@Component
public class WechatManagerNew {
  private static Logger log = Logger.getLogger(WechatManagerNew.class);

  @Autowired
  private WechatUtil wechatUtil;

  /**
   * 公众号可以通过微信网页授权机制，来获取用户基本信息
   * 
   */
  public Map<String,String> getUser(HttpServletRequest request,String code) {
     String accessTokenUrl = WechatConstat.accessTokenUrl;
     accessTokenUrl = accessTokenUrl.replace("{appid}", TenpayConfig.app_id)
	     .replace("{secret}", TenpayConfig.app_secret)
	     .replace("{code}", code);
     
     // 第二步：通过code换取网页授权access_token
     String accessTokenContent = httpRequest(accessTokenUrl,"GET",null);
     //	取到json串中的refresh_token值
     TypeReference<Map<String, String>> ref = new TypeReference<Map<String, String>>() {};
     Map<String, String> map = (Map<String, String>) JsonUtil.json2GenericObject(accessTokenContent, ref);
     String refreshToken = map.get("refresh_token");
     
     //第三步：刷新access_token
     String refreshTokenUrl = WechatConstat.refreshTokenUrl;
     refreshTokenUrl = refreshTokenUrl.replace("{appid}", TenpayConfig.app_id)
	     .replace("{refreshToken}", refreshToken);
     
     String refreshTokenContent = httpRequest(refreshTokenUrl,"GET",null);
     
     //	取到json串中的refresh_token值
     TypeReference<Map<String, String>> ref2 = new TypeReference<Map<String, String>>() {};
     Map<String, String> map2 = (Map<String, String>) JsonUtil.json2GenericObject(refreshTokenContent, ref2);
     if(map2.get("errcode") != null){
	 //@TODO  有错误
     }
     String accessToken = map2.get("access_token");
     String openid = map2.get("openid");
     
     
     //第四步：拉取用户信息(需scope为 snsapi_userinfo)
   //第三步：刷新access_token
     String userInfoUrl = WechatConstat.userInfoUrl;
     userInfoUrl = userInfoUrl.replace("{accessToken}", accessToken)
	     .replace("{openid}", openid);
     
     String userInfoContent = httpRequest(userInfoUrl,"GET",null);
     
     //	取到json串中的refresh_token值
     TypeReference<Map<String, String>> ref3 = new TypeReference<Map<String, String>>() {};
     Map<String, String> map3 = (Map<String, String>) JsonUtil.json2GenericObject(userInfoContent, ref3);
     if(map3.get("errcode") != null){
	 //@TODO  有错误
     }
     
     return map3;
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

  private static XStream xstream = new XStream(new XppDriver() {
    public HierarchicalStreamWriter createWriter(Writer out) {
      return new PrettyPrintWriter(out) {
        // 对所有xml节点的转换都增加CDATA标记
        boolean cdata = true;

        public void startNode(String name, Class clazz) {
          super.startNode(name, clazz);
        }

        protected void writeText(QuickWriter writer, String text) {
          if (cdata) {
            writer.write("<![CDATA[");
            writer.write(text);
            writer.write("]]>");
          } else {
            writer.write(text);
          }
        }
      };
    }
  });

  public static int getByteSize(String content) {
    int size = 0;
    if (null != content) {
      try {
        // 汉字採用utf-8编码时占3个字节
        size = content.getBytes("utf-8").length;
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return size;
  }

  /**
   * 将回复消息对象转换成xml字符串
   *
   * @param reply 回复消息对象
   * @return 返回符合微信接口的xml字符串
   */
  public static String replyToXml(Reply reply) {
    String type = reply.getMsgType();
    if (Reply.TEXT.equals(type)) {
      xstream.omitField(Reply.class, "articles");
      xstream.omitField(Reply.class, "articleCount");
      xstream.omitField(Reply.class, "musicUrl");
      xstream.omitField(Reply.class, "hQMusicUrl");
    } else if (Reply.MUSIC.equals(type)) {
      xstream.omitField(Reply.class, "articles");
      xstream.omitField(Reply.class, "articleCount");
      xstream.omitField(Reply.class, "content");
    } else if (Reply.NEWS.equals(type)) {
      xstream.omitField(Reply.class, "content");
      xstream.omitField(Reply.class, "musicUrl");
      xstream.omitField(Reply.class, "hQMusicUrl");
    }else if (Reply.CS.equals(type)) {
    	xstream.alias("xml", reply.getClass());
    }
    xstream.autodetectAnnotations(true);
    xstream.alias("xml", reply.getClass());
    xstream.alias("item", new Article().getClass());
    return xstream.toXML(reply);
  }

  /**
   * 解析request中的xml 并将数据存储到一个Map中返回
   *
   * @param request
   */
  public static Map<String, String> parseXml(HttpServletRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      InputStream inputStream = request.getInputStream();
      SAXReader reader = new SAXReader();
      Document document = reader.read(inputStream);
      Element root = document.getRootElement();
      List<Element> elementList = root.elements();
      for (Element e : elementList)
        // 遍历xml将数据写入map
        map.put(e.getName(), e.getText());
      inputStream.close();
      inputStream = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return map;
  }

  /**
   * 存储数据的Map转换为对应的Message对象
   *
   * @param map 存储数据的map
   * @return 返回对应Message对象
   */
  public static Message mapToMessage(Map<String, String> map) {
    if (map == null)
      return null;
    String msgType = map.get("MsgType");
    Message message = new Message();
    message.setToUserName(map.get("ToUserName"));
    message.setFromUserName(map.get("FromUserName"));
    if (null != map.get("CreateTime") && map.get("CreateTime").equals("")) {
      message.setCreateTime(new Date(Long.parseLong(map.get("CreateTime"))));
    }
    message.setMsgType(msgType);
    message.setMsgId(map.get("MsgId"));
    if (msgType.equals(Message.TEXT)) {
      message.setContent(map.get("Content"));
    } else if (msgType.equals(Message.IMAGE)) {
      message.setPicUrl(map.get("PicUrl"));
    } else if (msgType.equals(Message.LINK)) {
      message.setTitle(map.get("Title"));
      message.setDescription(map.get("Description"));
      message.setUrl(map.get("Url"));
    } else if (msgType.equals(Message.LOCATION)) {
      message.setLocationX(map.get("Location_X"));
      message.setLocationY(map.get("Location_Y"));
      message.setScale(map.get("Scale"));
      message.setLabel(map.get("Label"));
    } else if (msgType.equals(Message.EVENT)) {
      message.setEvent(map.get("Event"));
      message.setEventKey(map.get("EventKey"));
    }
    return message;
  }

  /**
   * 创建菜单
   * 
   * @param menu 菜单实例
   * @return 0表示成功，其他值表示失败
   */
  public int createMenu(String menuJsonStr) {
    int result = -1;
    if (StringUtils.isBlank(menuJsonStr)) {
      return result;
    }

    // 拼装创建菜单的url
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> objMap = null;
    try {
      String url = WechatConstat.MENU_CREATE_URL.replace("ACCESS_TOKEN", wechatUtil.getAccessToken().getToken());
      String httpResult = httpRequest(url, "POST", menuJsonStr);
      log.error("----->httpResult="+httpResult);
      // 如果请求成功
      if ("" != httpResult) {
        objMap = objectMapper.readValue(httpResult, Map.class);
        result = Integer.valueOf(objMap.get("errcode").toString());
        log.error("----->result="+result);
        if (0 != result) {
          log.error("创建菜单失败 errcode:{" + objMap.get("errcode") + "} errmsg:{" + objMap.get("errmsg")
              + "}");
        }
      }
    } catch (JsonParseException e) {
      e.printStackTrace();
      log.error("createMenu()", e);
    } catch (JsonMappingException e) {
      e.printStackTrace();
      log.error("createMenu()", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("createMenu()", e);
    } catch (Exception e) {
      e.printStackTrace();
      // 获取token失败
      log.error(
          "获取token失败 errcode:{" + objMap.get("errcode") + "} errmsg:{" + objMap.get("errmsg") + "}",
          e);
    }
    return result;
  }
  
  
  /**
   * 创建客服
   * 
   * @param menu 菜单实例
   * @return 0表示成功，其他值表示失败
   */
  public int createKeFu(String kefuJsonStr) {
    int result = -1;
    if (StringUtils.isBlank(kefuJsonStr)) {
      return result;
    }

    // 拼装创建菜单的url
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> objMap = null;
    try {
      String url = WechatConstat.KEFU_CREATE_URL.replace("ACCESS_TOKEN", wechatUtil.getAccessToken().getToken());
      String httpResult = httpRequest(url, "POST", kefuJsonStr);
      log.error("----->httpResult="+httpResult);
      // 如果请求成功
      if ("" != httpResult) {
        objMap = objectMapper.readValue(httpResult, Map.class);
        result = Integer.valueOf(objMap.get("errcode").toString());
        log.error("----->result="+result);
        if (0 != result) {
          log.error("创建客服失败 errcode:{" + objMap.get("errcode") + "} errmsg:{" + objMap.get("errmsg")
              + "}");
        }
      }
    } catch (JsonParseException e) {
      e.printStackTrace();
      log.error("createKeFu()", e);
    } catch (JsonMappingException e) {
      e.printStackTrace();
      log.error("createKeFu()", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("createKeFu()", e);
    } catch (Exception e) {
      e.printStackTrace();
      // 获取token失败
      log.error(
          "获取token失败 errcode:{" + objMap.get("errcode") + "} errmsg:{" + objMap.get("errmsg") + "}",
          e);
    }
    return result;
  }
  
}
