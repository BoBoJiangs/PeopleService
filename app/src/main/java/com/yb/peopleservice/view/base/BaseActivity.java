package com.yb.peopleservice.view.base;

import android.content.res.Resources;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;

import cn.jmessage.biz.httptask.task.GetEventNotificationTaskMng;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.sts.base.presenter.AbstractPresenter;

/**
 * 基础类
 *
 * @author weilin
 */
public abstract class BaseActivity<P extends AbstractPresenter> extends cn.sts.base.view.activity.BaseActivity<P> {


    @Override
    protected void initView() {
        initImmersionBar();
        JMessageClient.registerEventReceiver(this);
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(cn.sts.base.R.color.white).init();
    }

    public void onEvent(MessageEvent event) {
        //子线程模式
        LogUtils.i("未读条数："+JMessageClient.getAllUnReadMsgCount());
    }

    public void onEventMainThread(GetEventNotificationTaskMng.EventEntity event){
       // 主线程模式  当前登录用户信息被更新事件实体类
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    /**
     * 屏幕适配 传入设计图尺寸动态计算控件以及字体大小
     * @return
     */
    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1334);
    }
}
