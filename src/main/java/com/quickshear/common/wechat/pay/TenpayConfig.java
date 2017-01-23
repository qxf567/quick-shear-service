package com.quickshear.common.wechat.pay;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/* *
 *类名：TenpayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */
@Component
public class TenpayConfig {
	
        //商户号
	public static String mch_id="1428573602";
	/** 密钥 */
	public static String partner_key="qiansishun2016QIANSISHUN2017qssS";
	/** appid */
	public static String app_id;
	public static String app_secret;
	/** appkey */
//	public static String app_key;
	/** 支付完成后的回调处理页面 */
	public static String notify_url;
	
	/** 银行通道类型  */
	public static String bank_type="WX";
	
	public static String trade_type="APP";
	/** 币种，1人民币  */
	public static String fee_type="CNY";
	/** */
	public static String sign_method="sha1";
	
	public static String input_charset="GBK";
	
	public static String pkcs12_path;
	
	@Value("${wechat.appid}")
	private void setAppId(String appId) {
		TenpayConfig.app_id = appId;
	}

	@Value("${wechat.appsecret}")
	private void setAppSecret(String appSecret) {
		TenpayConfig.app_secret = appSecret;
	}

	@Value("${wechat.notify.url}")
	private void setNotifyUrl(String NotifyUrl){
		TenpayConfig.notify_url = NotifyUrl;
	}

	@Value("${app.instance.config}")
	private void setAppInstanceConfig(String config) {
		TenpayConfig.pkcs12_path = config+File.separator+"tenpay"+File.separator+TenpayConfig.mch_id+".p12";
	}
	
}
