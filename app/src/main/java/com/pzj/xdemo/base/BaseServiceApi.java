package com.pzj.xdemo.base;

import com.pzj.xdemo.login.bean.LoginUserBean;
import com.pzj.xdemo.login.bean.ServerTimeBean;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by pzj on 2016/12/14.
 */

public interface BaseServiceApi {
    public static final String PRE="/api/1.0/";


    /**
     * 图片上传
     * @param map 参数
     * @param body 图片信息
     * @return
     */
    @POST("common/VPFileUpload.ashx/common/VPFileUpload.ashx?appid=100000034&imgtype=10&from=1&action=UpLoadImage&isThumbnail=0")
    @Multipart
    Observable<Object> uploadFile(@PartMap Map<String, RequestBody> map, @Part("filedata") MultipartBody body);


    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url);

    /**
     * 获取服务器时间
     * @return
     */
    @POST(PRE+"SoapApiServer/AppStart/ServerTime")
    Observable<ServerTimeBean> getServerTime();

    /**
     * 登录
     * @return
     */
//    @POST(PRE+"StoreServer/Store/VerifyAccount")
//    @FormUrlEncoded
//    Observable<LoginUserBean> login(@Field("username") String userName, @Field("password") String pwd, @Field("key") String key);


    /**
     * 登录
     * @return
     */
    @POST(PRE+"StoreServer/Store/VerifyAccount")
    @FormUrlEncoded
    Observable<LoginUserBean> login(@FieldMap HashMap<String,String> map);
}
