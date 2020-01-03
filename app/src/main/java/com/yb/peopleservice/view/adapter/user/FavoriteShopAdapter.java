package com.yb.peopleservice.view.adapter.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
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
public class FavoriteShopAdapter extends BaseQuickAdapter<FavoriteBean, BaseViewHolder> {

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public FavoriteShopAdapter() {
        super(R.layout.adapter_shop_favorite);
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoriteBean item) {
        ShopInfo shopInfo = item.getShop();
        ImageView imageView = helper.getView(R.id.imageView);
        SwipeRevealLayout swipeRevealLayout = helper.getView(R.id.swipe_layout);
        if (shopInfo != null) {
            helper.setText(R.id.nameTV, shopInfo.getName());
            ImageLoaderUtil.loadServerImage(mContext, shopInfo.getHeadImg(), imageView);
        }
        viewBinderHelper.bind(swipeRevealLayout, item.getId());
        helper.getView(R.id.collectIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swipeRevealLayout.isOpened()) {
                    swipeRevealLayout.close(true);
                } else {
                    swipeRevealLayout.open(true);
                }

            }
        });
    }
}
