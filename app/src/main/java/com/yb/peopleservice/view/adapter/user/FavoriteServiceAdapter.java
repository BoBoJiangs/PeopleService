package com.yb.peopleservice.view.adapter.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.CollectPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;

import java.util.List;

import cn.sts.base.view.widget.AppDialog;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FavoriteServiceAdapter extends BaseQuickAdapter<ServiceListBean, BaseViewHolder>
        implements CollectPresenter.ICollectCallback {

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private CollectPresenter presenter;
    private int position;

    public FavoriteServiceAdapter() {
        super(R.layout.adapter_shop_favorite);
        viewBinderHelper.setOpenOnlyOne(true);
        presenter = new CollectPresenter(mContext, this);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceListBean item) {
        ImageView imageView = helper.getView(R.id.imageView);
        SwipeRevealLayout swipeRevealLayout = helper.getView(R.id.swipe_layout);
        helper.setText(R.id.nameTV, item.getName());
        if (!TextUtils.isEmpty(item.getMainImgs())) {
            List<String> imags = item.getMainImg();
            if (!imags.isEmpty()) {
                ImageLoaderUtil.loadServerImage(mContext, imags.get(0), imageView);
            }

        }
        helper.addOnClickListener(R.id.front_layout);
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
        helper.getView(R.id.delete_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog appDialog = new AppDialog(mContext);
                appDialog.title(R.string.is_cancel_collect)
                        .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                                position = helper.getAdapterPosition();
                                presenter.addFavorite(item.getId(), FavoriteServiceFragment.CANCEL_TYPE);
                            }
                        });

                appDialog.negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                    }
                });
                appDialog.setCancelable(false);
                appDialog.show();

            }
        });
    }



    @Override
    public void collectSuccess(FavoriteBean favoriteBean) {

    }

    @Override
    public void cancelSuccess() {
        remove(position);
        if (getData().isEmpty()) {
            setEmptyView(View.inflate(mContext, R.layout.view_nodata, null));
        }

    }

    @Override
    public void isCollect(FavoriteBean data) {

    }
}
