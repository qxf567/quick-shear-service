package com.quickshear.common.pay.tenpay;

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
	
	public static String partner;
	
//	public static String partner_password;
	/** 密钥 */
	public static String partner_key;
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
	
	public static String _package="Sign=WXPay";
	public static String input_charset="GBK";
	
	/** Token获取网关地址地址 */
	public static String token_url = "https://api.weixin.qq.com/cgi-bin/token";
	/** 订单查询url */
	public static String order_query_url = "https://api.mch.weixin.qq.com/pay/orderquery";
	/** 提交预支付单网关 */
	public static String prepay_url = "https://api.weixin.qq.com/pay/genprepay";
	/** 申请退款 */
	public static String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/** 统一下单 **/
	public static String unified_order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static String refund_query_url = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	public static String pkcs12_path;
	
	@Value("${pay.tenpay.partner}")
	private void setPartner(String partner) {
		TenpayConfig.partner = partner;
	}
	
//	@Value("${pay.tenpay.partner.password}")
//	private void setPartnerPassword(String partnerPassword) {
//		TenpayConfig.partner_password = partnerPassword;
//	}
	
	@Value("${pay.tenpay.partner.key}")
	private void setPartnerKey(String partnerKey) {
		TenpayConfig.partner_key = partnerKey;
	}

	@Value("${pay.tenpay.app.id}")
	private void setAppId(String appId) {
		TenpayConfig.app_id = appId;
	}

	@Value("${pay.tenpay.app.secret}")
	private void setAppSecret(String appSecret) {
		TenpayConfig.app_secret = appSecret;
	}

	@Value("${pay.tenpay.notify.url}")
	private void setNotifyUrl(String NotifyUrl){
		TenpayConfig.notify_url = NotifyUrl;
	}

	@Value("${app.instance.config}")
	private void setAppInstanceConfig(String config) {
		TenpayConfig.pkcs12_path = config+File.separator+"tenpay"+File.separator+TenpayConfig.partner+".p12";
	}
	
}
