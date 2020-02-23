package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopDetailsPresenter extends AbstractPresenter<ShopDetailsPresenter.IShopCallback> {

    public ShopDetailsPresenter(Context context, IShopCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 获取店铺详情
     * @param shopId
     */
    public void getShopDetails(String shopId) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, new IRequestListener<ShopInfo>() {
            @Override
            public void onRequestSuccess(ShopInfo data) {
                try {
                    getViewCallBack().getDataSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().getDataFail();
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
            public Observable getObservable(ServiceRequest iRequestServer) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("parentId", parentId);
                return iRequestServer.getShopDetails(shopId);
            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 分类列表回调
     */
    public interface IShopCallback extends IViewCallback {


        void getDataSuccess(ShopInfo data);

        void getDataFail();
    }
}
