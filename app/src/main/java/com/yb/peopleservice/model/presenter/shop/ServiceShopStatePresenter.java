package com.yb.peopleservice.model.presenter.shop;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.shop.ShopRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述:服务人员更改入驻店铺的状态
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceShopStatePresenter extends AbstractPresenter<ServiceShopStatePresenter.IServiceInfoCallback> {

    public ServiceShopStatePresenter(Context context, IServiceInfoCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }


    /**
     * 解除关联店铺
     *
     */
    public void unboundShop() {
        BaseRequestFunc<ShopRequest> requestFunc = new BaseRequestFunc<ShopRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().onFail();
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
                return iRequestServer.unboundShop();

            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 申请入驻到店铺
     *
     */
    public void applyEntryShop(String shopId) {
        BaseRequestFunc<ShopRequest> requestFunc = new BaseRequestFunc<ShopRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().onFail();
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
                return iRequestServer.applyEntryShop(shopId);

            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 取消入驻申请店铺
     *
     * @param id 店铺ID
     */
    public void cancelaShop() {
        BaseRequestFunc<ShopRequest> requestFunc = new BaseRequestFunc<ShopRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().onFail();
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
                return iRequestServer.cancelaShop();

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

        void onSuccess();

        void onFail();
    }
}
