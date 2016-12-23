package com.pzj.xdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pzj.vplibrary.widget.VpSlideBannerView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        VpSlideBannerView bannerView= (VpSlideBannerView) findViewById(R.id.banner);

        ArrayList<String> list=new ArrayList<String>();
        list.add("http://c.hiphotos.baidu.com/zhidao/pic/item/1e30e924b899a9016eb797091f950a7b0208f50b.jpg");
        list.add("http://p2.image.hiapk.com/uploads/allimg/140909/7730-140Z91H333-50.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=3478977478,3284599831&fm=11&gp=0.jpg");
        list.add("http://img2.jiemian.com/101/original/20161111/147886440028980700.jpg");
        bannerView.setDatas(list);
        bannerView.setOnBannerClickListener(new VpSlideBannerView.OnBannerClickListener() {
            @Override
            public void onBannerClick(int postion) {
                Toast.makeText(Main2Activity.this, "点击了"+postion, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
