package com.yb.peopleservice.view.activity.login;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.presenter.PasswordResetPresenter;
import com.yb.peopleservice.utils.MyTimer;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.StringUtils;
import cn.sts.base.view.widget.UtilityView;

import static com.yb.peopleservice.constant.IntentKeyConstant.ACCOUNT;


/**
 * 类描述: 重置密码or修改密码
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/25  12:40
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PasswordResetActivity extends BaseToolbarActivity implements PasswordResetPresenter.IResetPassword {
    @BindView(R.id.accountUV)
    UtilityView accountUV;
    @BindView(R.id.authCodeUV)
    UtilityView authCodeUV;
    @BindView(R.id.getAuthCodeTV)
    TextView getAuthCodeTV;
    @BindView(R.id.newPwdUV)
    UtilityView newPwdUV;

    private MyTimer myTimer;//获取验证码计时器
 
    private String account;//账号
    private PasswordResetPresenter resetPresenter;

    @Override
    public int contentViewResID() {
        return R.layout.activity_password_reset;
    }

    @Override
    protected void initData() {
        account = getIntent().getStringExtra(ACCOUNT);
        if (!TextUtils.isEmpty(account)){
            accountUV.setContentText(account);
        }
        myTimer = new MyTimer(60 * 1000, 1000,getAuthCodeTV,this);
        resetPresenter = new PasswordResetPresenter(this,this);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    /**
     * 点击获取验证码
     */
    @OnClick(R.id.getAuthCodeTV)
    void clickGetAuthCodeTV() {
        account = accountUV.getContentText();
        if (StringUtils.isBlank(account)) {
            ToastUtils.showShort("请输入账号");
            return;
        }
        if (RegexUtils.isMobileSimple(account)) {
            //手机号
            resetPresenter.getCode(account);
        }else{
            ToastUtils.showLong("请输入正确的电话号码");
        }

    }

    /**
     * 点击提交按钮
     */
    @OnClick(R.id.submitBtn)
    void submitBtn() {
        account = accountUV.getContentText();
        if (StringUtils.isBlank(account)) {
            ToastUtils.showShort("请输入账号");
            return;
        }
        //验证码
        String authCode = authCodeUV.getContentText();
        if (TextUtils.isEmpty(authCode)) {
            ToastUtils.showShort("请输入验证码");
            return;
        }
        //新密码
        String newPwd = newPwdUV.getContentText();
        if (TextUtils.isEmpty(newPwd) || newPwd.length() < 6) {
            ToastUtils.showShort("请输入正确的密码");
            return;
        }

        resetPresenter.resetPass(account, newPwd, authCode);

    }

    /**
     * 获取验证码成功
     *
     */
    @Override
    public void codeSuccess(Object data) {
        myTimer.start();
    }


    /**
     * 设置密码成功
     */
    @Override
    public void resetPasswordSuccess() {
        ToastUtils.showLong("密码修改成功");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(ACCOUNT,account);
        startActivity(intent);
        finish();
    }

    /**
     * 设置密码失败
     */
    @Override
    public void resetPasswordFailed(String error) {
        ToastUtils.showLong(error);
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
    public String getTitleName() {
        return "忘记密码";
    }


}
