package com.yb.peopleservice.view.activity.services.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.order.ConfirmOrderPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.address.AddressListActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.yb.peopleservice.view.fragment.user.order.CouponDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class ConfirmOrderActivity extends BaseToolbarActivity implements ConfirmOrderPresenter.IConfirmCallback {
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
    @BindView(R.id.prepayTV)
    TextView prepayTV;
    @BindView(R.id.moneyNameTV)
    TextView moneyNameTV;
    private ServiceListBean bean;
    private int num = 1;
    private ConfirmOrderPresenter presenter;
    private AddressListVO addressListVO;//用户选择的地址
    @Override
    public String getTitleName() {
        return null;
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        presenter = new ConfirmOrderPresenter(this, this);
        bean = getIntent().getParcelableExtra(ServiceListBean.class.getName());
        if (bean != null) {
            presenter.getCouponList(bean.getId());
            nameTV.setText(bean.getName());
            priceTV.setText("¥ " + bean.getPrice());
            totalUV.setContentText("¥ " + bean.getPrice());
            moneyTV.setText("¥ " + bean.getPrice());
            if (bean.getPriceType() == 1) {
                moneyNameTV.setText("预付金：");
                prepayTV.setVisibility(View.VISIBLE);
            } else {
                moneyNameTV.setText("支付金额：");
                prepayTV.setVisibility(View.GONE);
            }
            List<String> images = bean.getMainImg();
            ImageLoaderUtil.loadServerImage(this, images.isEmpty() ? "" : images.get(0), imageView);
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }


    @OnClick({R.id.cutIV, R.id.addIV, R.id.payBtn, R.id.addressUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cutIV:
                if (num > 1) {
                    num--;
                }
                updatePrice();
                break;
            case R.id.addIV:
                if (num < 10) {
                    num++;
                } else {
                    ToastUtils.showLong("数量已达上限！");
                }
                updatePrice();
                break;
            case R.id.payBtn:
                //根据状态不同显示隐藏
                CouponDialogFragment couponDialogFragment = new CouponDialogFragment();
                couponDialogFragment.show(getSupportFragmentManager(), CouponDialogFragment.class.getSimpleName());
                break;
            case R.id.addressUV:
                startActivityForResult(new Intent(getApplicationContext(), AddressListActivity.class)
                                .putExtra("isSelect", true),
                        IntentKeyConstant.REQUEST_CODE);
                break;
        }
    }

    private void updatePrice() {
        numTV.setText(num + "");
        totalUV.setContentText("¥ " + num * bean.getPrice());
        moneyTV.setText("¥ " + num * bean.getPrice());
    }

    @Override
    public void couponSuccess(List<CouponBean> data) {

    }

    @Override
    public void addressSuccess(AddressListVO data) {
        addressUV.setContentText(data.getConsigneeName() + " " + data.getConsigneePhone()
                + data.getDetailAddress() + data.getHouseNum());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentKeyConstant.REQUEST_CODE && data != null) {
            addressListVO = data.getParcelableExtra(AddressListVO.class.getName());
            if (addressListVO != null) {
                addressSuccess(addressListVO);
            }
        }
    }
}

