package com.yb.peopleservice.view.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.presenter.login.LoginPresenter;
import com.yb.peopleservice.model.presenter.login.RegisterPresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.MyTimer;
import com.yb.peopleservice.view.activity.common.WebViewActivity;
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
    @BindView(R.id.radioRg)
    protected RadioGroup radioGroup;
    @BindView(R.id.checkBox)
    CheckBox checkBox;

    private MyTimer myTimer;
    protected RegisterPresenter presenter;
    private String phone;
    private UserType type = UserType.CUSTOMER;


    @Override
    protected int contentViewResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        myTimer = new MyTimer(60 * 1000, 1000, getAuthCodeTV, this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
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
        if (data) {
            ToastUtils.showLong("手机号已注册");
        } else {
            myTimer.start();
            presenter.getCode(phone);
        }

    }

    @Override
    public void regisSuccess(Object data) {
//        registPush();
        ToastUtils.showLong("账号注册成功");
        finish();
    }


    @Override
    public void regisFail() {

    }

    @Override
    public void codeSuccess(Object data) {
        ToastUtils.showLong("验证码已发送至手机");
    }

    @OnClick(R.id.agreementTV)
    public void agreementClick() {
        startActivity(new Intent(this, WebViewActivity.class)
                .putExtra(IntentKeyConstant.WEB_VIEW_URL, BaseRequestServer.SERVER_URL + "agreement.html")
                .putExtra(IntentKeyConstant.WEB_VIEW_TITLE, "服务协议"));
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
                if (!checkBox.isChecked()) {
                    ToastUtils.showLong("需先同意服务协议");
                    return;
                }
                if (StringUtils.isEmpty(code)) {
                    ToastUtils.showLong("请输入验证码");
                    return;
                }
                if (!AppUtils.isPassWord(pass)) {
                    ToastUtils.showLong("密码必须由8位以上数字和英文组成");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", pass);
                map.put("type", type.getValue());
                map.put("code", code);
                register(map);
                break;
            case R.id.getAuthCodeTV:
//                presenter.checkUserName(phone);
                myTimer.start();
                presenter.getCode(phone);
                break;
        }

    }

    protected void register(Map<String, Object> map) {
        presenter.register(map);
    }
}
