package com.yb.peopleservice.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
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
public class HomeContentAdapter extends BaseQuickAdapter<ServiceListBean, BaseViewHolder> {

    public HomeContentAdapter() {
        super(R.layout.e_adapter_home_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceListBean item) {
        ImageView imageView  = helper.getView(R.id.imageView);
        String priceUnit = item.getPriceUnit();
        if (TextUtils.isEmpty(priceUnit)) {
            priceUnit = "元";
        } else {
            priceUnit = "元/" + priceUnit;
        }
        helper.setText(R.id.titleTV, item.getName());
        helper.setGone(R.id.priceTV2,false);
        helper.setText(R.id.priceTV1, item.getPrice() + priceUnit);
        ImageLoaderUtil.loadServerImage(mContext,item.getMainImgs(),imageView);
    }
}
