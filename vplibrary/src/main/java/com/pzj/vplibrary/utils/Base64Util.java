package com.pzj.vplibrary.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Key;

import Decoder.BASE64Decoder;

/**
 * Base64加解密
 * 
 */
public class Base64Util {
    public static String key = null;
    private static final String TAG = "Base64Util";

    public static Key getKey(){
        // String appID = "100000007";
        String appID = "100000058";
        for(int i = 16 - appID.length(); i > 0; i--){
            appID += "0";
        }
        return ZXSSOCipher.toKey(appID.getBytes());
    }

    /**
     * 加密字符串
     * 
     * @Title: encodeStr
     * @Description:TODO
     * @param str
     * @return String
     */
    public static String encodeStr(String str){
        String encrtptStr = null;
        if(TextUtils.isEmpty(str)){
            encrtptStr = "";
        } else{
            // return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
            try{
                encrtptStr = Base64.encodeToString(ZXSSOCipher.encrypt(str.getBytes(), getKey()), Base64.DEFAULT);
                // ZteLogUtils.i(TAG, "加密成功:"+encrtptStr);
            } catch(Exception e){
                encrtptStr = "";
                Log.e(TAG, "加密失败:" + e.toString());
            }
        }
        return encrtptStr;
    }

    /**
     * 界面字符串
     * 
     * @Title: decodeStr
     * @Description:TODO
     * @param str
     * @return String
     */
    public static String decodeStr(String str){
        String decodeStr = null;
        if(TextUtils.isEmpty(str)){
            decodeStr = "";
        } else{
            // return new String(Base64.decode(str.getBytes(), Base64.DEFAULT));
            try{
                decodeStr = new String(ZXSSOCipher.decrypt(Base64.decode(str.getBytes(), Base64.DEFAULT), getKey()));
                // ZteLogUtils.i(TAG, "解密成功:"+decodeStr);
            } catch(Exception e){
                decodeStr = "";
                Log.e(TAG, "解密失败:" + e.toString());
            }
        }
        return decodeStr;
    }
    
    //base64字符串转化成图片  
    public static  boolean generateImage(String imgStr, String imgFilePath)
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)
        {  
            return false;  
        }  
    } 

}
