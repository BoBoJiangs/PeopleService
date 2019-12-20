package com.yb.peopleservice.view.activity.common;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.presenter.shop.ServiceShopStatePresenter;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;

/**
 * 项目名称:Flower
 * 类描述: 店铺详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/4 17:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopDetailsActivity extends BaseToolbarActivity implements ServiceShopStatePresenter.IServiceInfoCallback {

    @BindView(R.id.companyNameTV)
    TextView companyNameTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.remakeTV)
    TextView remakeTV;
    @BindView(R.id.sureTV)
    TextView sureTV;
    @BindView(R.id.sureBtn)
    Button sureBtn;
    private ServiceShopStatePresenter presenter;
    private MyShop myShop;

    @Override
    public String getTitleName() {
        return "";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_shop_details;
    }

    @Override
    public void initToolView() {
        super.initToolView();
        myShop = (MyShop) getIntent().getSerializableExtra(MyShop.class.getName());
        if (myShop != null) {
            setViewText(myShop);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter = new ServiceShopStatePresenter(this, this);
    }


    @OnClick(R.id.sureBtn)
    public void onViewClicked() {
        if (myShop == null) {
            return;
        }
        if (myShop.getShop() == null) {
            return;
        }
        if (myShop.getStatus().equals("1")) {
            presenter.unboundShop(myShop.getShop().getId());
        }else{
            presenter.cancelaShop(myShop.getShop().getId());
        }
    }

    private void setViewText(MyShop myShop) {
        ShopInfo shopInfo = myShop.getShop();
        if (shopInfo != null) {
            titleTV.setText(shopInfo.getName());
            phoneTV.setText("联系电话：" + shopInfo.getPhone());
            addressTV.setText("地址：" + shopInfo.getAddress());
            remakeTV.setText(shopInfo.getIntroduction());
        }
        sureTV.setVisibility(View.VISIBLE);
        switch (myShop.getStatus()) {
            case "1"://正常
                sureBtn.setText("申请离职");
                sureTV.setVisibility(View.GONE);
                break;
            case "3"://申请入驻审核中
                sureTV.setText("申请入驻审核中");
                sureBtn.setText("撤销审核");
                break;
            case "4"://入驻申请未通过
                sureBtn.setText("撤销申请");
                sureTV.setText("申请未通过 " + myShop.getMessage());
                break;
            case "5"://离职申请入驻审核中
                sureTV.setText("离职申请入驻审核中");
                sureBtn.setText("撤销离职审核");
                break;
        }
    }

    @Override
    public void onSuccess() {
        ToastUtils.showLong("操作成功");
        setResult(ResponseCodeConstant.BASE_RESPONSE);
        finish();
    }

    @Override
    public void onFail() {

    }
}
