package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 地图附近的店铺和服务人员
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/2 9:51
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MapPresenter extends AbstractPresenter<MapPresenter.INearbyCallback> {
    public MapPresenter(Context context, INearbyCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 获取附近的店铺或者服务人员
     *
     * @param type 1:服务人员 2：店铺
     */
    public void getNearbyData(LatLng latLng, int type) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object object) {
                try {
                    if (type==AppConstant.SERVICE_TYPE){
                        getViewCallBack().onSuccess((List<ServiceInfo>) object);
                    }else{
                        getViewCallBack().shopSuccess((List<ShopInfo>) object);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                ToastUtils.showLong(error);
                try {
                    getViewCallBack().onFail();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, Double> map = new HashMap<>();
                map.put("latitude", latLng.latitude);
                map.put("longitude", latLng.longitude);
                if (type==AppConstant.SERVICE_TYPE) {
                    return iRequestServer.getNearbyServiceList(map);
                } else {
                    return iRequestServer.getNearbyShopList(map);
                }


            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }


    public interface INearbyCallback extends IViewCallback {

        /**
         * 获取服务人员附近点成功
         */
        void onSuccess(List<ServiceInfo> object);

        /**
         * 获取店铺附近点成功
         */
        void shopSuccess(List<ShopInfo> object);

        /**
         * 获取附近点失败
         */
        void onFail();
    }
}
