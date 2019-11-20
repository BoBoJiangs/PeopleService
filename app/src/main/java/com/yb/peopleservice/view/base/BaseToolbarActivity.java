package com.yb.peopleservice.view.base;

import android.content.res.Resources;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.gyf.immersionbar.ImmersionBar;

/**
 * 基础类
 *
 * @author weilin
 */
public abstract class BaseToolbarActivity extends cn.sts.base.view.activity.BaseToolbarActivity {

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
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(cn.sts.base.R.color.white).init();
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
