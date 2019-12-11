package com.yb.peopleservice.view.activity.login;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.noober.background.drawable.DrawableCreator;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.presenter.login.QuickLoginPresenter;
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
public class QuickLoginActivity extends BaseActivity<QuickLoginPresenter> implements
        PasswordInputView.InputListener , QuickLoginPresenter.ILoginCallback {


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
    private String phone;
    private QuickLoginPresenter presenter;
    private String code;//验证码

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
    protected QuickLoginPresenter createPresenter() {
        return presenter = new QuickLoginPresenter(this,this);
    }


    @OnClick({R.id.nextBtn, R.id.loginBtn, R.id.retryTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nextBtn:
                phone = phoneUV.getContentText();
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtils.showLong("请输入正确的电话号码");
                    return;
                }
                String textStr = "我们刚刚向 +86 <font color=\"#FF5F00\">" + phone +
                        "</font>发送了一个验证码";
                phoneTV.setText(Html.fromHtml(textStr));
                phoneLL.setVisibility(View.GONE);
                loginLL.setVisibility(View.VISIBLE);
                presenter.getCode(phone);
                break;
            case R.id.loginBtn:
                presenter.getLoginVoucher(phone,code);
                break;
            case R.id.retryTV:
                break;
        }
    }

    @Override
    public void onInputCompleted(String text) {
        code = text;
        if (text.length() == 4) {
            loginBtn.setEnabled(true);
            loginBtn.setBackground(drawable2);
        } else {
            loginBtn.setEnabled(false);
            loginBtn.setBackground(drawable);
        }
    }

    @Override
    public void codeSuccess(Object object) {

    }

    @Override
    public void loginSuccess(LoginBean data) {

    }

    @Override
    public void loginFail() {

    }
}
