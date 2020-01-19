package com.yb.peopleservice.view.adapter.order;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.view.weight.CustomPopWindow;

/**
 * 项目名称:PeopleService
 * 类描述: 订单列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    private Context context;

    public OrderListAdapter(Context context) {
        super(R.layout.item_order_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.getView(R.id.moreIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(v);
            }
        });
    }

    private void showPop(View view) {
       new CustomPopWindow.PopupWindowBuilder(context)
                .setView(R.layout.order_delete_pop)
                .create()
                .showAsDropDown(view, SizeUtils.dp2px(-10), SizeUtils.dp2px(-10));
    }
}
