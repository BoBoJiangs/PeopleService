package com.yb.peopleservice.view.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.presenter.login.LoginPresenter;
import com.yb.peopleservice.model.service.TimeService;
import com.yb.peopleservice.view.activity.main.MainActivity;
import com.yb.peopleservice.view.activity.main.ServiceMainActivity;
import com.yb.peopleservice.view.activity.main.ShopMainActivity;
import com.yb.peopleservice.view.base.BaseActivity;

import butterknife.BindView;
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
    private String phone;
    private String pass;

    @Override
    protected int contentViewResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        //注册时间服务
        Intent intent = new Intent(this, TimeService.class);
        startService(intent);
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
        if (data.getScope() != null && !data.getScope().isEmpty()) {
            if (data.getScope().contains(LoginBean.USER_TYPE)) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (data.getScope().contains(LoginBean.SHOP_TYPE)) {
                startActivity(new Intent(this, ShopMainActivity.class));
            } else if (data.getScope().contains(LoginBean.SERVICE_TYPE)) {
                startActivity(new Intent(this, ServiceMainActivity.class));
            } else {
                ToastUtils.showLong("未知的用户类型,请联系管理员！");
            }
        } else {
            ToastUtils.showLong("未知的用户类型,请联系管理员！");
        }
        finish();
    }

    @Override
    public void loginFail() {

    }


    public void login() {
        phone = phoneTV.getContentText();
        pass = pwdUV.getContentText();
//        if (!RegexUtils.isMobileExact(phone)) {
//            ToastUtils.showLong("请输入正确的电话号码");
//            return;
//        }
        if (StringUtils.isEmpty(pass)) {
            ToastUtils.showLong("请输入密码");
            return;
        }
        loginPresenter.login(phone, pass);
    }
}
