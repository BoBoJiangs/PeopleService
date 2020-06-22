package com.yb.peopleservice.view.activity.common;

import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/14 22:26
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class AboutActivity extends BaseToolbarActivity {
    @BindView(R.id.versionTV)
    TextView versionTV;
    @Override
    public String getTitleName() {
        return "关于";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        versionTV.setText("当前版本：V" + AppUtils.getAppVersionName());
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
