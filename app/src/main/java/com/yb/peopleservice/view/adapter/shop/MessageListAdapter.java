package com.yb.peopleservice.view.adapter.shop;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.MessageBean;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
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
public class MessageListAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {

    public MessageListAdapter() {
        super(R.layout.adapter_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.titleTV,item.getTitle());
        helper.setText(R.id.contentTV,item.getContent());
        helper.setText(R.id.timeTV,item.getTimestamp());
    }
}
