package com.yb.peopleservice.model.presenter.user.personal;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 优惠券列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class CouponsPresenter<T> extends AbstractQueryListPresenter<T> {

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public CouponsPresenter(Context context,
                            IQueryListCallback<T> IQueryListCallback) {
        super(context, IQueryListCallback);
    }


    @Override
    public void getList(boolean isShowProgress) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                return iRequestServer.couponsList();
            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(isShowProgress);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 领取优惠券
     */
    public void getCoupons(String couponId,ICouponsCallback callback) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, new IRequestListener<CouponBean>() {
            @Override
            public void onRequestSuccess(CouponBean data) {
                try {
                    callback.couponsSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    callback.getDataFail(error);
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
                Map<String, Object> map = new HashMap<>();
                map.put("couponId ", couponId);
                return iRequestServer.getCoupons(couponId);
            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    public interface ICouponsCallback extends IViewCallback {


        void couponsSuccess(CouponBean data);

        void getDataFail(String error);
    }
}
