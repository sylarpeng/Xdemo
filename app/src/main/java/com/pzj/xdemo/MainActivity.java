package com.pzj.xdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.pzj.vplibrary.http.ProgressListener;
import com.pzj.vplibrary.utils.AESUtil;
import com.pzj.vplibrary.utils.Base64Util;
import com.pzj.vplibrary.utils.LoadingProgressDialog;
import com.pzj.vplibrary.utils.VpGlide;
import com.pzj.vplibrary.utils.VpGlideOptions;
import com.pzj.vplibrary.widget.VpBottomTabView;
import com.pzj.xdemo.login.bean.LoginUserBean;
import com.pzj.xdemo.login.bean.ServerTimeBean;
import com.pzj.xdemo.utils.VpHttpUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.fl_bottomView)
    FrameLayout flBottomView;

    private Context mContext;

    int i = 0;

    private String tempPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mContext = this;
        initBottomView();

    }

    private void initBottomView() {
        VpBottomTabView bottomView=new VpBottomTabView(this)
                .addTab(R.mipmap.ic_home_normal,R.mipmap.ic_home_focus,"首页")
                .addTab(R.mipmap.ic_shop_normal,R.mipmap.ic_shop_focus,"店铺")
                .addTab(R.mipmap.ic_service_normal,R.mipmap.ic_service_focus,"我的")
                .setTabBackGroundColor(R.color.tab_background_color)
                .setTextColorNormal(R.color.tab_normal_color)
                .setTextColorSelected(R.color.tab_selected_color)
                .build();

        //添加点击监听
        bottomView.setOnTabItemClickListener(new VpBottomTabView.OnTabItemClickListener() {
            @Override
            public void onItemClick(int postion) {
                Toast.makeText(mContext, "点击了"+postion, Toast.LENGTH_SHORT).show();
            }
        });
        flBottomView.addView(bottomView);
    }


    @OnClick({R.id.iv, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onClick(View view) {
        String url = "http://img4.imgtn.bdimg.com/it/u=1368451630,1525355250&fm=11&gp=0.jpg";
        String url2 = "http://img4.imgtn.bdimg.com/it/u=1368451630,1525355250&fm1=11&gp=0.jpg";
        VpGlideOptions op = new VpGlideOptions.Builder()
                .setPlaceholder(R.mipmap.ic_default_banner)
                .setErrorholder(R.mipmap.ic_default_banner)
                .build();
        switch (view.getId()) {
            case R.id.btn1:
                VpGlide.loadImage(mContext, url, iv);
                break;
            case R.id.btn2:
                VpGlide.loadImage(mContext, url2, iv, op);
                break;
            case R.id.btn3:
                VpGlide.loadCircleImage(mContext, url, iv);
                break;
            case R.id.btn4:
                VpGlide.loadRoundImage(mContext, url, iv, 10);
                break;
            case R.id.btn5:
                VpGlide.loadGifImage(mContext, R.drawable.splash_loading, iv);
                break;
            case R.id.btn6:
                LoadingProgressDialog.showProgressDialog(this);
                VpHttpUtil.getServiceApi(mContext).getServerTime()
                        .flatMap(new Func1<ServerTimeBean, Observable<LoginUserBean>>() {
                            @Override
                            public Observable<LoginUserBean> call(ServerTimeBean serverTimeBean) {
                                return getLoginInfo(serverTimeBean);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LoginUserBean>() {
                            @Override
                            public void onCompleted() {
                                LoadingProgressDialog.closeProgressDialog();
                                Toast.makeText(mContext, "请求完成", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                LoadingProgressDialog.closeProgressDialog();
                                e.printStackTrace();
                                Toast.makeText(mContext, "访问网络失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(LoginUserBean loginUserBean) {
                                Toast.makeText(mContext, loginUserBean.getResultCode() == 1000 ? "登录成功" + loginUserBean.getData().getStoreName() : loginUserBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })

                ;

                break;


            case R.id.btn7://下载
                String baseUrl = "http://pic36.nipic.com/";
                String path = "20131128/11748057_141932278338_2.jpg";
                VpHttpUtil.downloadFile(mContext, baseUrl, path, mContext.getFilesDir().getAbsolutePath(), "test.jpg", new ProgressListener() {
                    @Override
                    public void onProgress(long total, long progress) {
                        Log.d("dd", "total=" + total + ",,,progress=" + progress + "-----currentThread=" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onFinished(String path) {
                        tempPath = path;
                        Log.d("dd", "onFinished" + path + "-----currentThread=" + Thread.currentThread().getName());
                        iv.setImageBitmap(BitmapFactory.decodeFile(path));
                        Toast.makeText(mContext, "下载成功=" + path, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(Exception e) {
                        e.printStackTrace();
                    }
                });

                break;

            case R.id.btn8://上传
                if (TextUtils.isEmpty(tempPath)) {
                    Toast.makeText(mContext, "请先选择文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> srcFileList = new ArrayList<String>();//要上传的图片地址列表
                srcFileList.add(tempPath);
                HashMap<String, String> hm = new HashMap<String, String>();

                baseUrl = "https://hxb.vpclub.cn/";
                VpHttpUtil.uploadFile(mContext, baseUrl, srcFileList, hm);

                break;

            case R.id.btn9:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;

        }
    }

    private void dd(){
//        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
//         rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);



    }

    private Observable<LoginUserBean> getLoginInfo(ServerTimeBean timeBean) {
        String mUserName = "18688451605";
        String mPassWord = "a1234567";
        if (timeBean == null)
            return null;
        if (timeBean.getResultCode() == 1000) {
            try {
                String serverTime = AESUtil.Decrypt(timeBean.getData(), "1000000580000001");
                String key = mUserName + serverTime.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
                key = AESUtil.Encrypt(key, "1000000580000001");
                String parUsername = Base64Util.encodeStr(mUserName);
                String parPwd = Base64Util.encodeStr(mPassWord);

                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("username", parUsername);
                hm.put("password", parPwd);
                hm.put("key", key);
                return VpHttpUtil.getServiceApi(mContext).login(hm);
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        } else {
            Toast.makeText(mContext, timeBean.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }


}
