package com.yb.peopleservice.view.adapter.user;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.order.MyCouponBean;
import com.yb.peopleservice.model.presenter.user.personal.CouponsPresenter;

import org.greenrobot.eventbus.EventBus;

import cn.sts.base.util.NumberUtil;

/**
 * 项目名称:PeopleService
 * 类描述: 优惠券列表适配器
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MyCouponAdapter extends BaseQuickAdapter<MyCouponBean, BaseViewHolder> {
    boolean isShowBtn = true;
    private CouponsPresenter presenter;

    public MyCouponAdapter() {
        super(R.layout.adapter_coupon);

    }

    public MyCouponAdapter(Context context) {
        super(R.layout.adapter_coupon);
        presenter = new CouponsPresenter(context, null);

    }

    public MyCouponAdapter(boolean isShowBtn) {
        super(R.layout.adapter_coupon);
        this.isShowBtn = isShowBtn;

    }

    @Override
    protected void convert(BaseViewHolder helper, MyCouponBean myCouponBean) {
        helper.setGone(R.id.payBtn, isShowBtn);
        CouponBean item = myCouponBean.getCoupon();
        if (item.getType() == 1) {
            helper.setText(R.id.nameTV, "代金券");
            helper.setText(R.id.moneyTV, item.getMoney() + "元");
        } else {
            helper.setText(R.id.nameTV, "折扣券");
            helper.setText(R.id.moneyTV, item.getDiscount() + "折");
        }
        helper.setText(R.id.termTV, "满" +
                NumberUtil.convertFloatZero(item.getDiscountCondition()) + "元可用(不可团购,不叠加)");
        helper.setText(R.id.dateTV, "有效期:" + item.getStartTime() + "-" + item.getEndTime());
        helper.getView(R.id.payBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getCoupons(item.getId(), new CouponsPresenter.ICouponsCallback() {
                    @Override
                    public void couponsSuccess(CouponBean data) {
                        EventBus.getDefault().post(item);
                    }

                    @Override
                    public void getDataFail(String error) {
                        if ("已经领取过优惠券，无法再领取".equals(error)) {
                            EventBus.getDefault().post(item);
                        }
                    }
                });
            }
        });
    }

}
