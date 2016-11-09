package com.quickshear.common.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
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
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.quickshear.common.pay.tenpay.util.Sha1Util;
import com.quickshear.common.wechat.domain.AccessToken;
import com.quickshear.common.wechat.domain.Article;
import com.quickshear.common.wechat.domain.Message;
import com.quickshear.common.wechat.domain.Reply;
import com.quickshear.common.wechat.utils.MyX509TrustManager;
import com.quickshear.common.wechat.utils.WechatUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author liuyh
 * @date 2015-9-11
 *
 */
@Component
public class WechatManager {
  private static Logger log = Logger.getLogger(WechatManager.class);

  // 获取access_token的接口地址（GET） 限200（次/天）
//  public final static String access_token_url =
//      "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

  // 获取jsapi_ticket的接口地址
  public final static String jsapi_ticket_url =
      "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

  /**
   * 菜单创建（POST） 限100（次/天）
   */
  public final static String MENU_CREATE_URL =
      "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

  @Autowired
  private WechatUtil wechatUtil;

  /**
   * 根据token计算signature验证是否为weixin服务端发送的消息
   */
  public boolean checkWeixinReques(HttpServletRequest request, String token) {
    String signature = request.getParameter("signature");
    String timestamp = request.getParameter("timestamp");
    String nonce = request.getParameter("nonce");
    log.debug("----------------checkWeixinReques------------------------");
    log.debug("signature=" + signature + ";timestamp=" + timestamp + ";nonce=" + nonce);
    log.debug("token=" + token);
    if (signature != null && timestamp != null && nonce != null) {
      String[] strSet = new String[] {token, timestamp, nonce};
      java.util.Arrays.sort(strSet);
      String key = "";
      for (String string : strSet) {
        key = key + string;
      }
      String pwd = Sha1Util.getSha1(key);
      return pwd.equals(signature);
    } else {
      return false;
    }
  }

  
  /** 计算signature
  */
 public String getSign(String timestamp,String noncestr,String url) {
	 AccessToken accessToken = wechatUtil.getAccessToken();
	 generateJsapiTicket(accessToken);
	String jspApi = accessToken.getJsapiTicket();
     String[] strSet = new String[] {"jsapi_ticket="+jspApi+"&", "timestamp="+timestamp+"&", "noncestr="+noncestr+"&","url="+url};
     java.util.Arrays.sort(strSet);
     String key = "";
     for (String string : strSet) {
       key = key + string;
     }
     String pwd = Sha1Util.getSha1(key);
     return pwd;
 }
  

  /**
   * 获取jsapi_ticket
   */
  public String getJsapiTicket() {
     AccessToken accessToken = wechatUtil.getAccessToken();
     generateJsapiTicket(accessToken);
    return accessToken.getJsapiTicket();
  }

  public void generateJsapiTicket(AccessToken accessToken) {
    String requestUrl = jsapi_ticket_url.replace("{0}", accessToken.getToken());
    String httpResult = httpRequest(requestUrl, "GET", null);
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> objMap = null;
    if ("" != httpResult) {
      try {
        objMap = objectMapper.readValue(httpResult, Map.class);
        if (null != objMap && objMap.get("errmsg").equals("ok")) {
          accessToken.setJsapiTicket(objMap.get("ticket").toString());
        } else {
          // 获取token失败
          int errcode = objMap != null ? Integer.valueOf(objMap.get("errcode").toString()) : 0;
          String errmsg = objMap != null ? objMap.get("errmsg").toString() : null;
          log.error("获取jsapi_ticket失败 errcode:{" + errcode + "} errmsg:{" + errmsg + "}");
        }
      } catch (JsonParseException e) {
        e.printStackTrace();
        log.error("generateJsapiTicket()", e);
      } catch (JsonMappingException e) {
        e.printStackTrace();
        log.error("generateJsapiTicket()", e);
      } catch (IOException e) {
        e.printStackTrace();
        log.error("generateJsapiTicket()", e);
      } catch (Exception e) {
        e.printStackTrace();
        accessToken.setJsapiTicket(null);
        // 获取token失败
        log.error("获取jsapi_ticket时返回值转换错误  errcode:{" + objMap.get("errcode") + "} errmsg:{"
            + objMap.get("errmsg") + "}", e);
      }
    }
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
      String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", wechatUtil.getAccessToken().getToken());
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
  
}
