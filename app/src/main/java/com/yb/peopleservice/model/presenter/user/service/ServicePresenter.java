package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
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
 * 类描述: 收藏相关
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/2 9:51
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServicePresenter extends AbstractPresenter<ServicePresenter.IServiceCallback> {
    public ServicePresenter(Context context, IServiceCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 查询团购订单
     */
    public void buyGroup(String id) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context,
                new IRequestListener<List<GroupBean>>() {
                    @Override
                    public void onRequestSuccess(List<GroupBean> data) {
                        try {
                            getViewCallBack().groupSuccess(data);

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
                return iRequestServer.buyGroup(id);

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
     * 获取优惠券
     */
    public void getCouponList(String id) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context,
                new IRequestListener<List<CouponBean>>() {
                    @Override
                    public void onRequestSuccess(List<CouponBean> data) {
                        try {
                            getViewCallBack().couponSuccess(data);
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
                return iRequestServer.getCouponList(id);
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
     * 服务
     */
    public interface IServiceCallback extends IViewCallback {

        /**
         * 获取团购列表
         */
        void groupSuccess(List<GroupBean> favoriteBean);

        void couponSuccess(List<CouponBean> data);

    }
}
