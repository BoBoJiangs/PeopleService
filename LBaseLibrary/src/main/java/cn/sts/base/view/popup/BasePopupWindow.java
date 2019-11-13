package cn.sts.base.view.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import butterknife.ButterKnife;
import cn.sts.base.R;


/**
 * PopupWindow基类
 * Created by weilin on 17/3/7.
 */
public abstract class BasePopupWindow extends PopupWindow {

    public Context context;
    public View contentView;

    public BasePopupWindow(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(contentViewResId(), null);
        //设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popup_window_animation);
        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        ButterKnife.bind(this, contentView);
    }

    /**
     * 布局文件id
     */
    public abstract int contentViewResId();

    /**
     * 显示
     */
    public void show(View view) {
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置activity为半透明
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(params);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        //设置activity为不透明
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 1.0f;
        ((Activity) context).getWindow().setAttributes(params);
    }
}
