package com.yb.peopleservice.view.activity.common;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.NumberUtil;
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

    @BindView(R.id.shopNameTV)
    TextView shopNameTV;
    @BindView(R.id.topLL)
    LinearLayout topLL;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.serviceNameTitleTV)
    TextView serviceNameTitleTV;
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
    protected OrderListBean orderListBean;
    private String staffPhone;

    @Override
    public String getTitleName() {
        return "订单详情";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.view_order_info;
    }

    @Override
    protected void initView() {
        orderListBean = getIntent().getParcelableExtra(OrderListBean.class.getName());
        if (orderListBean.getShop() != null) {
            setShopInfo(orderListBean.getShop());
        }
        if (orderListBean.getCommodity() != null) {
            setServiceInfo(orderListBean.getCommodity());
        }
        if (orderListBean.getOrder() != null) {
            setOrderInfo(orderListBean.getOrder());
        }
        if (orderListBean.getServiceStaff() != null) {
            setServiceStaff(orderListBean.getServiceStaff());
        } else {
            personLL.setVisibility(View.GONE);
        }
        if (!orderListBean.getCoupons().isEmpty()) {
            setCouponInfo(orderListBean.getCoupons().get(0));
        } else {
            couponUV.setVisibility(View.GONE);
        }
        if (orderListBean.getCustomer() != null) {
            setCustomInfo(orderListBean.getCustomer());
        }
        super.initView();
    }

    @Override
    protected void initData() {

    }

    protected void setCustomInfo(UserInfoBean customInfo) {

    }

    protected void setCouponInfo(CouponBean couponInfo) {
        if (couponInfo.getType() == 1) {
            couponUV.setContentText("﹣" + couponInfo.getMoney() + "元");
        } else {
            couponUV.setContentText(couponInfo.getDiscount() + "折");
        }
    }

    protected void setOrderInfo(OrderBean orderBean) {

        priceTV2.setText(orderBean.getPrice() + "元");
        numTV.setText("x" + orderBean.getAmount());
        priceUV.setContentText(NumberUtil.convertFloatZero(orderBean.getPrice() *
                orderBean.getAmount()));
        payUV.setContentText(NumberUtil.convertFloatZero(orderBean.getTotalPrice()));
    }

    /**
     * 设置店铺信息
     */
    protected void setShopInfo(ShopInfo shopInfo) {
        shopNameTV.setText(shopInfo.getName());
    }

    protected void setServiceInfo(ServiceListBean bean) {
        serviceNameTitleTV.setText(bean.getName());
        remakeTV.setText("服务内容：" + bean.getContentText());
        ImageLoaderUtil.loadServerImage(this, bean.getMainImage(), imageView);
    }


    protected void setServiceStaff(ServiceInfo staff) {
        staffPhone = staff.getPhone();
        serviceName.setText(staff.getName() + "（电话：" + staff.getPhone() + ")");
        ImageLoaderUtil.loadServerCircleImage(this, staff.getHeadImg(), headImg);
    }

    @OnClick(R.id.serviceName)
    public void callPhone() {
        if (!StringUtils.isEmpty(staffPhone)) {
            AppUtils.callPhone(staffPhone);
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

}
