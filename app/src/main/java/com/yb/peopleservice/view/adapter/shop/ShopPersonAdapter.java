package com.yb.peopleservice.view.adapter.shop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.PersonListBean;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.shop.AssignPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;

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
public class ShopPersonAdapter extends BaseQuickAdapter<ServiceInfo, BaseViewHolder> implements AssignPresenter.IAssignCallback {
    private Context context;
    private AssignPresenter presenter;
    private String orderId;

    public ShopPersonAdapter(Context context, String orderId) {
        super(R.layout.adapter_shop_person);
        this.context = context;
        this.orderId = orderId;
        presenter = new AssignPresenter(context, this);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceInfo serviceInfo) {
        ImageView imageView = helper.getView(R.id.headImg);
        ImageLoaderUtil.loadServerCircleImage(mContext, serviceInfo.getHeadImg(), imageView);
        helper.setText(R.id.titleTV, serviceInfo.getName());
        helper.getView(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog appDialog = new AppDialog(context)
                        .title("是否确认指派给当前服务人员？")
                        .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                                presenter.assignService(orderId,serviceInfo.getId());
                            }
                        })
                        .negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
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
    public void assignSuccess(Object data) {

        ToastUtils.showLong("指派成功等待服务人员接受");
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void assignFail() {

    }
}
