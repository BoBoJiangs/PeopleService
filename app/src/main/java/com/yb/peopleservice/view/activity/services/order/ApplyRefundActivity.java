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
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.presenter.user.order.ApplyRefundPresenter;
import com.yb.peopleservice.model.presenter.user.order.CommentOrderPresenter;
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

/**
 * 项目名称:PeopleService
 * 类描述: 评论
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/23 20:46
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ApplyRefundActivity extends BaseSelectImageActivity implements ApplyRefundPresenter.IApplyCallback {
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.refundTV)
    TextView refundTV;
    @BindView(R.id.submitBtn)
    TextView submitBtn;
    @BindView(R.id.bottomLL)
    LinearLayout bottomLL;
    private ApplyRefundPresenter presenter;
    private int level = 1;
    private OrderBean orderBean;
    private String refundMoney;

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
        presenter = new ApplyRefundPresenter(this, this);
        baseQuickAdapter.setNewData(orderBean.refundImgList());
    }

    @Override
    protected boolean isShowBtn() {
        if (OrderListAdapter.REFUND_STATE.equals(orderBean.getRefundStatus())) {
            return super.isShowBtn();
        } else {
            return false;
        }

    }

    @Override
    protected void setOrderInfo(OrderBean orderBean) {
        super.setOrderInfo(orderBean);
        this.orderBean = orderBean;
        refundMoney = orderBean.getTotalPrice() + "";
        refundTV.setText("退款金额：" + refundMoney);
    }

    @OnClick({R.id.submitBtn, R.id.updateTV})
    public void onViewClicked(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("reason", editText.getText().toString());
        map.put("orderId", orderBean.getId());
        switch (view.getId()) {
            case R.id.submitBtn:
                map.put("money", refundMoney);

                break;
            case R.id.updateTV:
                new XPopup.Builder(this)
                        .asCustom(new PayBottomPopup(this,
                                number -> {
                                    map.put("money", number);
                                }))
                        .show();
                break;
        }
        if (!getFileImages().isEmpty()) {
            presenter.submitData(getFileImages(), map);
        } else {
            presenter.submitData(map);
        }

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
}
