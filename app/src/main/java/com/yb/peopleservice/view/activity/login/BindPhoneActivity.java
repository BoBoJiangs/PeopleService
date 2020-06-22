package com.yb.peopleservice.view.activity.login;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.enums.LoginType;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.presenter.login.RegisterPresenter;
import com.yb.peopleservice.utils.MyTimer;
import com.yb.peopleservice.view.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 类描述:绑定手机号
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/25  20:19
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class BindPhoneActivity extends RegisterActivity {
    @BindView(R.id.titleTV)
    TextView titleTV;
    private String id;
    private String userType;

    @Override
    protected void initView() {
        super.initView();
        id = getIntent().getStringExtra("id");
        userType = getIntent().getStringExtra("userType");
        radioGroup.setVisibility(View.GONE);
        titleTV.setText("绑定手机号");
    }

    @Override
    protected void register(Map<String, Object> map) {
        map.put("type", userType);
        map.put("userId", id);
        if (LoginType.getLoginType() == null) {
            ToastUtils.showLong("绑定失败，请重试！");
            finish();
            return;
        }
        map.put("platformType", LoginType.getLoginType().getValue());
        super.register(map);
    }
}
