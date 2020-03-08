package com.yb.peopleservice.view.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/5 22:31
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class SetActivity extends BaseToolbarActivity {
    @BindView(R.id.infoUV)
    UtilityView infoUV;
    @BindView(R.id.updateUV)
    UtilityView imageUtility;

    @Override
    public String getTitleName() {
        return "设置";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_set;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.infoUV, R.id.updateUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.infoUV:
                startActivity(new Intent(this,EditUserInfoActivity.class));
                break;
            case R.id.updateUV:
                startActivity(new Intent(this,UpdatePasswordActivity.class));
                break;
        }
    }
}
