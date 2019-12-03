package com.yb.peopleservice.view.activity.login;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseActivity;
import com.yb.peopleservice.view.base.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;

public class LoginActivity extends BaseActivity {

    @Override
    protected int contentViewResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
