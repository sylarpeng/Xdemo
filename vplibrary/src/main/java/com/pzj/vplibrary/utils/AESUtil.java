package com.pzj.vplibrary.utils;

import android.util.Base64;

import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author tanping
 *
 */
public class AESUtil {

	// 加密
	public static String Encrypt(String sSrc, String sKey){
		try{
			if (sKey == null) {
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

			return  Base64.encodeToString(encrypted,0);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}


	}

	// 解密
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = Base64.decode(sSrc.getBytes(), 0);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		String cKey = "1234567891234567";
		// 需要加密的字串
		String cSrc = "[\"demo_中文_value1\",\"demo_中文_value2\"]";
		// 加密
		String enString = AESUtil.Encrypt(cSrc, cKey);

		// 解密
		String DeString = AESUtil.Decrypt(enString, cKey);
	}

 
	
	private static String getFromDate(java.util.Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return  simpleDateFormat.format(date);
	}

}