package com.yb.peopleservice.view.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.presenter.login.LoginPresenter;
import com.yb.peopleservice.view.MainActivity;
import com.yb.peopleservice.view.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

public class LoginActivity extends BaseActivity implements LoginPresenter.ILoginCallback {

    @BindView(R.id.phoneTV)
    UtilityView phoneTV;
    @BindView(R.id.pwdUV)
    UtilityView pwdUV;
    @BindView(R.id.seeIV)
    ImageView seeIV;
    private LoginPresenter loginPresenter;

    @Override
    protected int contentViewResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return loginPresenter = new LoginPresenter(this, this);
    }


    @OnClick({R.id.seeIV, R.id.forgetPwdTV, R.id.loginBtn, R.id.registerTV, R.id.quickLoginTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seeIV:
                break;
            case R.id.forgetPwdTV:
                break;
            case R.id.loginBtn:
                login();
                break;
            case R.id.registerTV:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.quickLoginTV:
                startActivity(new Intent(this, QuickLoginActivity.class));
                break;
        }
    }

    @Override
    public void loginSuccess(LoginBean data) {
        ToastUtils.showLong("登陆成功"+data.getAccess_token());
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void loginFail() {

    }


    public void login() {
        String phone = phoneTV.getContentText();
        String pass = pwdUV.getContentText();
//        if (!RegexUtils.isMobileExact(phone)) {
//            ToastUtils.showLong("请输入正确的电话号码");
//            return;
//        }
        if (StringUtils.isEmpty(pass)) {
            ToastUtils.showLong("请输入密码");
            return;
        }
        loginPresenter.login(phone,pass);
    }
}
