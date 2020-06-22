package com.yb.peopleservice.view.activity.services.order;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cb.ratingbar.CBRatingBar;
import com.lxj.xpopup.XPopup;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.presenter.user.order.ApplyRefundPresenter;
import com.yb.peopleservice.model.presenter.user.order.CommentOrderPresenter;
import com.yb.peopleservice.model.presenter.user.order.OrderStatePresenter;
import com.yb.peopleservice.view.activity.common.BaseSelectImageActivity;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;
import com.yb.peopleservice.view.weight.CustomPopup.MapBottomPopup;
import com.yb.peopleservice.view.weight.CustomPopup.PayBottomPopup;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.AppDialog;

import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT12;
import static com.yb.peopleservice.view.adapter.order.OrderListAdapter.BUTTON_TEXT13;

/**
 * 项目名称:PeopleService
 * 类描述: 评论
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/23 20:46
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ApplyRefundActivity extends BaseSelectImageActivity implements
        ApplyRefundPresenter.IApplyCallback, OrderStatePresenter.IOrderCallback {
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.refundTV)
    TextView refundTV;
    @BindView(R.id.submitBtn)
    TextView submitBtn;
    @BindView(R.id.bottomLL)
    LinearLayout bottomLL;
    @BindView(R.id.bottomLL2)
    LinearLayout bottomLL2;
    private ApplyRefundPresenter presenter;
    private int level = 1;
    private OrderBean orderBean;
    private String refundMoney;
    private OrderStatePresenter orderStatePresenter;
    private User user;

    @Override
    public String getTitleName() {
        if (isShowBtn) {
            bottomLL.setVisibility(View.VISIBLE);
            return "申请退款";
        } else {
            editText.setEnabled(false);
            editText.setText(orderBean.getRefundReason());
            editText.setHint("");
            bottomLL.setVisibility(View.GONE);
            return "查看退款";
        }

    }


    @Override
    protected int contentViewResID() {
        return R.layout.activity_apply_refund;
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }

    @Override
    protected void initData() {
        super.initData();
        orderStatePresenter = new OrderStatePresenter(this, this);
        presenter = new ApplyRefundPresenter(this, this);
        if (!orderBean.refundImgList().isEmpty()) {
            baseQuickAdapter.setNewData(orderBean.refundImgList());
        }

    }

    @Override
    protected boolean isShowBtn() {
        user = ManagerFactory.getInstance().getUserManager().getUser();
        if (OrderListAdapter.REFUND_STATE.equals(orderBean.getRefundStatus())) {
            return true;
        } else {
            if (OrderListAdapter.REFUND_STATE2.equals(orderBean.getRefundStatus())&&
                    user.getAccountType().contains(UserType.SHOP.getValue())) {
                bottomLL2.setVisibility(View.VISIBLE);
            }
            return false;
        }

    }

    @Override
    protected void setOrderInfo(OrderBean orderBean) {
        super.setOrderInfo(orderBean);
        this.orderBean = orderBean;
        refundMoney = orderBean.getTotalPrice() + "";
        refundTV.setText("退款金额：¥" + refundMoney);
    }

    @OnClick({R.id.submitBtn, R.id.updateTV, R.id.bottomTV1, R.id.bottomTV2})
    public void onViewClicked(View view) {

        Map<String, Object> map = new HashMap<>();
        map.put("reason", editText.getText().toString());
        map.put("orderId", orderBean.getId());
        switch (view.getId()) {
            case R.id.submitBtn:
                map.put("money", refundMoney);
                if (!getFileImages().isEmpty()) {
                    presenter.submitData(getFileImages(), map);
                } else {
                    presenter.submitData(map);
                }
                break;
            case R.id.updateTV:
                new XPopup.Builder(this)
                        .asCustom(new PayBottomPopup(this,
                                number -> {
                                    refundMoney = number + "";
                                    refundTV.setText("退款金额：¥" + number);
                                }))
                        .show();
                break;
            case R.id.bottomTV1:
                showDialog("是否确认拒绝退款?", orderBean.getId(), BUTTON_TEXT13);
                break;
            case R.id.bottomTV2:

                showDialog("是否确认同意退款?", orderBean.getId(), BUTTON_TEXT12);
                break;
        }


    }

    private void showDialog(String msg, String id, String refundStatus) {
        AppDialog appDialog = new AppDialog(this)
                .title(msg)
                .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                        orderStatePresenter.refundOrder(id, refundStatus);
                    }
                })
                .negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                    }
                });
        appDialog.setCancelable(false);
        appDialog.show();
    }

    @Override
    public void applySuccess(Object data) {
        EventBus.getDefault().post(new EventOrderBean());
        ToastUtils.showLong("申请成功,等待商家审核！");
        finish();
    }

    @Override
    public void applyFail() {

    }

    @Override
    public void acceptSuccess(Object data) {
        ToastUtils.showLong("订单处理成功");
        EventBus.getDefault().post(new EventOrderBean());
        finish();
    }

    @Override
    public void acceptFail() {

    }
}
