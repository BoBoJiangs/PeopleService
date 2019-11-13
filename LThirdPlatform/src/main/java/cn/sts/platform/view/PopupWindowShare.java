package cn.sts.platform.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import cn.sts.base.view.popup.BasePopupWindow;
import cn.sts.platform.R;


/**
 * 第三方分享
 * Created by weilin on 17/2/16.
 */

public class PopupWindowShare extends BasePopupWindow implements View.OnClickListener {

    private ShareListener shareListener;


    public PopupWindowShare(Context context, ShareListener shareListener) {
        super(context);
        this.shareListener = shareListener;
        initView();
    }

    private void initView() {

        contentView.findViewById(R.id.wxFriendLL).setOnClickListener(this);
        contentView.findViewById(R.id.wxTimelineLL).setOnClickListener(this);
    }

    @Override
    public int contentViewResId() {
        return R.layout.popup_share;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (view.getId() == R.id.wxFriendLL) {
            shareListener.shareToWX(SendMessageToWX.Req.WXSceneSession);
        } else if (view.getId() == R.id.wxTimelineLL) {
            shareListener.shareToWX(SendMessageToWX.Req.WXSceneTimeline);
        }
    }


    /**
     * 分享回调
     */
    public interface ShareListener {

        /**
         * 分享到微信
         *
         * @param scene 场景分享到对话:SendMessageToWX.Req.WXSceneSession、分享到朋友圈:SendMessageToWX.Req.WXSceneTimeline、分享到收藏:SendMessageToWX.Req.WXSceneFavorite
         */
        void shareToWX(int scene);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
