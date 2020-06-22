package com.yb.peopleservice.view.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.presenter.user.order.OrderDetailsPresenter;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.view.activity.services.order.ApplyRefundActivity;
import com.yb.peopleservice.view.activity.services.order.CommentOrderActivity;
import com.yb.peopleservice.view.activity.services.order.PayActivity;
import com.yb.peopleservice.view.activity.shop.ShopPersonActivity;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;
import com.yb.peopleservice.view.adapter.order.PayListAdapter;
import com.yb.peopleservice.view.adapter.order.RecordListAdapter;
import com.yb.peopleservice.view.weight.CustomPopup.PayBottomPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.NumberUtil;
import cn.sts.base.view.widget.UtilityView;

import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT10;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT11;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT14;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT2;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT3;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT4;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT5;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT6;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT7;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT9;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.REFUND_STATE;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.REFUND_STATE1;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.REFUND_STATE2;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.REFUND_STATE3;

/**
 * 项目名称:Flower
 * 类描述: 订单详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/4 17:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderDetailsActivity extends BaseOrderInfoActivity implements
        OrderDetailsPresenter.IDetailsCallback {

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
    @BindView(R.id.bottomTV3)
    TextView bottomTV3;
    @BindView(R.id.trackTV)
    TextView trackTV;
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
    @BindView(R.id.payLL)
    LinearLayout payLL;
    @BindView(R.id.recordLL)
    LinearLayout recordLL;
    @BindView(R.id.payListView)
    RecyclerView payListView;
    @BindView(R.id.recordListView)
    RecyclerView recordListView;
    @BindView(R.id.poiLL)
    LinearLayout poiLL;
    @BindView(R.id.startPoiUV)
    UtilityView startPoiUV;
    @BindView(R.id.endPoiUV)
    UtilityView endPoiUV;

    private User user;
    private OrderDetailsPresenter presenter;
    private PayListAdapter payListAdapter;
    private RecordListAdapter recordListAdapter;
    private OrderBean orderBean;

    @Override
    public String getTitleName() {
        return "订单详情";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recordListAdapter.stop();
    }

    @Override
    public void initToolView() {
        super.initToolView();
        titleTV.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void initView() {
        super.initView();
        payListAdapter = new PayListAdapter();
        recordListAdapter = new RecordListAdapter(this);
        payListView.setLayoutManager(new LinearLayoutManager(this));
        recordListView.setLayoutManager(new LinearLayoutManager(this));
        payListView.setAdapter(payListAdapter);
        recordListView.setAdapter(recordListAdapter);
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

        super.initData();
    }

    @Override
    protected void setCustomInfo(UserInfoBean customInfo) {


    }

    @Override
    protected void setOrderInfo(OrderBean orderBean) {
        super.setOrderInfo(orderBean);
        user = ManagerFactory.getInstance().getUserManager().getUser();
        showOrderState(orderBean);
        addressTV.setText(orderBean.getConsigneeAddress() +
                orderBean.getConsigneeHouseNumber());
        nameTV.setText("顾客姓名：" + orderBean.getConsigneeName());
        phoneTV.setText(orderBean.getConsigneePhone());
//        orderNumUV.setContentText(orderBean);
        orderTimeUV.setContentText(orderBean.getCreateTime());
        payTimeUV.setContentText(orderBean.getPayTime());
        orderNumUV.setContentText(orderBean.getOrderNumber());
        presenter = new OrderDetailsPresenter(this, this);
        presenter.orderDetails(orderBean.getId());
        if (orderBean.getTrajectoryId() != 0 && orderBean.getStatus() > 6) {
            trackTV.setVisibility(View.VISIBLE);
        } else {
            trackTV.setVisibility(View.GONE);
        }
        if (orderBean.getCalculatedDistance() == 1) {
            poiLL.setVisibility(View.VISIBLE);
            startPoiUV.setTitleText("起点：");
            endPoiUV.setTitleText("终点：");
            startPoiUV.setContentText(orderBean.getStartAddress());
            endPoiUV.setContentText(orderBean.getEndAddress());
        }
    }

    private void showOrderState(OrderBean orderBean) {
        this.orderBean = orderBean;
        stateIV.setImageResource(R.mipmap.fuw);
        switch (orderBean.getStatus()) {
            case OrderBean.NO_PAY:
                stateIV.setImageResource(R.mipmap.daichengtuan);
                stateTV.setText("待付款");
                bottomTV2.setVisibility(View.VISIBLE);
                bottomTV2.setText(BUTTON_TEXT10);
                break;
            case OrderBean.GROUP_NOT_FINISH:
                stateIV.setImageResource(R.mipmap.daichengtuan);
                stateTV.setText("待成团");
                break;
            case OrderBean.PAID:
                stateTV.setText("待指派");
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.SHOP.getValue())) {
                        bottomTV2.setVisibility(View.VISIBLE);
                        bottomTV2.setText(OrderListAdapter.BUTTON_TEXT1);
                    } else if (user.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                        bottomTV3.setVisibility(View.VISIBLE);
                        bottomTV3.setText(BUTTON_TEXT2);
                    }
                }
                break;
            case OrderBean.ASSIGN:
                stateTV.setText("待接单");
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.STAFF.getValue())) {
                        bottomTV1.setVisibility(View.VISIBLE);
                        bottomTV1.setText(BUTTON_TEXT3);
                        bottomTV2.setVisibility(View.VISIBLE);
                        bottomTV2.setText(BUTTON_TEXT4);
                    } else if (user.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                        bottomTV3.setVisibility(View.VISIBLE);
                        bottomTV3.setText(BUTTON_TEXT2);
                    }
                }
                break;
            case OrderBean.WAITING:
                stateTV.setText("前往中");
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.STAFF.getValue())) {
                        bottomTV2.setVisibility(View.VISIBLE);
                        bottomTV2.setText(BUTTON_TEXT5);
                    } else if (user.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                        bottomTV3.setVisibility(View.VISIBLE);
                        bottomTV3.setText(BUTTON_TEXT2);
                    }
                }
                break;
            case OrderBean.ARRIVED:
                stateTV.setText("已抵达");
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                        bottomTV3.setVisibility(View.VISIBLE);
                        bottomTV3.setText(BUTTON_TEXT2);
                        bottomTV1.setVisibility(View.VISIBLE);
                        bottomTV1.setText(BUTTON_TEXT6);
                    }
                }
                break;
            case OrderBean.DOING:
                stateTV.setText("服务中");
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.STAFF.getValue())) {
                        bottomTV2.setVisibility(View.VISIBLE);
                        bottomTV2.setText(BUTTON_TEXT7);
                    } else if (user.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                        bottomTV1.setVisibility(View.VISIBLE);
                        bottomTV1.setText(BUTTON_TEXT14);
                        bottomTV3.setVisibility(View.VISIBLE);
                        bottomTV3.setText(BUTTON_TEXT2);
                    }
                }
                break;
            case OrderBean.ASSESS:
                stateIV.setImageResource(R.mipmap.daipingjia);
                stateTV.setText("待评价");
                bottomTV2.setVisibility(View.VISIBLE);
                bottomTV2.setText(BUTTON_TEXT9);
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
        showRefundState(orderBean);
    }

    /**
     * 显示退款状态
     */
    private void showRefundState(OrderBean orderBean) {
        String refundState = orderBean.getRefundStatus();
        if (!StringUtils.isEmpty(refundState) && !REFUND_STATE.equals(refundState)) {
            bottomTV3.setVisibility(View.GONE);
            switch (refundState) {
                case REFUND_STATE1:
                case REFUND_STATE2:
                case REFUND_STATE3:
                    bottomTV3.setVisibility(View.VISIBLE);
                    bottomTV3.setText(BUTTON_TEXT11);
                    break;

            }
        }

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.bottomTV1, R.id.bottomTV2, R.id.bottomTV3,
            R.id.phoneTV, R.id.trackTV, R.id.startPoiUV, R.id.endPoiUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startPoiUV:
                startActivity(new Intent(this, OtherSearchActivity.class)
                        .putExtra("positionType", 1)
                        .putExtra(OrderBean.class.getName(), orderBean));
                break;
            case R.id.endPoiUV:
                startActivity(new Intent(this, OtherSearchActivity.class)
                        .putExtra("positionType", 2)
                        .putExtra(OrderBean.class.getName(), orderBean));
                break;
            case R.id.bottomTV1:
                clickButton(bottomTV1, orderListBean);
                break;
            case R.id.bottomTV2:
                clickButton(bottomTV2, orderListBean);
                break;
            case R.id.bottomTV3:
                clickButton(bottomTV3, orderListBean);
                break;
            case R.id.trackTV:
                startActivity(new Intent(this, OtherSearchActivity.class)
                        .putExtra("isTrack", true)
                        .putExtra(OrderBean.class.getName(), orderBean));
                break;
            case R.id.phoneTV:
                String phone = phoneTV.getText().toString();
                if (!StringUtils.isEmpty(phone)) {
                    AppUtils.callPhone(phone);
                }
                break;
        }
    }

    @Override
    public void getDataSuccess(OrderListBean data) {
        if (data != null) {
            payListAdapter.setNewData(data.getPayInfos());
            recordLL.setVisibility(View.GONE);
//            if (data.getOrderSounds().isEmpty()) {
//                recordLL.setVisibility(View.GONE);
//            }
//            recordListAdapter.setNewData(data.getOrderSounds());
        }

    }

    @Override
    public void acceptSuccess(Object data) {
        finish();
    }

    @Override
    public void getDataFail() {

    }

    private void clickButton(TextView textView, OrderListBean item) {
        OrderBean orderBean = item.getOrder();
        switch (textView.getText().toString()) {
            case OrderListAdapter.BUTTON_TEXT1://待指派服务人员
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.SHOP.getValue())) {
                        startActivity(new Intent(this, ShopPersonActivity.class)
                                .putExtra(OrderBean.class.getName(), orderBean));
                    }
                }
                break;
            case BUTTON_TEXT2://申请退款
            case BUTTON_TEXT11://查看退款
                startActivity(new Intent(this, ApplyRefundActivity.class)
                        .putExtra(OrderListBean.class.getName(), item));
                break;
            case BUTTON_TEXT4://接受订单
            case BUTTON_TEXT3:
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.STAFF.getValue())) {
                        //接受订单
                        presenter.acceptOrder(orderBean,
                                textView.getId() == R.id.bottomTV2, OrderBean.ASSIGN);

                    }
                }
                break;
            case BUTTON_TEXT5://已抵达
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.STAFF.getValue())) {
                        //已抵达
                        presenter.acceptOrder(orderBean, true, OrderBean.WAITING);
                    }
                }
                break;
            case BUTTON_TEXT6:// 开始服务
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                        //开始服务
                        presenter.acceptOrder(orderBean, true, OrderBean.ARRIVED);

                    }
                }
                break;
            case BUTTON_TEXT7://服务完成
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(UserType.STAFF.getValue())) {
                        //服务完成
                        presenter.acceptOrder(orderBean, true, OrderBean.DOING);
                    }
                }
                break;
            case BUTTON_TEXT9:
                startActivity(new Intent(this, CommentOrderActivity.class)
                        .putExtra(OrderListBean.class.getName(), item));
                break;
            case BUTTON_TEXT10://立即支付
                startActivity(new Intent(this, PayActivity.class)
                        .putExtra(OrderBean.class.getName(), orderBean)
                        .putExtra(IntentKeyConstant.DATA_KEY, orderBean.getTotalPrice()));
                break;
            case BUTTON_TEXT14:
                new XPopup.Builder(this)
                        .asCustom(new PayBottomPopup(this, new PayBottomPopup.PayCallBack() {
                            @Override
                            public void moneyNum(String number) {
                                startActivity(new Intent(OrderDetailsActivity.this, PayActivity.class)
                                        .putExtra(IntentKeyConstant.DATA_KEY, Float.parseFloat(number))
                                        .putExtra(OrderBean.class.getName(), orderBean)
                                        .putExtra("isAddPay", true));
                            }
                        }, true))
                        .show();
                break;
            default:
        }
    }
}
