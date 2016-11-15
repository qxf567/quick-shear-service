package com.quickshear.common.pay.tenpay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quickshear.common.pay.tenpay.client.TenpayHttpClient;
import com.quickshear.common.pay.tenpay.util.MD5Util;
import com.quickshear.common.pay.tenpay.util.TenpayUtil;
import com.quickshear.common.pay.tenpay.util.XMLUtil;


/**
 * 请求处理类
 * 请求处理类继承此类，重写createSign方法即可。
 *
 */
public class RequestHandler {
	private static Logger logger = LoggerFactory.getLogger("PAY");
	
	/** 网关url地址 */
	private String gateUrl;
	
	/** 密钥 */
	private String key;
	
	/** 请求的参数 */
	private SortedMap parameters;
	
	/** debug信息 */
	private String debugInfo;
	private String charset = "GBK";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	/**
	 * 构造函数
	 * @param request
	 * @param response
	 */
	public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
//		this.gateUrl = "https://gw.tenpay.com/gateway/pay.htm";
		this.gateUrl="https://api.mch.weixin.qq.com/pay/unifiedorder";
		this.key = TenpayConfig.partner_key;
		this.parameters = new TreeMap();
		this.debugInfo = "";
	}
	
	/**
	*初始化函数。
	*/
	public void init() {
		//nothing to do
	}

	/**
	*获取入口地址,不包含参数值
	*/
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	*设置入口地址,不包含参数值
	*/
	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	/**
	*获取密钥
	*/
	public String getKey() {
		return key;
	}

	/**
	*设置密钥
	*/
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 获取参数值
	 * @param parameter 参数名称
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * 设置参数值
	 * @param parameter 参数名称
	 * @param parameterValue 参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * 返回所有的参数
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {		
		return this.parameters;
	}

	/**
	*获取debug信息
	*/
	public String getDebugInfo() {
		return debugInfo;
	}
	
	// 特殊字符处理
	public String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, this.charset).replace("+", "%20");
	}
		
	// 获取package带参数的签名包
/*	public String genPackage(SortedMap<String, String> packageParams)
			throws UnsupportedEncodingException {
		String sign = createSign(packageParams);

		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = packageParams.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String,String> entry = it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + UrlEncode(v) + "&");
		}

		// 去掉最后一个&
		String packageValue = sb.append("sign=" + sign).toString();
		logger.debug(String.format("packageValue= %s", packageValue));
		return packageValue;
	}
*/
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = packageParams.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String,String> entry = it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		logger.debug(String.format("md5 sb: %s", sb));
		String sign = MD5Util.MD5Encode(sb.toString(), null)
				.toUpperCase();

		return sign;

	}

	
		// 提交预支付
		public String sendPrepay(SortedMap<String,String> params) {
			String prepayid = null;
		    // 转换成xml
			String postData = XMLUtil.map2Xml(params);
			// 设置链接参数
			String requestUrl = gateUrl;
			logger.info(String.format("post url= %s", requestUrl));
			logger.info(String.format("post data= %s", postData));
			TenpayHttpClient httpClient = new TenpayHttpClient();
			httpClient.setReqContent(requestUrl);
			httpClient.setCharset("UTF-8");
			String resContent = "";
			Map<String, String> map = new HashMap<String, String>();
			if (httpClient.callHttpPost(requestUrl, postData)) {
				resContent = httpClient.getResContent();
				logger.info(String.format("res xml= %s", resContent));
				try {
					map = XMLUtil.doXMLParse(resContent);
					if ("SUCCESS".equals(map.get("return_code"))) {
					    prepayid = map.get("prepay_id");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			return prepayid;
		}
			
		/**
		 * 查询订单信息
		 * @param packageParams
		 * @param token
		 * @return
		 */
		public String orderQuery(SortedMap<String,String> packageParams,String token) {
			String errcode = "";
			// 转换成xml
			String postData = XMLUtil.map2Xml(packageParams);
			// 设置链接参数
			String requestUrl = this.gateUrl;
			logger.info(String.format("post url= %s", requestUrl));
			logger.info(String.format("post data= %s", postData));
			TenpayHttpClient httpClient = new TenpayHttpClient();
			httpClient.setReqContent(requestUrl);
			httpClient.setCharset("UTF-8");
			String resContent = "";
			if (httpClient.callHttpPost(requestUrl, postData)) {
				resContent = httpClient.getResContent();
				logger.info(String.format("res xml= %s", resContent));
				Map<String, String> map = new HashMap<String, String>();
				try {
					map = XMLUtil.doXMLParse(resContent);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if ("SUCCESS".equals(map.get("return_code"))) {
					return "0";
				} else {
					logger.error(String.format("查询订单信息错误,info = %s", map.get("return_msg")));
				}
			}
			return "-1";
		}
		
	/**
	 * 获取带参数的请求URL
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public String getRequestURL() throws UnsupportedEncodingException {
		
		this.createSign();
		
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			
			if(!"spbill_create_ip".equals(k)) {
				sb.append(k + "=" + URLEncoder.encode(v, enc) + "&");
			} else {
				sb.append(k + "=" + v.replace(".", "%2E") + "&");
			}
		}
		//去掉最后一个&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		
		return this.getGateUrl() + "?" + reqPars;
		
	}
	
	public void doSend() throws UnsupportedEncodingException, IOException {
		this.response.sendRedirect(this.getRequestURL());
	}
	
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	protected void createSign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		
		this.setParameter("sign", sign);
		
		//debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign);
		
	}
	
	/**
	*设置debug信息
	*/
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	
	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}
	
	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
