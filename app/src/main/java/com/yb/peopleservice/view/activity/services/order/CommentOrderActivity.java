package com.yb.peopleservice.view.activity.services.order;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.cb.ratingbar.CBRatingBar;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.presenter.user.order.CommentOrderPresenter;
import com.yb.peopleservice.model.presenter.user.order.OrderStatePresenter;
import com.yb.peopleservice.view.activity.common.BaseSelectImageActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class CommentOrderActivity extends BaseSelectImageActivity implements CommentOrderPresenter.IOrderCallback {
    @BindView(R.id.ratingBar)
    CBRatingBar ratingBar;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.submitBtn)
    TextView submitBtn;
    private CommentOrderPresenter presenter;
    private int level = 1;
    private OrderBean orderBean;

    @Override
    protected int contentViewResID() {
        return R.layout.activity_comment_order;
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter = new CommentOrderPresenter(this, this);
        ratingBar.setOnStarTouchListener(new CBRatingBar.OnStarTouchListener() {
            @Override
            public void onStarTouch(int i) {
                level = i;
            }
        });
    }

    @Override
    protected void setOrderInfo(OrderBean orderBean) {
        super.setOrderInfo(orderBean);
        this.orderBean = orderBean;
    }

    @OnClick(R.id.submitBtn)
    public void onViewClicked() {
        Map<String, Object> map = new HashMap<>();
        map.put("level", level);
        map.put("text", editText.getText().toString());
        map.put("orderId", orderBean.getId());
        if (!getFileImages().isEmpty()) {
            presenter.submitData(getFileImages(), map);
        } else {
            presenter.submitData(map);
        }
    }

    @Override
    public void commentSuccess(Object data) {
        EventBus.getDefault().post(new EventOrderBean());
        ToastUtils.showLong("评论成功");
        finish();
    }

    @Override
    public void commentFail() {

    }
}
