package cn.sts.platform.presenter.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.blankj.utilcode.util.ToastUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import cn.sts.base.util.BitmapUtil;
import cn.sts.platform.util.ThirdPlatformUtil;

/**
 * 分享
 * Created by weilin on 2019-07-10.
 */
public class SharePresenter {

    private static final int THUMB_SIZE = 150;

    private Context context;

    public SharePresenter(Context context) {
        this.context = context;
    }

    /**
     * 分享链接到微信
     *
     * @param title        网页标题
     * @param description  网页描述
     * @param url          网页地址
     * @param imageUrl     图片地址
     * @param failImageRes 图片地址获取图片失败后默认显示的资源图片
     * @param scene        分享的目标场景：分享到对话:SendMessageToWX.Req.WXSceneSession、分享到朋友圈:SendMessageToWX.Req.WXSceneTimeline、分享到收藏:SendMessageToWX.Req.WXSceneFavorite
     */
    public void shareUrlToWx(String title, String description, String url, String imageUrl, int failImageRes, Integer scene) {

        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                shareUrlToWx(title, description, url, bitmap, scene);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                shareUrlToWx(title, description, url, BitmapFactory.decodeResource(context.getResources(), failImageRes), scene);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    private void shareUrlToWx(String title, String description, String url, Bitmap imageBitmap, Integer scene) {
        Bitmap thumbBmp = Bitmap.createScaledBitmap(imageBitmap, THUMB_SIZE, THUMB_SIZE, true);

        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //用 WXMediaMessage 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);
//        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        //分享的目标场景
        req.scene = scene == null ? SendMessageToWX.Req.WXSceneSession : scene;

        //调用api接口，发送数据到微信
        boolean status = ThirdPlatformUtil.getIWXAPI().sendReq(req);
        if (!status) {
            ToastUtils.showLong("分享失败");
        }
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
