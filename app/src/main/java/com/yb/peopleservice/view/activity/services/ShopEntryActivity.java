package com.yb.peopleservice.view.activity.services;

import android.view.View;

import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.view.activity.common.ShopDetailsActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 项目名称:PeopleService
 * 类描述:店铺入驻
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/15 20:04
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopEntryActivity extends ShopDetailsActivity {
    private ServiceInfo serviceInfo;

    @Override
    public void initToolView() {
        super.initToolView();
        serviceInfo = getIntent().getParcelableExtra(ServiceInfo.class.getName());
        super.initData();
        shopInfo = serviceInfo.getShop();
        setViewText();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onViewClicked() {
        if (serviceInfo.getJobStatus().equals("1")) {
            presenter.unboundShop();
        } else {
            presenter.cancelaShop();
        }
    }

    @Override
    public void onSuccess() {
        EventBus.getDefault().post(serviceInfo);
        super.onSuccess();
    }

    @Override
    protected void setViewText() {
        super.setViewText();
        titleTV.setText(shopInfo.getName());
        stateLL.setVisibility(View.VISIBLE);
        sureTV.setVisibility(View.VISIBLE);
        switch (serviceInfo.getJobStatus()) {
            case "1"://正常
                sureBtn.setText("申请离职");
                sureTV.setVisibility(View.GONE);
                break;
            case "2"://申请入驻审核中
                sureTV.setText("申请入驻审核中");
                sureBtn.setText("撤销申请");
                break;
//            case "5"://离职申请入驻审核中
//                sureTV.setText("离职申请入驻审核中");
//                sureBtn.setText("撤销离职审核");
//                break;
        }
    }
}
