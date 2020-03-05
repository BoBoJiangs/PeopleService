package com.yb.peopleservice.view.base;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.LogUtils;
import com.flyco.tablayout.widget.MsgView;
import com.gyf.immersionbar.ImmersionBar;
import com.yb.peopleservice.R;

import butterknife.BindView;
import cn.jmessage.biz.httptask.task.GetEventNotificationTaskMng;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;

/**
 * 基础类
 *
 * @author weilin
 */
public abstract class BaseToolbarActivity extends cn.sts.base.view.activity.BaseToolbarActivity {
    @BindView(R.id.msgMV)
    protected MsgView msgMV;
    @BindView(R.id.msgMV2)
    protected MsgView msgMV2;
    @BindView(R.id.rightIV)
    protected ImageView rightIV;
    @BindView(R.id.rightIV2)
    protected ImageView rightIV2;
    @BindView(R.id.rightLL)
    protected LinearLayout rightLL;

    @Override
    protected void initView() {
        initImmersionBar();
    }

    @Override
    public void initToolView() {
        super.initToolView();
        if (titleTV != null) {
            titleTV.setTextColor(ContextCompat.getColor(getApplicationContext(), cn.sts.base.R.color.black));
        }
        setMsgText(0);
        setMsg2Text(0);
        rightLL.setVisibility(View.GONE);
        JMessageClient.registerEventReceiver(this);
    }

    public void onEvent(MessageEvent event) {
        LogUtils.i("未读条数："+JMessageClient.getAllUnReadMsgCount());
    }

    public void onEventMainThread(GetEventNotificationTaskMng.EventEntity event){
        LogUtils.i("未读条数："+JMessageClient.getAllUnReadMsgCount());
    }


    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(cn.sts.base.R.color.white).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }


    protected void setMsgText(int index) {
        if (index <= 0) {
            msgMV.setVisibility(View.GONE);
        } else {
            msgMV.setVisibility(View.VISIBLE);
        }
    }

    protected void setMsg2Text(int index) {
        if (index <= 0) {
            msgMV2.setVisibility(View.GONE);
        } else {
            msgMV2.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 屏幕适配 传入设计图尺寸动态计算控件以及字体大小
     *
     * @return
     */
    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1334);
    }

}
