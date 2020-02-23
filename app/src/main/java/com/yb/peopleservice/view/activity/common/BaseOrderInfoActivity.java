package com.yb.peopleservice.view.activity.common;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:Flower
 * 类描述: 订单详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/4 17:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseOrderInfoActivity extends BaseToolbarActivity {

    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.stateTV)
    TextView stateTV;
    @BindView(R.id.topLL)
    LinearLayout topLL;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.remakeTV)
    TextView remakeTV;
    @BindView(R.id.priceTV2)
    TextView priceTV2;
    @BindView(R.id.numTV)
    TextView numTV;
    @BindView(R.id.priceLL)
    LinearLayout priceLL;
    @BindView(R.id.headImg)
    ImageView headImg;
    @BindView(R.id.serviceName)
    TextView serviceName;
    @BindView(R.id.personLL)
    LinearLayout personLL;
    @BindView(R.id.priceUV)
    UtilityView priceUV;
    @BindView(R.id.couponUV)
    UtilityView couponUV;
    @BindView(R.id.payUV)
    UtilityView payUV;
    @BindView(R.id.infoLL)
    LinearLayout infoLL;
    private OrderListBean orderListBean;
    
    @Override
    public String getTitleName() {
        return "订单详情";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.view_order_info;
    }

    @Override
    protected void initData() {
        orderListBean = getIntent().getParcelableExtra(orderListBean.getClass().getName());
        setDataText();
    }

    private void setDataText() {
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

}
