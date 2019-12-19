package com.yb.peopleservice.model.presenter.shop;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.shop.ServiceInfo;
import com.yb.peopleservice.model.database.bean.ShopInfo;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.shop.ShopRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 获取服务人员信息
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceInfoPresenter extends AbstractPresenter<ServiceInfoPresenter.IServiceInfoCallback> {

    public ServiceInfoPresenter(Context context, IServiceInfoCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }


    /**
     * 获取店铺信息
     */
    public void getServiceInfo() {
        AbstractRequestFunc<ShopRequest> requestFunc = new AbstractRequestFunc<ShopRequest>(context, new IRequestListener<ServiceInfo>() {
            @Override
            public void onRequestSuccess(ServiceInfo data) {
                try {
                    getViewCallBack().serviceInfoSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().serviceInfoFail();
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
                return iRequestServer.getServiceInfo();

            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }


    public interface IServiceInfoCallback extends IViewCallback {


        void serviceInfoSuccess(ServiceInfo data);

        void serviceInfoFail();
    }
}