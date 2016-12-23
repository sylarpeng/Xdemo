package com.pzj.vplibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.pzj.vplibrary.R;

/**
 * Created by pzj on 2016/12/13.
 * description 图片加载
 */

public class VpGlide {

    /**
     * 加载普通图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImage(Context context,String url,ImageView iv){
        loadImage(context,url,iv,new VpGlideOptions.Builder().build());
    }

    /**
     * 加载圆形图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadCircleImage(Context context,String url,ImageView iv){
        loadCircleImage(context,url,iv,new VpGlideOptions.Builder().build());
    }
    /**
     * 加载圆角图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadRoundImage(Context context,String url,ImageView iv,int radius){
        loadRoundImage(context,url,iv,radius,new VpGlideOptions.Builder().build());
    }

    /**
     * 加载gif图片
     * @param context
     * @param resId
     * @param iv
     */
    public static void loadGifImage(Context context,int resId,ImageView iv){
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public static void loadImage(Context context,String url,ImageView iv,VpGlideOptions options){
        Glide.with(context)
                .load(url)
                .placeholder(options.getPlaceholder())
                .error(options.getError())
                .diskCacheStrategy(options.getDiskCacheStrategy())
                .crossFade()
                .into(iv);
    }
    public static void loadCircleImage(Context context,String url,ImageView iv,VpGlideOptions options){
        Glide.with(context)
                .load(url)
                .placeholder(options.getPlaceholder())
                .error(options.getError())
                .diskCacheStrategy(options.getDiskCacheStrategy())
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .into(iv);
    }
    public static void loadRoundImage(Context context,String url,ImageView iv,int radius,VpGlideOptions options){
        Glide.with(context)
                .load(url)
                .placeholder(options.getPlaceholder())
                .error(options.getError())
                .diskCacheStrategy(options.getDiskCacheStrategy())
                .transform(new GlideRoundTransform(context,radius))
                .crossFade()
                .into(iv);
    }



    /**
     * 圆形图片
     */
    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private  Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }


    /**
     * 圆角图片
     */
    public static class GlideRoundTransform extends BitmapTransformation {

        private  float radius = 0f;

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

}
