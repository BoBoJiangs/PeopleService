package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 收藏相关
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/2 9:51
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class CollectPresenter extends AbstractPresenter<CollectPresenter.ICollectCallback> {
    public CollectPresenter(Context context, ICollectCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 添加收藏
     *
     * @param targetId 收藏对象的ID
     * @param type     1:商品 2：店铺 3取消收藏
     */
    public void addFavorite(String targetId, int type) {
        AbstractRequestFunc<ServiceRequest> requestFunc = new AbstractRequestFunc<ServiceRequest>(context, new IRequestListener<FavoriteBean>() {
            @Override
            public void onRequestSuccess(FavoriteBean favoriteBean) {
                try {
                    if (type == 3) {
                        getViewCallBack().cancelSuccess();
                    }else{
                        getViewCallBack().collectSuccess(favoriteBean);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                ToastUtils.showLong(error);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, Object> map = new HashMap<>();
                map.put("targetId", targetId);
                map.put("type", type);
                if (type==3){
                    return iRequestServer.deleteFavorite(targetId);
                }else{
                    return iRequestServer.addFavorite(map);
                }

            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 获取是否已收藏
     *
     * @param targetId 收藏对象的ID
     */
    public void getFavorite(String targetId) {
        AbstractRequestFunc<ServiceRequest> requestFunc = new AbstractRequestFunc<ServiceRequest>(context,
                new IRequestListener<FavoriteBean>() {
            @Override
            public void onRequestSuccess(FavoriteBean data) {
                try {
                    getViewCallBack().isCollect(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().cancelSuccess();
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
                return iRequestServer.getFavorite(targetId);
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
     * 收藏
     */
    public interface ICollectCallback extends IViewCallback {

        /**
         * 收藏成功
         */
        void collectSuccess(FavoriteBean favoriteBean);

        /**
         * 取消收藏成功
         */
        void cancelSuccess();

        /**
         * 获取是否已收藏
         */
        void isCollect(FavoriteBean data);
    }
}
