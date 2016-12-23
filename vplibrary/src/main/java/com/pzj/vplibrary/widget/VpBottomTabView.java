package com.pzj.vplibrary.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pzj.vplibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzj on 2016/12/16.
 * 底部导航栏
 */

public class VpBottomTabView extends LinearLayout {
    private Context mContext;
    private List<TabItem> tabs=new ArrayList<TabItem>();
    private int textColorNormal;
    private int textColorSelected;
    private int tabBackGroundColor;

    private int marginTop;
    private int marginMiddle;
    private int marginBottom;

    private ViewPager viewPager;

    private OnTabItemClickListener itemClickListener;

    public VpBottomTabView(Context context) {
        this(context,null);
    }

    public VpBottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView();
    }

    private void initView() {
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        this.textColorNormal=Color.BLACK;
        this.textColorSelected=Color.RED;
        this.tabBackGroundColor=Color.WHITE;
        this.marginTop= UIUtil.dip2px(mContext,5);
        this.marginMiddle=UIUtil.dip2px(mContext,3);
        this.marginBottom=UIUtil.dip2px(mContext,3);


    }


    public VpBottomTabView addTab(int normalImg,int selectedImg, String tabName){
        tabs.add(new TabItem(normalImg,selectedImg,tabName));
        return this;
    }

    public VpBottomTabView setTextColorNormal(int color){
        this.textColorNormal=mContext.getResources().getColor(color);
        return this;
    }

    public VpBottomTabView setTextColorSelected(int color){
        this.textColorSelected=mContext.getResources().getColor(color);
        return this;
    }
    public VpBottomTabView setTabBackGroundColor(int color){
        this.tabBackGroundColor=mContext.getResources().getColor(color);
        return this;
    }

    public VpBottomTabView setMarginTop(int margin){
        this.marginTop=margin;
        return this;
    }
    public VpBottomTabView setMarginBottom(int margin){
        this.marginBottom=margin;
        return this;
    }
    public VpBottomTabView setMarginMiddle(int margin){
        this.marginMiddle=margin;
        return this;
    }

    public VpBottomTabView build(){
        this.setBackgroundColor(tabBackGroundColor);
        this.setPadding(0,this.marginTop,0,this.marginBottom);

        if(tabs!=null && tabs.size()>0){
            for(int i=0;i<tabs.size();i++){
                TabItem tabItem = tabs.get(i);
                LinearLayout llItem=new LinearLayout(mContext);
                llItem.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1));
                llItem.setGravity(Gravity.CENTER_HORIZONTAL);
                llItem.setOrientation(LinearLayout.VERTICAL);
                ImageView iv=new ImageView(mContext);
                iv.setImageResource(i==0?tabItem.selectedImg:tabItem.normalImg);
                TextView tv=new TextView(mContext);
                tv.setText(tabItem.tabName);
                tv.setTextColor(i==0?textColorSelected:textColorNormal);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(0,this.marginMiddle,0,0);
                llItem.addView(iv);
                llItem.addView(tv);
                this.addView(llItem);

                final int tempN=i;
                llItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateStyle(tempN);
                        if(itemClickListener!=null){
                            itemClickListener.onItemClick(tempN);
                        }

                        if(viewPager!=null && tempN<=viewPager.getChildCount()-1){
                            viewPager.setCurrentItem(tempN);
                        }
                    }
                });
            }
        }

        return this;
    }

    private void updateStyle(int currentSelectPosition){
        int childCount = this.getChildCount();
        for(int i=0;i<childCount;i++){
            TabItem tabItem = tabs.get(i);
            View childView = this.getChildAt(i);
            if(childView instanceof LinearLayout){
                LinearLayout llItem=(LinearLayout)childView;
                if(llItem.getChildAt(0) instanceof ImageView){
                    ImageView iv=(ImageView)llItem.getChildAt(0);
                    iv.setImageResource(i==currentSelectPosition?tabItem.selectedImg:tabItem.normalImg);
                }
                if(llItem.getChildAt(1) instanceof TextView){
                    TextView tv=(TextView)llItem.getChildAt(1);
                    tv.setTextColor(i==currentSelectPosition?textColorSelected:textColorNormal);
                }

            }
        }
    }

    public interface OnTabItemClickListener{
        void onItemClick(int postion);
    }
    public void setOnTabItemClickListener(OnTabItemClickListener listener){
       this.itemClickListener=listener;
    }

    public void setUpWithViewPager(ViewPager viewPager){
        this.viewPager=viewPager;
    }

    public class TabItem{
        private int normalImg;
        private int selectedImg;
        private String tabName;

        public TabItem( int normalImg,int selectedImg, String tabName) {
            this.normalImg = normalImg;
            this.selectedImg = selectedImg;
            this.tabName = tabName;
        }
    }

}
