package com.yb.peopleservice.view.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.noober.background.drawable.DrawableCreator;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.presenter.login.QuickLoginPresenter;
import com.yb.peopleservice.view.activity.main.MainActivity;
import com.yb.peopleservice.view.activity.main.ServiceMainActivity;
import com.yb.peopleservice.view.activity.main.ShopMainActivity;
import com.yb.peopleservice.view.base.BaseActivity;
import com.yb.peopleservice.view.weight.PasswordInputView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.app.AppManager;
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
    @BindView(R.id.radioRg)
    RadioGroup radioGroup;
    private UserType type = UserType.CUSTOMER;
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

    @Override
    protected QuickLoginPresenter createPresenter() {
        return presenter = new QuickLoginPresenter(this,this);
    }


    @OnClick({R.id.nextBtn, R.id.loginBtn, R.id.retryTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nextBtn:
                if (type == null) {
                    ToastUtils.showLong("请选择账号类型");
                    return;
                }
                phone = phoneUV.getContentText();
//                presenter.checkUserName(phone);
                if (!RegexUtils.isMobileSimple(phone)) {
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
                presenter.getLoginVoucher(phone,code,type.getValue());
                break;
            case R.id.retryTV:
                presenter.getCode(phone);
                break;
        }
    }

    @Override
    public void checkSuccess(boolean isExist) {
        if (isExist){
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
        }else{
            ToastUtils.showLong("账号不存在");
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
        ToastUtils.showLong("验证码已发送,请注意查收");
        phoneLL.setVisibility(View.GONE);
        loginLL.setVisibility(View.VISIBLE);
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
        ToastUtils.showLong("验证码发送失败,请重试");
    }
}
