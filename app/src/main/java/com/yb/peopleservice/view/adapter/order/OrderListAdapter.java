package com.yb.peopleservice.view.adapter.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.presenter.user.order.OrderStatePresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.common.OrderDetailsActivity;
import com.yb.peopleservice.view.activity.services.order.ApplyRefundActivity;
import com.yb.peopleservice.view.activity.services.order.CommentOrderActivity;
import com.yb.peopleservice.view.activity.shop.ShopPersonActivity;
import com.yb.peopleservice.view.weight.CustomPopWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述: 订单列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean, BaseViewHolder> implements OrderStatePresenter.IOrderCallback {
    public static final String BUTTON_TEXT1 = "指派";
    public static final String BUTTON_TEXT2 = "申请退款";
    public static final String BUTTON_TEXT3 = "拒绝";
    public static final String BUTTON_TEXT4 = "接受";
    public static final String BUTTON_TEXT5 = "抵达";
    public static final String BUTTON_TEXT6 = "开始服务";
    public static final String BUTTON_TEXT7 = "服务完成";
    public static final String BUTTON_TEXT8 = "申请补款";
    public static final String BUTTON_TEXT9 = "立即评价";
    public static final String BUTTON_TEXT10 = "立即支付";
    public static final String BUTTON_TEXT11 = "查看退款";

    private Context context;
    private User user;
    private OrderStatePresenter presenter;

    public OrderListAdapter(Context context) {
        super(R.layout.item_order_layout);
        this.context = context;
        user = ManagerFactory.getInstance().getUserManager().getUser();
        presenter = new OrderStatePresenter(context, this);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean item) {
        ShopInfo shopInfo = item.getShop();
        ServiceListBean serviceListBean = item.getCommodity();
        OrderBean orderBean = item.getOrder();
        ImageView imageView = helper.getView(R.id.imageView);
        TextView stateTV = helper.getView(R.id.stateTV);
        TextView bottomTV1 = helper.getView(R.id.bottomTV1);
        TextView bottomTV2 = helper.getView(R.id.bottomTV2);
        bottomTV1.setVisibility(View.GONE);
        bottomTV2.setVisibility(View.GONE);
        bottomTV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton(orderBean, bottomTV2, item);
            }
        });
        bottomTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton(orderBean, bottomTV1, item);

            }
        });
        if (orderBean != null) {
            switch (orderBean.getStatus()) {
                case OrderBean.GROUP_NOT_FINISH:
                    stateTV.setText("凑团中");
                    break;
                case OrderBean.PAID:
                    stateTV.setText("待指派");
                    if (user != null && !user.getAccountType().isEmpty()) {
                        if (user.getAccountType().contains(LoginBean.SHOP_TYPE)) {
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT1);
                        } else if (user.getAccountType().contains(LoginBean.USER_TYPE)) {
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT2);
                        }
                    }
                    break;
                case OrderBean.ASSIGN:
                    stateTV.setText("待接单");
                    if (user != null && !user.getAccountType().isEmpty()) {
                        if (user.getAccountType().contains(LoginBean.SERVICE_TYPE)) {
                            bottomTV1.setVisibility(View.VISIBLE);
                            bottomTV1.setText(BUTTON_TEXT3);
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT4);
                        } else if (user.getAccountType().contains(LoginBean.USER_TYPE)) {
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT2);
                        }
                    }
                    break;
                case OrderBean.WAITING:
                    stateTV.setText("前往中");
                    if (user != null && !user.getAccountType().isEmpty()) {
                        if (user.getAccountType().contains(LoginBean.SERVICE_TYPE)) {
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT5);
                        } else if (user.getAccountType().contains(LoginBean.USER_TYPE)) {
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT2);
                        }
                    }


                    break;
                case OrderBean.ARRIVED:
                    if (user != null && !user.getAccountType().isEmpty()) {
                        if (user.getAccountType().contains(LoginBean.USER_TYPE)) {
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT2);
                            bottomTV1.setVisibility(View.VISIBLE);
                            bottomTV1.setText(BUTTON_TEXT6);
                        }
                    }
                    stateTV.setText("已抵达");
                    break;
                case OrderBean.DOING:
                    if (user != null && !user.getAccountType().isEmpty()) {
                        if (user.getAccountType().contains(LoginBean.SERVICE_TYPE)) {
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT7);
                        } else if (user.getAccountType().contains(LoginBean.USER_TYPE)) {
                            bottomTV1.setVisibility(View.VISIBLE);
                            bottomTV1.setText("申请补款");
                            bottomTV2.setVisibility(View.VISIBLE);
                            bottomTV2.setText(BUTTON_TEXT2);
                        }
                    }
                    stateTV.setText("服务中");
                    break;
                case OrderBean.ASSESS:
                    stateTV.setText("待评价");
                    bottomTV2.setVisibility(View.VISIBLE);
                    bottomTV2.setText(BUTTON_TEXT9);
                    break;
                case OrderBean.COMPLETED:
                    stateTV.setText("已完成");
                    break;
                case OrderBean.NO_PAY:
                    stateTV.setText("待付款");
                    bottomTV2.setVisibility(View.VISIBLE);
                    bottomTV2.setText(BUTTON_TEXT10);
                    break;
                case OrderBean.CLOSE:
                    stateTV.setText("已关闭");
                    bottomTV2.setVisibility(View.VISIBLE);
                    bottomTV2.setText(BUTTON_TEXT11);
                    break;
                default:
                    stateTV.setText("状态未知");
            }
        }

        if (shopInfo != null) {
            helper.setText(R.id.nameTV, shopInfo.getName());
        }
        if (serviceListBean != null) {
            helper.setText(R.id.serviceName, serviceListBean.getName());
            List<String> images = serviceListBean.getMainImg();
            ImageLoaderUtil.loadServerImage(mContext, images.isEmpty() ? "" : images.get(0), imageView);
        }
        if (orderBean != null) {
            helper.setText(R.id.priceTV2, orderBean.getPrice() + "元");
            helper.setText(R.id.numTV, "x" + orderBean.getAmount());
            helper.setText(R.id.textView1, "合计：" + orderBean.getTotalPrice() + "元");
        }

        helper.getView(R.id.moreIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(v);
            }
        });
    }

    private void clickButton(OrderBean orderBean, TextView textView, OrderListBean item) {
        switch (textView.getText().toString()) {
            case BUTTON_TEXT1://待指派服务人员
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(LoginBean.SHOP_TYPE)) {
                        context.startActivity(new Intent(context, ShopPersonActivity.class)
                                .putExtra(OrderBean.class.getName(), orderBean));
                    }
                }
                break;
            case BUTTON_TEXT2://申请退款
                context.startActivity(new Intent(context, ApplyRefundActivity.class)
                        .putExtra(OrderListBean.class.getName(), item));
                break;
            case BUTTON_TEXT4://接受订单
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(LoginBean.SERVICE_TYPE)) {
                        //接受订单
                        presenter.acceptOrder(orderBean.getId(),
                                textView.getId() == R.id.bottomTV2, OrderBean.ASSIGN);

                    }
                }
                break;
            case BUTTON_TEXT5://已抵达
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(LoginBean.SERVICE_TYPE)) {
                        //已抵达
                        presenter.acceptOrder(orderBean.getId(), true, OrderBean.WAITING);

                    }
                }
                break;
            case BUTTON_TEXT6:// 开始服务
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(LoginBean.USER_TYPE)) {
                        //开始服务
                        presenter.acceptOrder(orderBean.getId(), true, OrderBean.ARRIVED);

                    }
                }
                break;
            case BUTTON_TEXT7://服务完成
                if (user != null && !user.getAccountType().isEmpty()) {
                    if (user.getAccountType().contains(LoginBean.SERVICE_TYPE)) {
                        //服务完成
                        presenter.acceptOrder(orderBean.getId(), true, OrderBean.DOING);

                    }
                }
                break;
            case BUTTON_TEXT9:
                context.startActivity(new Intent(context, CommentOrderActivity.class)
                        .putExtra(OrderListBean.class.getName(), item));
                break;
            default:
        }
    }

    private void showPop(View view) {
        new CustomPopWindow.PopupWindowBuilder(context)
                .setView(R.layout.order_delete_pop)
                .create()
                .showAsDropDown(view, SizeUtils.dp2px(-10), SizeUtils.dp2px(-10));
    }

    @Override
    public void setOnItemClick(View v, int position) {
        super.setOnItemClick(v, position);
        OrderListBean orderBean = getItem(position);
        context.startActivity(new Intent(context, OrderDetailsActivity.class)
                .putExtra(OrderListBean.class.getName(), orderBean));
    }

    @Override
    public void acceptSuccess(Object data) {
        ToastUtils.showLong("订单处理成功");
        EventBus.getDefault().post(new EventOrderBean());
    }

    @Override
    public void acceptFail() {

    }
}
