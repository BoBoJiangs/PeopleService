package com.yb.peopleservice.view.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.presenter.PasswordResetPresenter;
import com.yb.peopleservice.model.presenter.user.UpdatePassWordPresenter;
import com.yb.peopleservice.view.activity.login.LoginActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.app.AppManager;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.StringUtils;
import cn.sts.base.view.widget.UtilityView;

import static com.yb.peopleservice.constant.IntentKeyConstant.ACCOUNT;


/**
 * 类描述: 修改密码
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/25  12:40
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class UpdatePasswordActivity extends BaseToolbarActivity
        implements UpdatePassWordPresenter.IPwdCallback {


    @BindView(R.id.passwordUV)
    UtilityView passwordUV;
    @BindView(R.id.newPwdUV)
    UtilityView newPwdUV;
    @BindView(R.id.againPwdUV)
    UtilityView againPwdUV;
    @BindView(R.id.submitBtn)
    Button submitBtn;
    private UpdatePassWordPresenter resetPresenter;

    @Override
    public int contentViewResID() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void initData() {
        resetPresenter = new UpdatePassWordPresenter(this, this);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    /**
     * 点击提交按钮
     */
    @OnClick(R.id.submitBtn)
    void submitBtn() {
        String passWord = passwordUV.getContentText();
        if (StringUtils.isBlank(passWord)) {
            ToastUtils.showShort("请输入原密码");
            return;
        }
        String newPwd = newPwdUV.getContentText();
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showShort("请输入新密码密码");
            return;
        }
        //新密码
        String againPwd = againPwdUV.getContentText();
        if (TextUtils.isEmpty(againPwd)) {
            ToastUtils.showShort("请输入正确的密码");
            return;
        }

        if (!againPwd.equals(newPwd)) {
            ToastUtils.showShort("两次输入的密码不一致");
            return;
        }

        if (newPwd.length() < 6) {
            ToastUtils.showShort("密码至少六位");
            return;
        }

        resetPresenter.password(passWord,newPwd);

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getTitleName() {
        return "修改密码";
    }


    @Override
    public void updateSuccess(UserInfoBean data) {
        ToastUtils.showLong("修改密码成功,请重新登录");
        startActivity(new Intent(this,LoginActivity.class));
        AppManager.getAppManager().finishAllActivity();
        finish();
    }

    @Override
    public void updateFail() {

    }
}
