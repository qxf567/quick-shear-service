package com.quickshear.service.sms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/** 利用本地文件实现记录openid读取的位置记录 */
@Service
public class StorageService implements InitializingBean {

    public void set(String key, String value) {
	try {
	    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("OPENID"));
	    bos.write((key + ":" + value).getBytes());
	    bos.write("\r\n".getBytes());
	    bos.flush();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    
	}
    }

    public String get() {
	try {
	    BufferedInputStream bis = new BufferedInputStream(new FileInputStream("OPENID"));
	    byte[] b = new byte[100];
	    bis.read(b);
	    return new String(b).trim();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public void afterPropertiesSet() throws Exception {
	File file = new File("OPENID");
	if (!file.exists()) {
	    try {
		file.createNewFile();
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("OPENID"));
		bos.flush();
		bos.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
	StorageService los = new StorageService();
	los.set("openid", "9999999888");
	System.out.println(los.get());
	los.set("openiddd", "dddd");
	System.out.println(los.get());
    }
}