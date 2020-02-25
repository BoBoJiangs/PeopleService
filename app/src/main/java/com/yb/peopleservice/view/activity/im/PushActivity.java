package com.yb.peopleservice.view.activity.im;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.view.activity.main.MainActivity;
import com.yb.peopleservice.view.base.BaseActivity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.presenter.AbstractPresenter;

public class PushActivity extends BaseActivity {
    User user;

    @Override
    protected int contentViewResID() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initData() {
        user = ManagerFactory.getInstance().getUserManager().getUser();

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    public void registPush() {
        JMessageClient.register(user.getAccount(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    ToastUtils.showLong("注册成功");
                } else {
                    ToastUtils.showLong("注册失败");
                }
            }
        });
    }

    public void loginPush() {
        JMessageClient.login(user.getAccount(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    ToastUtils.showLong("登陆成功"+appKey);
                } else {
                    ToastUtils.showLong("登陆失败");
                }
            }
        });
    }


    @OnClick({R.id.regisBtn, R.id.loginBtn, R.id.jumpBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regisBtn:
                registPush();
                break;
            case R.id.loginBtn:
                loginPush();
                break;
            case R.id.jumpBtn:
                break;
        }
    }
}
