package com.yb.peopleservice.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.HomeListBean;

import java.util.List;


/**
 * 类描述: 首页列表适配器
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21  14:30
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class HomeListAdapter extends BaseMultiItemQuickAdapter<HomeListBean, BaseViewHolder> {
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeListAdapter(List<HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        addItemType(HomeListBean.TITLE_TYPE, R.layout.e_adapter_home_title);
        addItemType(HomeListBean.CONTENT_TYPE, R.layout.e_adapter_home_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean item) {

        switch (helper.getItemViewType()) {
            case HomeListBean.TITLE_TYPE:

                break;
            case HomeListBean.CONTENT_TYPE:
                TextView textView= helper.getView(R.id.priceTV2);
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                break;
        }

    }
}
