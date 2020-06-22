package com.yb.peopleservice.view.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.alipay.sdk.app.AuthTask;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.enums.LoginType;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.eventbean.EventLoginBean;
import com.yb.peopleservice.model.presenter.login.LoginPresenter;
import com.yb.peopleservice.model.presenter.login.WeChatLoginPresenter;
import com.yb.peopleservice.model.service.TimeService;
import com.yb.peopleservice.utils.OrderInfoUtil2_0;
import com.yb.peopleservice.view.activity.main.MainActivity;
import com.yb.peopleservice.view.activity.main.ServiceMainActivity;
import com.yb.peopleservice.view.activity.main.ShopMainActivity;
import com.yb.peopleservice.view.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.app.AppManager;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;
import cn.sts.platform.presenter.login.ThirdPlatformLoginPresenter;

public class LoginActivity extends BaseActivity implements LoginPresenter.ILoginCallback {

    @BindView(R.id.phoneTV)
    UtilityView phoneTV;
    @BindView(R.id.pwdUV)
    UtilityView pwdUV;
    @BindView(R.id.seeIV)
    ImageView seeIV;
    @BindView(R.id.radioRg)
    RadioGroup radioGroup;
    private LoginPresenter loginPresenter;
    private String phone;
    private String pass;
    private boolean isShowPwd = false;//是否显示密码
    private UserType type = UserType.CUSTOMER;
    private WeChatLoginPresenter platformLoginPresenter;
    @Override
    protected int contentViewResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        platformLoginPresenter = new WeChatLoginPresenter(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbl:
                        type = UserType.SHOP;
                        break;
                    case R.id.rb2:
                        type = UserType.STAFF;
                        break;
                    case R.id.rb3:
                        type = UserType.CUSTOMER;
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventLoginBean bean){
//        ToastUtils.showLong(bean.getAuthorCode());
        LogUtils.e(bean.getAuthorCode());
        loginPresenter.platformLogin(bean.getAuthorCode(),type.getValue());
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return loginPresenter = new LoginPresenter(this, this);
    }


    @OnClick({R.id.seeIV, R.id.forgetPwdTV, R.id.loginBtn,
            R.id.registerTV, R.id.quickLoginTV,R.id.wxIV,R.id.zfbIV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seeIV:
                if (isShowPwd) {
                    isShowPwd = false;
                    seeIV.setImageResource(R.mipmap.login_ps1);
                    pwdUV.getInputEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    isShowPwd = true;
                    seeIV.setImageResource(R.mipmap.login_ps);
                    pwdUV.getInputEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.forgetPwdTV:
                startActivity(new Intent(this,PasswordResetActivity.class));
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
            case R.id.wxIV:
                LoginType.setLoginType(LoginType.WECHAT);
                platformLoginPresenter.wxLogin();
                break;
            case R.id.zfbIV:
                LoginType.setLoginType(LoginType.ALIPAY);
                loginPresenter.authInfo();
                break;
        }
    }

    @Override
    public void loginSuccess(User data) {
        AppManager.getAppManager().finishAllActivity();
        if (data.getAccountType() != null && !data.getAccountType().isEmpty()) {
            if (data.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (data.getAccountType().contains(UserType.SHOP.getValue())) {
                startActivity(new Intent(this, ShopMainActivity.class));
            } else if (data.getAccountType().contains(UserType.STAFF.getValue())) {
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
        if (type == null) {
            ToastUtils.showLong("请选择账号类型");
            return;
        }
        if (StringUtils.isEmpty(pass)) {
            ToastUtils.showLong("请输入密码");
            return;
        }
        loginPresenter.login(phone, pass,type.getValue());
    }
}
