package com.yb.peopleservice.view.activity.common;

import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import cn.sts.base.presenter.AbstractPresenter;

/**
 * 项目名称:Flower
 * 类描述: 订单详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/4 17:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderDetailsActivity extends BaseToolbarActivity {

    @Override
    public String getTitleName() {
        return "订单详情";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
