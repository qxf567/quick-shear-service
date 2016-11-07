package com.quickshear.common.enumeration;


/**
 * 发短信类型
 * 
 */
public enum SmsTypeEnum {
	
	QUICK_LOGIN("001", "快速登录验证码","%s（验证码），请尽快使用。如非本人操作，请忽略本短信。"), 
	NORMAL_LOGIN("002", "普通登录验证码","%s（验证码），请尽快使用。如非本人操作，请忽略本短信。"),
	INIT_PASSWD("003","初始密码","您于%s访问修好了网站www.xiuhaole.com，已为您创建初始登录密码：%s，请凭手机号（尾号为%s）登录后尽快修改密码。客服电话400-900-6688。"),
	FORGET_PASSWD("004", "忘记密码验证码","%s（验证码），请尽快使用。如非本人操作，请忽略本短信。"),
	//客户短信
	CUST_SMS_DISPATCH("101", "派单","您预约的订单%s将由维修工程师%s为您服务（工号%s，联系电话%s），稍后将联系您，请保持手机畅通。"),
	CUST_SMS_REDISPATCH("102", "改派","您预约的订单%s已变更为维修工程师%s为您服务（工号%s，联系电话%s），稍后将联系您，请保持手机畅通哦。"),
	CUST_SMS_CHANGED_PLAN("103", "改价","您预约的订单%s经维修工程师检测，支付金额已变更为%s元，若有疑问，请即刻拨打客服电话400-900-6688。"),
	CUST_SMS_CANCEL("104", "取消","您预约的订单%s已被取消，如有需要，请登录网站www.xiuhaole.com 重新预约。客服电话400-900-6688。"),
	CUST_SMS_RESERVICE_DISPATCH("105", "售后派单","您预约的售后订单%s将由维修工程师%s为您服务（工号%s，联系电话%s），稍后将联系您，请保持手机畅通。"),
	CUST_SMS_RESERVICE_REDISPATCH("106", "售后改派","您预约的售后订单%s已变更为维修工程师%s为您服务（工号%s，联系电话%s），稍后将联系您，请保持手机畅通哦。"),
	CUST_SMS_RESERVICE_CANCEL("107", "售后取消","您预约的售后订单%s已被取消，如有需要，请登录网站www.xiuhaole.com 重新预约。客服电话400-900-6688。"),
	//工程师短信
	ENGINEER_SMS_DISPATCH("201", "派单","您有新订单！订单%s已分配给您，客户手机号：%s，请及时查看订单详情，尽快与客户联系。客服电话400-900-6688。"),
	ENGINEER_SMS_REDISPATCH("202", "改派","订单%s改派成功！已分配给其他工程师。客服电话400-900-6688。"),
	ENGINEER_SMS_CHANGED_PLAN("203", "改价","订单%s支付金额已变更为%s元，若有疑问，请即刻拨打客服电话400-900-6688。"),
	ENGINEER_SMS_CANCEL("204", "取消","订单%s已取消！客服电话400-900-6688。"),
	ENGINEER_SMS_RESERVICE_DISPATCH("205", "售后派单","您有新售后单！售后单%s已分配给您，客户手机号：%s，请及时查看订单详情，尽快与客户联系。客服电话400-900-6688。"),
	ENGINEER_SMS_RESERVICE_REDISPATCH("206", "售后改派","售后订单%s改派成功！已分配给其他工程师。客服电话400-900-6688。"),
	ENGINEER_SMS_RESERVICE_CANCEL("207", "售后取消","售后订单%s已取消！客服电话400-900-6688。"),
	//工程师微信
	ENGINEER_WECHA_DISPATCH("301", "派单","工程师%s，系统给您派单了！"),
	ENGINEER_WECHA_REDISPATCH("302", "改派","工程师%s，订单改派申请已通过！"),
	ENGINEER_WECHA_CHANGED_PLAN("303", "改价","工程师%s，订单改价申请已通过！"),
	ENGINEER_WECHA_CANCEL("304", "取消","工程师%s，您的服务订单已取消成功！"),
	ENGINEER_WECHA_RESERVICE_DISPATCH("305", "售后派单","工程师%s，您有新的售后订单！"),
	ENGINEER_WECHA_RESERVICE_REDISPATCH("306", "售后改派","工程师%s，售后订单改派申请已通过！"),
	ENGINEER_WECHA_RESERVICE_CANCEL("307", "售后取消","工程师%s，您的服务售后订单已取消成功！"),
	ENGINEER_WECHA_COMPLETION("308", "服务完成","工程师%s，您的订单已服务完成，等待用户付款！"),
	ENGINEER_WECHA_RESERVICE_COMPLETION("309", "售后服务完成","工程师%s，您的售后订单已服务完成！"),
	ENGINEER_WECHA_TRADE_COMPLETION("310", "交易完成","工程师%s，您的订单已交易完成！"),
	ENGINEER_WECHA_REMARK("333", "remark","如有疑问，请拨打客服电话400-900-6688。");
	
	private String value;

	private String desc;
	
	private String content;

	private SmsTypeEnum(String value, String desc,String content) {
		this.value = value;
		this.desc = desc;
		this.content = content;
	}

	public static SmsTypeEnum getRoleEnum(String value) {
		if (value == null) {
			return null;
		}
		SmsTypeEnum[] enumArray = SmsTypeEnum.values();
		for (SmsTypeEnum c : enumArray) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getContent(){
		return content;
	}
}
