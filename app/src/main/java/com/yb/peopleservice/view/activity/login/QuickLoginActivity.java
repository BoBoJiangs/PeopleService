package com.yb.peopleservice.view.activity.login;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.noober.background.drawable.DrawableCreator;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseActivity;
import com.yb.peopleservice.view.weight.PasswordInputView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 类描述:快捷登录
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/4  10:55
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class QuickLoginActivity extends BaseActivity implements PasswordInputView.InputListener {


    @BindView(R.id.phoneUV)
    UtilityView phoneUV;
    @BindView(R.id.nextBtn)
    Button nextBtn;
    @BindView(R.id.phoneLL)
    LinearLayout phoneLL;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.vcivCode)
    PasswordInputView vcivCode;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.retryTV)
    TextView retryTV;
    @BindView(R.id.bottomLL)
    LinearLayout bottomLL;
    @BindView(R.id.loginLL)
    LinearLayout loginLL;
    Drawable drawable;
    Drawable drawable2;

    @Override
    protected int contentViewResID() {
        return R.layout.activity_quick_login;
    }

    @Override
    protected void initData() {
        drawable = new DrawableCreator.Builder()
                .setCornersRadius(SizeUtils.dp2px(20))
                .setSolidColor(Color.parseColor("#CCCCCC"))
                .build();
        drawable2 = new DrawableCreator.Builder()
                .setCornersRadius(SizeUtils.dp2px(20))
                .setGradientAngle(0)
                .setGradientColor(Color.parseColor("#FF7E43"),
                        Color.parseColor("#FF5F00"))
                .build();
        vcivCode.setInputListener(this::onInputCompleted);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.nextBtn, R.id.loginBtn, R.id.retryTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nextBtn:
                phoneLL.setVisibility(View.GONE);
                loginLL.setVisibility(View.VISIBLE);
                break;
            case R.id.loginBtn:
                break;
            case R.id.retryTV:
                break;
        }
    }

    @Override
    public void onInputCompleted(String text) {
        if (text.length() == 4) {

            loginBtn.setBackground(drawable2);
        } else {
            loginBtn.setBackground(drawable);
        }
    }
}
