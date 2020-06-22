package com.yb.peopleservice.view.adapter.shop;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.model.presenter.shop.ServiceShopStatePresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.services.StoreEntryActivity;

import org.greenrobot.eventbus.EventBus;

import cn.sts.base.app.AppManager;
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
public class EntryListAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> implements ServiceShopStatePresenter.IServiceInfoCallback {
    private ServiceShopStatePresenter presenter;
    private StoreEntryActivity activity;
    public EntryListAdapter(StoreEntryActivity activity) {
        super(R.layout.service_adapter_shop_entry);
        this.activity = activity;
        presenter = new ServiceShopStatePresenter(activity,this);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        ImageView imageView =helper.getView(R.id.imageView);
        ImageLoaderUtil.loadServerImage(mContext,item.getHeadImg(),imageView);
        helper.setText(R.id.titleTV,item.getName());
        helper.setText(R.id.levelTV,"店铺评分："+item.getLevel());
        helper.getView(R.id.entryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog appDialog = new AppDialog(activity);
                appDialog.title("是否确认入驻店铺？")
                        .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                                presenter.applyEntryShop(item.getId());
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
    public void onSuccess() {
        AppManager.getAppManager().finishActivity();
        EventBus.getDefault().post(new ServiceInfo());
    }

    @Override
    public void onFail() {

    }
}
