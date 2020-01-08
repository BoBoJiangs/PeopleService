package com.yb.peopleservice.view.activity.common;

import androidx.appcompat.app.AppCompatActivity;
import cn.sts.base.presenter.AbstractPresenter;

import android.os.Bundle;

import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

public class MyIncomeActivity extends BaseToolbarActivity {


    @Override
    protected int contentViewResID() {
        return R.layout.activity_my_income;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return null;
    }
}
