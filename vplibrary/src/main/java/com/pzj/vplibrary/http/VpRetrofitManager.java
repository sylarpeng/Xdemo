package com.pzj.vplibrary.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.pzj.vplibrary.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pzj on 2016/12/14.
 * des Retrofit2网络请求框架封装
 */

public class VpRetrofitManager {
    private static String URL_BASE_HTTP;//基类url地址
    private static HashMap<String,String> publicParams;//公共参数
    private static HashMap<String,String> headerParams;//头部参数
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static Context mContext;

    public static final int NETTYPE_0=0;//普通接口
    public static final int NETTYPE_1=1;//文件上传
    public static final int NETTYPE_2=2;//文件下载
    private static int mNetType;

    private static ProgressListener mListener;

    public static Retrofit getRetrofit(Context context,String baseUrl,HashMap<String,String> headerParams,HashMap<String,String> params){
        return getRetrofit(context,baseUrl,headerParams,params,0,null);
    }
    public static Retrofit getRetrofit(Context context,String baseUrl,HashMap<String,String> hdParams,HashMap<String,String> params,int netType,ProgressListener listener){
        mContext=context;
        URL_BASE_HTTP=baseUrl;
        headerParams=hdParams;
        publicParams=params;
        mNetType=netType;
        mListener=listener;
        initOkhttpClient();
        initRetrofit();
        return mRetrofit;
    }

    /**
     * 初始化okhttpclient配置
     * @return
     */
    private static void initOkhttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null)
        {
            synchronized (VpRetrofitManager.class)
            {
                if (mOkHttpClient == null)
                {
                    //设置Http缓存
                    Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptro)
                            .addInterceptor(logInterceptor)
//                            .addInterceptor(cacheInterceptor)
//                            .addNetworkInterceptor(cacheInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .sslSocketFactory(getSSLSocketFactory())
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String s, SSLSession sslSession) {
                                    return true;
                                }
                            })
                            .build();

                }
            }
        }
    }

    private static void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE_HTTP)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    /**
     * 网络缓存
     */
    private static Interceptor cacheInterceptor = new Interceptor(){

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            if(NetworkUtil.isNetworkConnected(mContext)){//在线缓存
                Response response=chain.proceed(request);
                int maxAge=60; //在线缓存在60s内可读取
                String cacheControl=request.cacheControl().toString();
                Log.d("cache","在线缓存在1分钟内可读取"+cacheControl);

                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control","public, max-age=" + maxAge)
                        .build();

            }else{//离线缓存
                Log.d("cache","离线缓存时间设置");
                request=request.newBuilder()
                        .cacheControl(FORCE_CACHE1)
                        .build();
                Response response=chain.proceed(request);

                return response.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control","public, max-age=50")
                        .build();
            }

        }
    };

    //---修改了系统方法--这是设置在多长时间范围内获取缓存里面
    public static final CacheControl FORCE_CACHE1 = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(10, TimeUnit.SECONDS)//这里是10s，CacheControl.FORCE_CACHE--是int型最大值
            .build();

    private static Interceptor interceptro = new Interceptor()
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {


            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();

            //请求定制：添加请求头
            if(headerParams!=null && headerParams.size()>0){
                Iterator<String> iterator = headerParams.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    String val=headerParams.get(key);
                    requestBuilder.addHeader(key, val);
                }
            }

            if(mNetType==NETTYPE_0){
                FormBody.Builder newFormBody = new FormBody.Builder();
                if(original.body() instanceof FormBody){
                    //添加上原有参数
                    FormBody oidFormBody = (FormBody) original.body();
                    for (int i = 0;i<oidFormBody.size();i++){
                        newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                    }
                }
                //统一添加公共参数
                if(publicParams!=null && publicParams.size()>0){
                    Iterator<String> iterator = publicParams.keySet().iterator();
                    while (iterator.hasNext()){
                        String key = iterator.next();
                        String val=publicParams.get(key);
                        newFormBody.add(key, val);
                    }
                }
                requestBuilder.method(original.method(),newFormBody.build());
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }else{

                requestBuilder.method(original.method(),original.body());
                if(mListener==null){
                    return chain.proceed(requestBuilder.build());
                }else{
                    //文件上传,下载进度进行监听
                    okhttp3.Response orginalResponse =chain.proceed(requestBuilder.build());
                    return orginalResponse.newBuilder()
                            .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                                @Override
                                public void onProgress(long total, long progress) {
                                    //TODO 进度刷新频率待优化
                                    //切换到主线程
                                    Message msg=Message.obtain();
                                    msg.what=1;
                                    Bundle bundle = new Bundle();
                                    bundle.putLong("total", total);
                                    bundle.putLong("progress", progress);
                                    msg.setData(bundle);
                                    handle.sendMessage(msg);
                                }

                                @Override
                                public void onFinished(String path) {

                                }

                                @Override
                                public void onFailed(Exception e) {
                                    mListener.onFailed(e);
                                }
                            }))
                            .build();
                }


            }




        }
    };



    static Handler handle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    mListener.onProgress(bundle.getLong("total"),bundle.getLong("progress"));
                    break;
            }
        }
    };


    public static SSLSocketFactory getSSLSocketFactory(){
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        try{
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            return sslContext
                    .getSocketFactory();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }




}
