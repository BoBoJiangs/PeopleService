package com.yb.peopleservice.view.adapter.user;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.presenter.user.personal.CouponsPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
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
public class CouponAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {
    boolean isShowBtn = true;
    private CouponsPresenter presenter;

    public CouponAdapter() {
        super(R.layout.adapter_coupon);

    }

    public CouponAdapter(Context context) {
        super(R.layout.adapter_coupon);
        presenter = new CouponsPresenter(context, null);

    }

    public CouponAdapter(boolean isShowBtn) {
        super(R.layout.adapter_coupon);
        this.isShowBtn = isShowBtn;

    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {
        helper.setGone(R.id.payBtn, isShowBtn);
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
