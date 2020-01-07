package com.yb.peopleservice.model.presenter.user.service.order;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;

import java.util.HashMap;
import java.util.List;
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
public class ConfirmOrderPresenter extends AbstractPresenter<ConfirmOrderPresenter.IConfirmCallback> {
    public ConfirmOrderPresenter(Context context, IConfirmCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 获取优惠券
     */
    public void getCouponList(String id) {
        AbstractRequestFunc<ServiceRequest> requestFunc = new AbstractRequestFunc<ServiceRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    if(data instanceof List){
                        getViewCallBack().couponSuccess((List<CouponBean>) data);
                    }else if(data instanceof AddressListVO){
                        getViewCallBack().addressSuccess((AddressListVO)data);
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
                return Observable.merge(iRequestServer.getDefaultAddress(), iRequestServer.getCouponList(id));
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
     * 确认订单
     */
    public interface IConfirmCallback extends IViewCallback {

        /**
         * 获取优惠券列表
         */
        void couponSuccess(List<CouponBean> data);

        /**
         * 获取默认地址
         */
        void addressSuccess(AddressListVO data);

    }
}