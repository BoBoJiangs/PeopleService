package com.yb.peopleservice.view.activity.login;

import android.os.Bundle;
import android.widget.TextView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.model.presenter.login.RegisterPresenter;
import com.yb.peopleservice.utils.MyTimer;
import com.yb.peopleservice.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

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

    @Override
    protected int contentViewResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        myTimer = new MyTimer(60 * 1000, 1000, getAuthCodeTV, this);
        myTimer.start();
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
    public void regisSuccess(Object data) {

    }

    @Override
    public void regisFail() {

    }


    @OnClick(R.id.regisBtn)
    public void onViewClicked() {

    }
}
