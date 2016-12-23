package com.pzj.vplibrary.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 功能简介：MD5加密工具类
 * 密码等安全信息存入数据库时，转换成MD5加密形式
 * @author
 * 
 *
 */
public class MD5Util
{
	  public static String MD516(String sourceStr) {
	        String result = "";
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(sourceStr.getBytes());
	            byte b[] = md.digest();
	            int i;
	            StringBuffer buf = new StringBuffer("");
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0)
	                    i += 256;
	                if (i < 16)
	                    buf.append("0");
	                buf.append(Integer.toHexString(i));
	            }
	            result = buf.toString();
	            System.out.println("MD5(" + sourceStr + ",32) = " + result);
	            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
	            
	            return buf.toString().substring(8, 24).toUpperCase();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println(e);
	        }
	        return result;
	    }
	  
	  public static String MD532(String sourceStr) {
	        String result = "";
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(sourceStr.getBytes());
	            byte b[] = md.digest();
	            int i;
	            StringBuffer buf = new StringBuffer("");
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0)
	                    i += 256;
	                if (i < 16)
	                    buf.append("0");
	                buf.append(Integer.toHexString(i));
	            }
	            result = buf.toString();
	          //  System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));       
	            return result.toUpperCase();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println(e);
	        }
	        return result;
	    }

	public static String stringToMD5(String string){
		byte[] hash;

		try{
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
			return null;
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
			return null;
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for(byte b : hash){
			if((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}
}