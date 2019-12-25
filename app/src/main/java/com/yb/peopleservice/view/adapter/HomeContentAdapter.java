package com.yb.peopleservice.view.adapter;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class HomeContentAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public HomeContentAdapter() {
        super(R.layout.e_adapter_home_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {

    }
}
