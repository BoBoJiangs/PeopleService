package com.yb.peopleservice.view.activity.services.order;

import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.cb.ratingbar.CBRatingBar;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.presenter.user.order.ApplyRefundPresenter;
import com.yb.peopleservice.model.presenter.user.order.CommentOrderPresenter;
import com.yb.peopleservice.view.activity.common.BaseSelectImageActivity;

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
    private ApplyRefundPresenter presenter;
    private int level = 1;
    private OrderBean orderBean;
    private String refundMoney;

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
    }

    @Override
    protected void setOrderInfo(OrderBean orderBean) {
        super.setOrderInfo(orderBean);
        this.orderBean = orderBean;
        refundMoney = orderBean.getTotalPrice()+"";
        refundTV.setText("退款金额："+refundMoney);
    }

    @OnClick(R.id.submitBtn)
    public void onViewClicked() {
        Map<String, Object> map = new HashMap<>();
        map.put("money", refundMoney);
        map.put("reason", editText.getText().toString());
        map.put("orderId", orderBean.getId());
        if (!getImages().isEmpty()) {
            presenter.submitData(getImages(), map);
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
