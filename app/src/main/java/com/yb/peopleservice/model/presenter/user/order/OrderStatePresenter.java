package com.yb.peopleservice.model.presenter.user.order;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.OrderRequest;

import java.util.List;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 订单状态操作
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderStatePresenter extends AbstractPresenter<OrderStatePresenter.IOrderCallback> {

    public OrderStatePresenter(Context context, IOrderCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 接受/拒绝 服务
     *
     * @param orderId
     */
    public void acceptOrder(String orderId, boolean isAccept, int orderStatus) {
        BaseRequestFunc<OrderRequest> requestFunc = new BaseRequestFunc<OrderRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().acceptSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().acceptFail();
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
            public Observable getObservable(OrderRequest iRequestServer) {
                switch (orderStatus) {
                    case OrderBean.ASSIGN:
                        if (isAccept) {
                            return iRequestServer.acceptOrder(orderId);
                        } else {
                            return iRequestServer.refuseOrder(orderId);
                        }

                    case OrderBean.WAITING:
                        return iRequestServer.arriveOrder(orderId);
                    case OrderBean.ARRIVED:
                        return iRequestServer.startOrder(orderId);
                    case OrderBean.DOING:
                        return iRequestServer.endOrder(orderId);
                }
                return iRequestServer.arriveOrder(orderId);
            }

            @Override
            public Class<OrderRequest> getRequestInterfaceClass() {
                return OrderRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 分类列表回调
     */
    public interface IOrderCallback extends IViewCallback {


        void acceptSuccess(Object data);

        void acceptFail();
    }
}
