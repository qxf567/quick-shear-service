package com.quickshear.common.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	/**
	 * MD5加密，不为空的参数String按顺序连接，并加上KEY后获得散列串
	 * @param datium 要加密的参数序列
	 * @return
	 */
	public static String md5Encrypted(String key, Object... datium)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		StringBuilder sb = new StringBuilder("");
		if (datium != null && datium.length > 0) {
			for (Object data : datium) {
				if (data != null) {
					sb.append(data.toString());
				}
			}
		}
		sb.append(key);

		md.update(sb.toString().getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
}