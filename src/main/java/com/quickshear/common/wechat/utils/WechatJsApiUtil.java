package com.quickshear.common.wechat.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.quickshear.common.config.ShearConfig;
import com.quickshear.common.wechat.WechatManager;
import com.quickshear.common.wechat.domain.AccessToken;

@Component
public class WechatJsApiUtil {
	private static Logger LOGGER = Logger.getLogger(WechatJsApiUtil.class);
    @Autowired
    private ShearConfig config;
    @Autowired
    private WechatManager wechatManager;
    @Autowired
    private WechatUtil wechatUtil;
    // 微信多媒体接口
    private String wxMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s"; 
    
	/**
	 * 设置微信jsApi页面授权参数
	 */
    public void setWxJsApiToModel(Model model, String url) {

		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String nonceStr = "Wm3WZY" + timestamp;
		String sign = wechatManager.getSign(timestamp, nonceStr, url);
		model.addAttribute("appid", config.getAppId());
		model.addAttribute("timestamp", timestamp);
		model.addAttribute("nonceStr", nonceStr);
		model.addAttribute("sign", sign);
	}
    
	/**
	 * 从微信多媒体接口下载图片到本地
	 */
	public Boolean writeImageToDisk(String mediaId,String folder) {
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			// 获取token
			AccessToken accessToken = wechatUtil.getAccessToken();
			// 微信多媒体url
			String mediaUrl = String.format(wxMediaUrl, accessToken.getToken(),
					mediaId);

			// 获取图片流
			URL url = new URL(mediaUrl);
			HttpURLConnection httpURLConn = (HttpURLConnection) url
					.openConnection();
			// 设置网络连接超时时间
			httpURLConn.setConnectTimeout(3000);
			// 设置应用程序要从网络连接读取数据
			// httpURLConn.setDoInput(true);
			// 设置请求方式
			httpURLConn.setRequestMethod("GET");
			// 获取文件名
			// String field=httpURLConn.getHeaderField("Content-Disposition");
			// String fileName = field.substring(field.indexOf("filename")+10,
			// field.length()-1);
			// 设置存放图片文件的本地路径
			String path = config.getFastdfsServerHost()
					+ System.getProperty("file.separator") + folder
					+ System.getProperty("file.separator") + mediaId;
			// 通过输入流获取图片数据
			inputStream = httpURLConn.getInputStream();
			// 保存文件流到本地
			fileOutputStream = new FileOutputStream(path);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = inputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, byteread);
				fileOutputStream.flush();
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("writeImageToDisk error:" + e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return false;
	}

}
