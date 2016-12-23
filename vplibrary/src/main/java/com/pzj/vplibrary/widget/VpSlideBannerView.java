package com.pzj.vplibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pzj.vplibrary.R;
import com.pzj.vplibrary.utils.UIUtil;
import com.pzj.vplibrary.utils.VpGlide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzj on 2016/12/22.
 * 自定义轮播banner
 */

public class VpSlideBannerView extends RelativeLayout {
    private Context mContext;
    private ViewPager mViewPager;
    private SlideAdapter mSlideAdapter;
    private static final int DEFAULT_INTERVALTIME=3*1000;
    private ArrayList<String> mPathList;
    private OnBannerClickListener mClickListener;

    private Handler mHandler;

    private LinearLayout mIndicatorContainer;


    /**
     * 是否自动切换
     */
    private boolean mIsAutoPlay;
    /**
     * 轮播间隔时间
     */
    private int mIntervalTime;

    /**
     * 是否显示指示器
     */
    private boolean mIsShowIndicator;

    /**
     * 指示器显示位置(0:left;1:center;2:right)
     */
    private int  mIndicatorPosition;


    public VpSlideBannerView(Context context) {
        this(context,null);
    }

    public VpSlideBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VpSlideBannerView);
        mIsAutoPlay=ta.getBoolean(R.styleable.VpSlideBannerView_isAutoPlay,true);
        mIntervalTime=ta.getInteger(R.styleable.VpSlideBannerView_intervalTime,DEFAULT_INTERVALTIME);
        mIsShowIndicator=ta.getBoolean(R.styleable.VpSlideBannerView_isShowIndicator,true);
        mIndicatorPosition=ta.getInteger(R.styleable.VpSlideBannerView_indicatorPosition,1);
        ta.recycle();

        initView();
    }

    private void initView() {

        mViewPager=new ViewPager(mContext);
        mSlideAdapter = new SlideAdapter();
        mViewPager.setAdapter(mSlideAdapter);
        mViewPager.setOnTouchListener(mytouchListener);
        mViewPager.setOnPageChangeListener(pagerChangeListener);

        mIndicatorContainer=new LinearLayout(mContext);
        this.addView(mViewPager);

        LayoutParams layoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        if(mIndicatorPosition==0){//左
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        }else if(mIndicatorPosition==1){//中间
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        }else{
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        }
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        layoutParams.setMargins(0,0,0,UIUtil.dip2px(mContext,10));
        mIndicatorContainer.setLayoutParams(layoutParams);
        this.addView(mIndicatorContainer);
    }

    /**
     * 刷新数据
     * @param list
     */
    public void setDatas(ArrayList<String> list){
        this.mPathList=list;
        this.mSlideAdapter.notifyDataSetChanged();
        initIndicatorContainer();
        if(mIsAutoPlay){
            startPlayTask();
        }
    }

    private void initIndicatorContainer(){
        if(!mIsShowIndicator){
            mIndicatorContainer.setVisibility(View.GONE);
        }else{
            mIndicatorContainer.setVisibility(View.VISIBLE);
            if(mPathList!=null){
                for(int i=0;i<mPathList.size();i++){
                    ImageView ivPoint=new ImageView(mContext);
                    ivPoint.setImageResource(i==0?R.drawable.banner_point_selected:R.drawable.banner_point_normal);
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                    params.leftMargin= UIUtil.dip2px(mContext,10);
                    if(i==mPathList.size()-1){
                        params.rightMargin= UIUtil.dip2px(mContext,10);
                    }
                    mIndicatorContainer.addView(ivPoint,params);
                }
            }

        }
    }

    private void updateIndicatorContainer(int currentPosition){
        int childCount=mIndicatorContainer.getChildCount();
        for(int i=0;i<childCount;i++){
            ImageView iv=(ImageView) mIndicatorContainer.getChildAt(i);
            iv.setImageResource(i==currentPosition?R.drawable.banner_point_selected:R.drawable.banner_point_normal);
        }
    }



    /**
     * 开始轮播
     */
    private void startPlayTask(){
        stopPlayTask();
        if(mHandler==null){
            mHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if(mPathList!=null && mPathList.size()>0){
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                    }

                    mHandler.sendEmptyMessageDelayed(0,mIntervalTime);
                }
            };
            mHandler.sendEmptyMessageDelayed(0,mIntervalTime);
        }else{
            mHandler.sendEmptyMessageDelayed(0,mIntervalTime);
        }
    }

    /**
     * 暂停路轮播
     */
    private void stopPlayTask(){
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private ViewPager.OnPageChangeListener pagerChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            position=position%mPathList.size();
            updateIndicatorContainer(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private OnTouchListener mytouchListener= new OnTouchListener() {
        float downX;
        float downY;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN: //手指按下
                    downX=motionEvent.getX();
                    downY=motionEvent.getY();
                    //暂停
                    stopPlayTask();
                    break;

                case MotionEvent.ACTION_MOVE://手指移动
                    //暂停
                    stopPlayTask();
                    break;

                case MotionEvent.ACTION_UP://手指松开
                    float nowX=motionEvent.getX();
                    float nowY=motionEvent.getY();

                    if(Math.abs(nowX-downX)<5 && Math.abs(nowY-downY)<5){
                        //点击
                        if(mClickListener!=null){
                            int position=mViewPager.getCurrentItem()%mPathList.size();
                            mClickListener.onBannerClick(position);
                        }
                    }
                    if(mIsAutoPlay){
                        startPlayTask();
                    }

                    break;

                case MotionEvent.ACTION_CANCEL:
                    startPlayTask();
                    break;

            }


            return false;
        }
    };


    private class SlideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            if(mPathList!=null && mPathList.size()<=1){
                return mPathList.size();
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%mPathList.size();

            ImageView iv=new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(iv);
            VpGlide.loadImage(mContext,mPathList.get(position),iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    public interface OnBannerClickListener{
        void onBannerClick(int postion);
    }

    public void setOnBannerClickListener(OnBannerClickListener listener){
        this.mClickListener=listener;
    }

}
