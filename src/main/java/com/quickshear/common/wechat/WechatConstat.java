package com.quickshear.common.wechat;

/**
 * 微信相关url
 * 
 */
public class WechatConstat {

    /**
     * 第一步：用户同意授权，获取code
     */
    public static String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    /**
     * 第二步：通过code换取网页授权access_token
     */
    public static String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}grant_type=authorization_code";

    /**
     * 第三步：刷新access_token
     */
    public static String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid={appid}&grant_type=refresh_token&refresh_token={refreshToken}";

    /**
     * 
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     */
    public static String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token={accessToken}&openid={openid}&lang=zh_CN";

    /**
     * 菜单创建（POST） 限100（次/天）
     */
    public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    
    /**
     * 菜单客服
     */
    public static String KEFU_CREATE_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
    
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

    // 获取access_token的接口地址（GET） 限200（次/天）
     public final static String access_token_url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // 获取jsapi_ticket的接口地址
    public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

}
