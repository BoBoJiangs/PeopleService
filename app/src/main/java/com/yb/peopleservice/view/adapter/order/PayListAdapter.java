package com.yb.peopleservice.view.adapter.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.PayInfoBean;

import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/18 21:11
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PayListAdapter extends BaseQuickAdapter<PayInfoBean, BaseViewHolder> {


    public PayListAdapter() {
        super(R.layout.adapter_pay_info);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PayInfoBean item) {
        if (item.getPayType() == 2) {
            helper.setText(R.id.titleTV, "¥" + item.getMoney() + "（补款）");
        } else {
            helper.setText(R.id.titleTV, "¥" + item.getMoney() + "（下单）");
        }
        helper.setText(R.id.timeTV, item.getPayTime());
    }
}
