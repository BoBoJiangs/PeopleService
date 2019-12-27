package com.yb.peopleservice.view.adapter.user.classify;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.utils.ImageLoaderUtil;

import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceListAdapter extends BaseQuickAdapter<ServiceListBean, BaseViewHolder> {

    public ServiceListAdapter() {
        super(R.layout.adapter_service);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceListBean item) {
        ImageView imageView = helper.getView(R.id.imageView);
        String priceUnit = item.getPriceUnit();
        if (TextUtils.isEmpty(priceUnit)) {
            priceUnit = "元";
        } else {
            priceUnit = "元/" + priceUnit;
        }
        helper.setText(R.id.nameTV, item.getName());
        helper.setText(R.id.contentTV, "服务内容：" + item.getContentText());
        helper.setText(R.id.priceTV, item.getPrice() + priceUnit);
        helper.setText(R.id.totalTV, "已售：" + item.getTotalSold());
        List<String> images = item.getMainImg();
        ImageLoaderUtil.loadServerImage(mContext, images.isEmpty() ? "" : images.get(0), imageView);
    }
}
