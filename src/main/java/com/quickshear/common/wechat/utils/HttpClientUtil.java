package com.quickshear.common.wechat.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;


/**
 * httpClient工具类
 * 
 * @author liuyh
 * 
 */
public class HttpClientUtil {

  private HttpClient httpclient;

  public void setHttpclient(HttpClient httpclient) {
    this.httpclient = httpclient;
  }

  /**
   * 以get方式发送http请求
   * 
   * @param url
   * @return
   */
  public String sendRequest(String url) {
    GetMethod getMethod = new GetMethod(url);
    try {
      getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
          new DefaultHttpMethodRetryHandler());
      // httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
      // getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,6000);
      httpclient.executeMethod(getMethod);
      return getMethod.getResponseBodyAsString();
    } catch (Exception e) {
      e.printStackTrace();
      return "FAIL";
    } finally {
      getMethod.releaseConnection();
    }
  }

  /**
   * 以get方式发送http请求
   * 
   * @param url
   * @return
   */
  public boolean isActive(String url) {
    boolean flag = false;
    GetMethod getMethod = new GetMethod(url);
    try {
      getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
          new DefaultHttpMethodRetryHandler());
      int statusCode = httpclient.executeMethod(getMethod);
      if (statusCode == 200) {
        flag = true;
      }
      return flag;
    } catch (Exception e) {
      e.printStackTrace();
      return flag;
    } finally {
      getMethod.releaseConnection();
    }
  }

  /**
   * 以post方式发送http请求
   * 
   * @param url
   * @return
   */
  public String sendRequestAsPost(String url, NameValuePair[] param) {
    PostMethod postMethod = new PostMethod(url);
    try {
      httpclient = new HttpClient();
      postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
          new DefaultHttpMethodRetryHandler());
      httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(1000);

      postMethod.setRequestHeader("Content-Type",
          "application/x-www-form-urlencoded;charset=utf-8");
      postMethod.setRequestBody(param);

      postMethod.releaseConnection();

      int statusCode = httpclient.executeMethod(postMethod);
      if (statusCode == 200) {
        return postMethod.getResponseBodyAsString();
      } else {
        return "error";
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "500";
    } finally {
      postMethod.releaseConnection();
    }
  }

  /**
   * 以post方式发送http请求
   * 
   * @param url
   * @return
   */
  public String sendRequestAsPost(String url, String postParam) {
    PostMethod postMethod = new PostMethod(url);
    try {
//      httpclient = new HttpClient();
      postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
          new DefaultHttpMethodRetryHandler());
      httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(1000);

      postMethod.setRequestHeader("Content-type", "text/xml; charset=UTF-8");
      if (StringUtils.isNotBlank(postParam)) {
        postMethod.setRequestBody(postParam);
      }
      postMethod.releaseConnection();

      int statusCode = httpclient.executeMethod(postMethod);
      if (statusCode == 200) {
        return postMethod.getResponseBodyAsString();
      } else {
        return "error";
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "500";
    } finally {
      postMethod.releaseConnection();
    }
  }


  /**
   * 验证请求是否是本机发出
   * 
   * @param request true本机发出 false非本机发出
   * @return
   */
  public static boolean isRequestFromSelf(HttpServletRequest request) {
    if (getRemoteIpAddr(request).equals(getLocalIpAddr())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 获取远程客户端IP地址
   * 
   * @param request
   * @return
   */
  public static String getRemoteIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("Proxy-Client-IP");

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("WL-Proxy-Client-IP");

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("HTTP_CLIENT_IP");

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getRemoteAddr();

    if ("0:0:0:0:0:0:0:1".equals(ip.trim()))
      ip = "server";

    // 判断请求是否是本机发出,如果是本机发出,那么就获取本机地址
    if ("127.0.0.1".equals(ip) || ip.equalsIgnoreCase("localhost"))
      ip = getLocalIpAddr();

    return ip;
  }

  /**
   * 获取本机IP地址
   * 
   * @return
   */
  public static String getLocalIpAddr() {
    try {
      Enumeration<?> netInterfaces = NetworkInterface.getNetworkInterfaces();
      InetAddress ip = null;
      String ipAddr = null;
      while (netInterfaces.hasMoreElements()) {
        NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
        ip = (InetAddress) ni.getInetAddresses().nextElement();
        if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
            && ip.getHostAddress().indexOf(":") == -1) {
          ipAddr = ip.getHostAddress();
          break;
        }
      }
      return ipAddr;
    } catch (SocketException e) {
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 判断某回调地址是否是指定的网关地址
   * 
   * @param notifyUrl
   * @param getwayList
   * @return true 是网关 false不是网关地址
   */
  public static boolean isLocalNotifyUrl(String notifyUrl, List<?> getwayList) {
    boolean flag = false;
    for (Object object : getwayList) {
      String getway = (String) object;
      if (notifyUrl.toLowerCase().contains(getway)) {
        flag = true;
        break;
      }
    }
    return flag;
  }
}
