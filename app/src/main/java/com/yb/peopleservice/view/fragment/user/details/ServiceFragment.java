package com.yb.peopleservice.view.fragment.user.details;

import androidx.fragment.app.Fragment;

import com.yb.peopleservice.R;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务内容
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/2 16:56
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceFragment extends BaseFragment {
    public static Fragment getInstanceFragment() {
        ServiceFragment fragment = new ServiceFragment();
        return fragment;
    }
    @Override
    public int viewResID() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
