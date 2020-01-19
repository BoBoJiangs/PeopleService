//package com.yb.peopleservice.model.presenter.user.service;
//
//import android.content.Context;
//
//import com.amap.api.maps.model.LatLng;
//import com.blankj.utilcode.util.ToastUtils;
//import com.yb.peopleservice.model.bean.user.FavoriteBean;
//import com.yb.peopleservice.model.server.BaseRequestServer;
//import com.yb.peopleservice.model.server.user.ServiceRequest;
//import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import cn.sts.base.callback.IViewCallback;
//import cn.sts.base.model.listener.IRequestListener;
//import cn.sts.base.model.server.request.AbstractRequestFunc;
//import cn.sts.base.presenter.AbstractPresenter;
//import io.reactivex.Observable;
//
///**
// * 项目名称:PeopleService
// * 类描述: 地图附近的店铺和服务人员
// * 创建人:yangbo_ QQ:819463350
// * 创建时间: 2020/1/2 9:51
// * 修改人:
// * 修改时间:
// * 修改描述:
// */
//public class MapPresenter extends AbstractPresenter<MapPresenter.ICollectCallback> {
//    public MapPresenter(Context context, ICollectCallback viewCallBack) {
//        super(context, viewCallBack);
//
//    }
//
//    @Override
//    public void unbind() {
//        super.unbind();
//    }
//
//    /**
//     * 添加收藏
//     *
//     * @param targetId 收藏对象的ID
//     * @param type     1:商品 2：店铺 3取消收藏
//     */
//    public void addFavorite(LatLng latLng) {
//        AbstractRequestFunc<ServiceRequest> requestFunc = new AbstractRequestFunc<ServiceRequest>(context, new IRequestListener<FavoriteBean>() {
//            @Override
//            public void onRequestSuccess(FavoriteBean favoriteBean) {
//                try {
//                    if (type.equals(FavoriteServiceFragment.CANCEL_TYPE)) {
//                        getViewCallBack().cancelSuccess();
//                    } else {
//                        getViewCallBack().collectSuccess(favoriteBean);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onRequestFailure(String error) {
//                ToastUtils.showLong(error);
//            }
//
//            @Override
//            public void onRequestCancel() {
//
//            }
//        }) {
//            @Override
//            public Observable getObservable(ServiceRequest iRequestServer) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("targetId", targetId);
//                map.put("type", type);
//                if (type.equals(FavoriteServiceFragment.CANCEL_TYPE)) {
//                    return iRequestServer.deleteFavorite(targetId);
//                } else {
//                    return iRequestServer.addFavorite(map);
//                }
//
//            }
//
//            @Override
//            public Class<ServiceRequest> getRequestInterfaceClass() {
//                return ServiceRequest.class;
//            }
//        };
//        requestFunc.setShowProgress(true);
//        BaseRequestServer.getInstance().request(requestFunc);
//    }
//
//
//    public interface INearbyCallback extends IViewCallback {
//
//        /**
//         * 收藏成功
//         */
//        void onSuccess(Object object);
//
//        /**
//         * 取消收藏成功
//         */
//        void onFail();
//    }
//}
