package com.quickshear.common.wechat.domain;
/** 
 * 复杂按钮（父按钮） 
 *  
 * @author liuyh 
 */  
public class WechatMenuComplexButton extends WechatMenuButton {
	private WechatMenuButton[] sub_button;

	public WechatMenuButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(WechatMenuButton[] sub_button) {
		this.sub_button = sub_button;
	}
}
