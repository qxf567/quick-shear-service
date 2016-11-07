package com.quickshear.common.wechat.domain;
/** 
 * 普通按钮（子按钮） 
 *  
 * @author liuyh 
 */
public class WechatMenuCommonButton extends WechatMenuButton {
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
