package com.quickshear.common.util;

import org.apache.commons.codec.digest.DigestUtils;


public class Md5Utils extends DigestUtils{

    private Md5Utils(){}

    /**
     *
     * @param original 原值
     * @param saltKey 盐值
     * @return
     */
    public static String md5Hex(String original, String saltKey){
       String  saltValue = md5Hex(original + saltKey);
       return saltValue;
    }
}
