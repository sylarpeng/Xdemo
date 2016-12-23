package com.pzj.xdemo.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.pzj.vplibrary.http.ProgressListener;
import com.pzj.vplibrary.http.VpRetrofitManager;
import com.pzj.vplibrary.http.VpRetrofitManager2;
import com.pzj.vplibrary.utils.IOUtil;
import com.pzj.vplibrary.utils.MD5Util;
import com.pzj.xdemo.base.BaseContents;
import com.pzj.xdemo.base.BaseServiceApi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pzj on 2016/12/14.
 */

public class VpHttpUtil {
    private static String baseUrl= BaseContents.URL_BASE_HTTP;

    /**
     * 接口访问
     * @param context
     * @return
     */
    public static BaseServiceApi getServiceApi(Context context){
        return VpRetrofitManager2.getInstance(context)
                .baseUrl(baseUrl)
                .addHeaderParams(getHeaderParams())
                .addPublicParams(getPublicParams())
                .createServiceApi(BaseServiceApi.class);
//        return VpRetrofitManager.getRetrofit(context,baseUrl,getHeaderParams(),getPublicParams()).create(BaseServiceApi.class);

    }


    /**
     * 文件下载
     * @param context
     * @param baseurl 基类url地址
     * @param srcPath 远程文件路径
     * @param desDir  下载存放目录
     * @param desFileName 下载存放文件名
     * @param listener 下载进度回调
     */
    public static void downloadFile(final Context context, String baseurl, final String srcPath, final String desDir, final String desFileName, final ProgressListener listener){
//        VpRetrofitManager.getRetrofit(context,baseurl,null,null,VpRetrofitManager.NETTYPE_1,listener).create(BaseServiceApi.class)
        VpRetrofitManager2.getInstance(context)
                .baseUrl(baseurl)
                .addDownLoadListener(listener)
                .createServiceApi(BaseServiceApi.class)
                .downloadFile(srcPath)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        String s = IOUtil.writeResponseBodyToDisk(responseBody, desDir, desFileName);
                        return s;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String filePath) {
                        if(listener!=null){
                            listener.onFinished(filePath);
                        }

                    }
                });
    }


    public static void uploadFile(final Context context, String baseurl, List<String> filePaths, HashMap<String,String> map){
        //参数
        final Map<String, RequestBody> paramsMap=new HashMap<String,RequestBody>();
        //文件
        final MultipartBody.Builder builder=new MultipartBody.Builder();

        if(map!=null){
            Iterator<String> iterator = map.keySet().iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                paramsMap.put(key,RequestBody.create(MediaType.parse("form-data"),map.get(key)));
            }
        }

        if(filePaths!=null){
            for(String path:filePaths){
                File f=new File(path);
                builder.addFormDataPart("filedata",f.getName(),RequestBody.create(MultipartBody.FORM,f));
            }
        }

        VpRetrofitManager.getRetrofit(context,baseurl,getUploadHeaderParams(),null,VpRetrofitManager.NETTYPE_2,null).create(BaseServiceApi.class)
                .uploadFile(paramsMap,builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Object o) {
                        Toast.makeText(context, "上传成功"+o.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


    }




    private static HashMap<String,String> getPublicParams(){
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("appkey", "100000058");
        map.put("timestamp", getTimeStamp());
        map.put("digest", toDigest());
        map.put("token", "");
        map.put("origin", "1"); // 1:android 2:ios
        map.put("v", "409");
        return map;
    }
    private static HashMap<String,String> getHeaderParams(){
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        return map;
    }
    private static HashMap<String,String> getUploadHeaderParams(){
        HashMap<String,String> map=new HashMap<String,String>();
        String boundary = "******";
        map.put("Content-type", "multipart/form-data; boundary=" + boundary);
        map.put("Charset", "UTF-8");
        map.put("Connection", "Keep-Alive");
        return map;
    }



    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
    public static String toDigest(){
        String time = getTimeStamp();
        String value ="appkey" + "100000058" + "timestamp" + time + "D835D9F15A628EA4";
        value = MD5Util.stringToMD5(value);
        return value;
    }

}
