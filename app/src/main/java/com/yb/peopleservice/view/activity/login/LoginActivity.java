package com.yb.peopleservice.view.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.view.MainActivity;
import com.yb.peopleservice.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phoneTV)
    UtilityView phoneTV;
    @BindView(R.id.pwdUV)
    UtilityView pwdUV;
    @BindView(R.id.seeIV)
    ImageView seeIV;

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


    @OnClick({R.id.seeIV, R.id.forgetPwdTV, R.id.loginBtn, R.id.registerTV, R.id.quickLoginTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seeIV:
                break;
            case R.id.forgetPwdTV:
                break;
            case R.id.loginBtn:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerTV:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.quickLoginTV:
                startActivity(new Intent(this,QuickLoginActivity.class));
                break;
        }
    }
}
