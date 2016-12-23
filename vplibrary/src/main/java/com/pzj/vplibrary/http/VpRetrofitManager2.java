package com.pzj.vplibrary.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pzj on 2016/12/23.
 */

public class VpRetrofitManager2 {
    private static VpRetrofitManager2 instance;
    private  String baseUrl;//基类url地址
    private HashMap<String,String> publicParams;//公共参数
    private HashMap<String,String> headerParams;//头部参数
    private ProgressListener mDownLoadListener;

    private  HashMap<String,Object> serviceMap=new HashMap<String,Object>();

    private static Context mContext;

    private VpRetrofitManager2(){};

    public static VpRetrofitManager2 getInstance(Context context) {
        mContext=context;
        if(instance==null){
            synchronized (VpRetrofitManager2.class){
                if(instance==null){
                    instance=new VpRetrofitManager2();
                }
            }
        }
          return instance;
    }


    public VpRetrofitManager2 baseUrl(String url){
        baseUrl=url;
        return this;
    }
    public VpRetrofitManager2 addHeaderParams(HashMap<String,String> hParams){
        publicParams=hParams;
        return this;
    }
    public VpRetrofitManager2 addPublicParams(HashMap<String,String> pParams){
        publicParams=pParams;
        return this;
    }
    public VpRetrofitManager2 addDownLoadListener(ProgressListener listener){
        mDownLoadListener=listener;
        return this;
    }

    public <T>T createServiceApi(Class<T> cls){
        String key=baseUrl;
        if(!serviceMap.containsKey(baseUrl)){
            serviceMap.put(key,createRefrofit(cls));
        }
        return (T)serviceMap.get(key);

    }

    private <T>T createRefrofit(Class<T> cls){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(cls);
    }

    private OkHttpClient createHttpClient(){
        //设置Http缓存
        Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptro)
                .addInterceptor(logInterceptor)
//              .addInterceptor(cacheInterceptor)
//              .addNetworkInterceptor(cacheInterceptor)
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

        return client;
    }


    private  Interceptor interceptro = new Interceptor()
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

            if(mDownLoadListener==null){
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
                                mDownLoadListener.onFailed(e);
                            }
                        }))
                        .build();
            }


        }
    };

    Handler handle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    mDownLoadListener.onProgress(bundle.getLong("total"),bundle.getLong("progress"));
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
