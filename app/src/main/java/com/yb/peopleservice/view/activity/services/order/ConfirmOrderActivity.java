package com.yb.peopleservice.view.activity.services.order;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述: 确认订单
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/6 15:27
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ConfirmOrderActivity extends BaseToolbarActivity {
    @BindView(R.id.addressUV)
    UtilityView addressUV;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.numTV)
    TextView numTV;
    @BindView(R.id.totalUV)
    UtilityView totalUV;
    @BindView(R.id.couponTV)
    UtilityView couponTV;
    @BindView(R.id.moneyTV)
    TextView moneyTV;
    @BindView(R.id.payBtn)
    Button payBtn;
    private ServiceListBean bean;

    @Override
    public String getTitleName() {
        return null;
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initData() {
        bean = getIntent().getParcelableExtra(ServiceListBean.class.getName());
        if (bean != null) {
            nameTV.setText(bean.getName());
            priceTV.setText("¥ "+bean.getPrice());
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

}
