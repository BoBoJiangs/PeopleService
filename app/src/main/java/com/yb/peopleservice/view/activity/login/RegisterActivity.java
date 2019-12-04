package com.yb.peopleservice.view.activity.login;

import android.widget.TextView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.utils.MyTimer;
import com.yb.peopleservice.view.base.BaseActivity;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.getAuthCodeTV)
    TextView getAuthCodeTV;
    private MyTimer myTimer;
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
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
    }
}
