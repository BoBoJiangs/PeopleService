package com.yb.peopleservice.model.presenter.shop;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.shop.ShopRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺认证
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EditServicePresenter extends AbstractPresenter<EditServicePresenter.IEditCallback> {

    public EditServicePresenter(Context context, IEditCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 修改服务人员信息
     */
    public void editServiceInfo(ServiceInfo serviceInfo) {
        BaseRequestFunc<ShopRequest> requestFunc = new BaseRequestFunc<ShopRequest>(context, new IRequestListener<ShopInfo>() {
            @Override
            public void onRequestSuccess(ShopInfo data) {
                try {
                    getViewCallBack().editSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().editFail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtils.showLong(error);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(ShopRequest iRequestServer) {
                //店铺
                return iRequestServer.editStaffsInfo(serviceInfo);

            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }


    public interface IEditCallback extends IViewCallback {


        void editSuccess(ShopInfo data);

        void editFail();
    }
}
