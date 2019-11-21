package com.yb.peopleservice.utils;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yb.peopleservice.GlideApp;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.weight.CenterCropRoundCornerTransform;
import com.youth.banner.loader.ImageLoader;


/** 
 * 图片加载是为了配合图片轮播特用的
 * Created by sts on 2018/5/21.
 *
 * @author daichao
 */

public class GlideImageLoader extends ImageLoader {
    RequestOptions myOptions = new RequestOptions()
            .transform(new CenterCropRoundCornerTransform(SizeUtils.dp2px(5)));
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        GlideApp.with(context)
                .load(path)
                .placeholder(R.drawable.base_icon_default)
                .error(R.drawable.base_icon_default)
                .fallback(R.drawable.base_icon_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(myOptions)
                .into(imageView);
    }
}
