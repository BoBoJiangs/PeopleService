package com.yb.peopleservice.view.activity.common;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class OrderDetailsActivity extends BaseOrderInfoActivity {

    @BindView(R.id.orderStateTV)
    TextView stateTV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.bottomTV1)
    TextView bottomTV1;
    @BindView(R.id.bottomTV2)
    TextView bottomTV2;
    @BindView(R.id.btnLL)
    LinearLayout btnLL;
    @BindView(R.id.orderNumUV)
    UtilityView orderNumUV;
    @BindView(R.id.orderTimeUV)
    UtilityView orderTimeUV;
    @BindView(R.id.payTypeUV)
    UtilityView payTypeUV;
    @BindView(R.id.payTimeUV)
    UtilityView payTimeUV;
    @BindView(R.id.stateIV)
    ImageView stateIV;
    private User user;
    @Override
    public String getTitleName() {
        return "订单详情";
    }

    @Override
    public void initToolView() {
        super.initToolView();
        titleTV.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.base_text_color).init();
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initData() {
        orderNumUV.getContentTextView().setLines(1);
        user = ManagerFactory.getInstance().getUserManager().getUser();
        super.initData();
    }

    @Override
    protected void setCustomInfo(UserInfoBean customInfo) {
        nameTV.setText("预约人："+customInfo.getNickname());
        phoneTV.setText(customInfo.getPhone());

    }

    @Override
    protected void setOrderInfo(OrderBean orderBean) {
        super.setOrderInfo(orderBean);
        showOrderState(orderBean);
        addressTV.setText(orderBean.getConsigneeAddress());
//        orderNumUV.setContentText(orderBean);
        orderTimeUV.setContentText(orderBean.getCreateTime());
        payTimeUV.setContentText(orderBean.getPayTime());
        orderNumUV.setContentText(orderBean.getOrderNumber());
    }

    private void showOrderState(OrderBean orderBean){
        stateIV.setImageResource(R.mipmap.fuw);
        switch (orderBean.getStatus()) {
            case OrderBean.NO_PAY:
                stateIV.setImageResource(R.mipmap.daichengtuan);
                stateTV.setText("待付款");
                break;
            case OrderBean.GROUP_NOT_FINISH:
                stateIV.setImageResource(R.mipmap.daichengtuan);
                stateTV.setText("待成团");
                break;
            case OrderBean.PAID:
                stateTV.setText("待指派");
                break;
            case OrderBean.ASSIGN:
                stateTV.setText("待接单");
                break;
            case OrderBean.WAITING:
                stateTV.setText("前往中");

                break;
            case OrderBean.ARRIVED:
                stateTV.setText("已抵达");
                break;
            case OrderBean.DOING:
                stateTV.setText("服务中");
                break;
            case OrderBean.ASSESS:
                stateIV.setImageResource(R.mipmap.daipingjia);
                stateTV.setText("待评价");
//                bottomTV2.setVisibility(View.VISIBLE);
//                bottomTV2.setText("立即评价");
                break;
            case OrderBean.COMPLETED:
                stateIV.setImageResource(R.mipmap.wancheng);
                stateTV.setText("已完成");
                break;
            case OrderBean.CLOSE:
                stateTV.setText("已关闭");
//                bottomTV2.setVisibility(View.VISIBLE);
//                bottomTV2.setText("查看退款");
                break;
            default:
                stateTV.setText("状态未知");
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.bottomTV1, R.id.bottomTV2,R.id.phoneTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottomTV1:
                break;
            case R.id.bottomTV2:
                break;
            case R.id.phoneTV:
                String phone = phoneTV.getText().toString();
                if (!StringUtils.isEmpty(phone)){
                    AppUtils.callPhone(phone);
                }
                break;
        }
    }
}
