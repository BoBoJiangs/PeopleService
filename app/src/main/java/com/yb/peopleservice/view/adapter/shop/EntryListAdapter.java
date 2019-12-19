package com.yb.peopleservice.view.adapter.shop;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.ClassifyListBean;
import com.yb.peopleservice.model.database.bean.ShopInfo;
import com.yb.peopleservice.utils.ImageLoaderUtil;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EntryListAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public EntryListAdapter() {
        super(R.layout.service_adapter_shop_entry);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        ImageView imageView =helper.getView(R.id.imageView);
        ImageLoaderUtil.loadServerImage(mContext,item.getHeadImg(),imageView);
        helper.setText(R.id.titleTV,item.getName());
        helper.setText(R.id.levelTV,"店铺评分："+item.getLevel());
    }
}