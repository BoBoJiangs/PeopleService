package com.yb.peopleservice.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.server.BaseRequestServer;


/**
 * 类描述: 图片加载工具类
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/10  10:14
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public class ImageLoaderUtil {

    private static final String TAG = "GlideUtil";


    private static RequestOptions options;
    private static RequestOptions options2;

    private static RequestOptions getOptions() {
        if (options == null) {
            options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.base_icon_default)
                    .error(R.drawable.base_icon_default)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }
        return options;
    }

    private static RequestOptions getCircleOptions() {
        if (options2 == null) {
            options2 = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.default_head)
                    .error(R.mipmap.default_head)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }
        return options2;
    }

    /**
     * 加载服务器圆形图片
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView view
     */
    public static void loadServerCircleImage(final Context context, String url, final ImageView imageView) {
        if (!StringUtils.isEmpty(url) && !url.contains("http")) {
            url = BaseRequestServer.getFileUrl(true) + url;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(getCircleOptions())
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }

                });


    }
    public static void loadServerImage(Context context, String url, ImageView imageView) {
        loadServerImage(context,url,imageView,true);
    }

    /**
     * 加载服务器图片
     *
     * @param url       图片地址
     * @param imageView view
     */
    public static void loadServerImage(Context context, String url, ImageView imageView,boolean isPublic) {
        if (!StringUtils.isEmpty(url) && !url.contains("http")) {
            url = BaseRequestServer.getFileUrl(isPublic) + url;
        }
        Glide.with(context)
                .load(url)
                .apply(getOptions())
                .into(imageView);
    }


    /**
     * 加载服务器图片
     *
     * @param url       图片地址
     * @param imageView view
     */
    public static void loadServerImage(Context context, String url, ImageView imageView,
                                       RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param context   上下文
     * @param url       本地图片地址
     * @param imageView view
     */
    public static void loadLocalImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(getOptions())
                .into(imageView);
    }

    /**
     * 加载本地圆形图片
     *
     * @param context   上下文
     * @param url       本地图片地址
     * @param imageView view
     */
    public static void loadLocalCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(getOptions())
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }

                });
    }
}
