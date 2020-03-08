package com.yb.peopleservice.view.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.presenter.login.LoginPresenter;
import com.yb.peopleservice.model.presenter.login.RegisterPresenter;
import com.yb.peopleservice.utils.MyTimer;
import com.yb.peopleservice.view.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;
import jiguang.chat.utils.ToastUtil;

public class RegisterActivity extends BaseActivity implements RegisterPresenter.IRegisCallback {
    @BindView(R.id.getAuthCodeTV)
    TextView getAuthCodeTV;
    @BindView(R.id.phoneUV)
    UtilityView phoneUV;
    @BindView(R.id.codeUV)
    UtilityView codeUV;
    @BindView(R.id.passWord)
    UtilityView passWord;
    private MyTimer myTimer;
    private RegisterPresenter presenter;
    private String phone;


    @Override
    protected int contentViewResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        myTimer = new MyTimer(60 * 1000, 1000, getAuthCodeTV, this);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter = new RegisterPresenter(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
    }

    @Override
    public void checkSuccess(Boolean data) {
        if (data){
            ToastUtils.showLong("手机号已注册");
        }else{
            myTimer.start();
            presenter.getCode(phone);
        }

    }

    @Override
    public void regisSuccess(Object data) {
//        registPush();
        finish();
    }



    @Override
    public void regisFail() {

    }

    @Override
    public void codeSuccess(Object data) {

    }


    @OnClick({R.id.regisBtn, R.id.getAuthCodeTV})
    public void onViewClicked(View view) {
        phone = phoneUV.getContentText();

        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showLong("请输入正确的电话号码");
            return;
        }
        switch (view.getId()) {
            case R.id.regisBtn:
                String code = codeUV.getContentText();
                String pass = passWord.getContentText();

                if (StringUtils.isEmpty(code)) {
                    ToastUtils.showLong("请输入验证码");
                    return;
                }
                if (StringUtils.isEmpty(pass)) {
                    ToastUtils.showLong("请输入密码");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", pass);
                map.put("type", "3");
                map.put("code", code);
                presenter.register(map);
                break;
            case R.id.getAuthCodeTV:
                presenter.checkUserName(phone);
                break;
        }

    }
}
