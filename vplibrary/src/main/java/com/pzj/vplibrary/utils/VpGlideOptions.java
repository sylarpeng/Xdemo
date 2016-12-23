package com.pzj.vplibrary.utils;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pzj.vplibrary.R;

/**
 * Created by pzj on 2016/12/13.
 */

public class VpGlideOptions {
    /**
     * 占位符
     */
    private int placeholder;

    /**
     * 加载失败图标
     */
    private int error;

    /**缓存策略
     * all:缓存源资源和转换后的资源none:不作任何磁盘缓存source:缓存源资源result：缓存转换后的资源
     */
    private DiskCacheStrategy diskCacheStrategy;
    /**
     * 缩略图支持(0.1~1)
     */
    private float thumbnail;
    /**
     * 加载尺寸：宽
     */
    private int overrideWidth;
    /**
     * 加载尺寸：高
     */
    private int overrideHeight;


    public VpGlideOptions(VpGlideOptions.Builder builder) {
        this.placeholder = builder.placeholder;
        this.error=builder.error;
        this.diskCacheStrategy=builder.diskCacheStrategy;
        this.thumbnail=builder.thumbnail;
        this.overrideWidth=builder.overrideWidth;
        this.overrideHeight=builder.overrideHeight;
    }


    public int getPlaceholder() {
        return placeholder;
    }

    public int getError() {
        return error;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public int getOverrideWidth() {
        return overrideWidth;
    }

    public int getOverrideHeight() {
        return overrideHeight;
    }

    public static class Builder{

        //设置默认值
        private int placeholder= R.drawable.ic_launcher;
        private int error=R.drawable.ic_launcher;
        private DiskCacheStrategy diskCacheStrategy= DiskCacheStrategy.ALL;
        private float thumbnail=0.1f;
        private int overrideWidth=0;
        private int overrideHeight=0;

        public VpGlideOptions.Builder setPlaceholder(int placeholder){
            this.placeholder=placeholder;
            return this;
        }
        public VpGlideOptions.Builder setErrorholder(int errorHolder){
            this.error=errorHolder;
            return this;
        }
        public VpGlideOptions.Builder setDiskCacheStrategy(DiskCacheStrategy diskCacheStrategy){
            this.diskCacheStrategy=diskCacheStrategy;
            return this;
        }
        public VpGlideOptions.Builder setThumbnail(float thumbnail){
            this.thumbnail=thumbnail;
            return this;
        }
        public VpGlideOptions.Builder setOverride(int width,int heiht){
            this.overrideWidth=width;
            this.overrideHeight=heiht;
            return this;
        }


        public VpGlideOptions build(){
            return new VpGlideOptions(this);
        }
    }
}
