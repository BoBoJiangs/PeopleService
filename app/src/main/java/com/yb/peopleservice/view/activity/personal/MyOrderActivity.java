package com.yb.peopleservice.view.activity.personal;

import com.blankj.utilcode.util.FragmentUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.yb.peopleservice.view.fragment.user.order.OrderListFragment;
import com.yb.peopleservice.view.fragment.user.order.OrderTabFragment;

import cn.sts.base.presenter.AbstractPresenter;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/4/21 19:41
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MyOrderActivity extends BaseToolbarActivity {
    private int index;
    @Override
    public String getTitleName() {
        return "我的订单";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initData() {
        index = getIntent().getIntExtra(IntentKeyConstant.DATA_KEY,0);
        FragmentUtils.add(getSupportFragmentManager(),
                OrderTabFragment.getInstanceFragment(index),R.id.frameLayout);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
