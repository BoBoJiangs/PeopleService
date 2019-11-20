package com.yb.peopleservice.view.base;

import android.content.res.Resources;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.gyf.immersionbar.ImmersionBar;
import com.yb.peopleservice.R;

import java.util.ArrayList;

import cn.sts.base.model.entity.TabEntity;

/**
 * 基础ViewPager
 * Created by weilin on 2018/2/28.
 */

public abstract class BaseViewPagerActivity extends cn.sts.base.view.activity.BaseViewPagerActivity {
    @Override
    public void initView() {
        super.initView();
        initImmersionBar();
    }

    @Override
    public void initToolView() {
        super.initToolView();
        if (titleTV != null) {
            titleTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        }
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.white).init();
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

    /**
     * 组装标签的实体，包括名称，图片等
     */
    protected ArrayList<CustomTabEntity> getTabEntityList() {
        ArrayList<CustomTabEntity> mTabEntityList = new ArrayList<>();
        for (String title : mTitles) {
            mTabEntityList.add(new TabEntity(title));
        }
        return mTabEntityList;
    }

    @Override
    public void showViewPagerTitle(int position) {

    }
}
