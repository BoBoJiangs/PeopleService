package com.yb.peopleservice.model.presenter.user.order;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.OrderRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 订单详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderDetailsPresenter extends AbstractPresenter<OrderDetailsPresenter.IDetailsCallback> implements
        OrderStatePresenter.IOrderCallback {
    private OrderStatePresenter presenter;

    public OrderDetailsPresenter(Context context, IDetailsCallback viewCallBack) {
        super(context, viewCallBack);
        presenter = new OrderStatePresenter(context, this);
    }

    @Override
    public void unbind() {
        super.unbind();
        presenter.unbind();
    }

    public void acceptOrder(OrderBean orderBean, boolean isAccept, int orderStatus) {
        presenter.acceptOrder(orderBean,isAccept,orderStatus);
    }

    /**
     * 查询录音
     */
    public void orderDetails(String orderId) {
        BaseRequestFunc<OrderRequest> requestFunc = new BaseRequestFunc<OrderRequest>(context, new IRequestListener<OrderListBean>() {
            @Override
            public void onRequestSuccess(OrderListBean data) {
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
            public Observable getObservable(OrderRequest iRequestServer) {
                return iRequestServer.orderDetails(orderId);
            }

            @Override
            public Class<OrderRequest> getRequestInterfaceClass() {
                return OrderRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    @Override
    public void acceptSuccess(Object data) {
        ToastUtils.showLong("订单处理成功");
        EventBus.getDefault().post(new EventOrderBean());
        try {
            getViewCallBack().acceptSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acceptFail() {

    }

    /**
     * 分类列表回调
     */
    public interface IDetailsCallback extends IViewCallback {


        void getDataSuccess(OrderListBean data);

        void getDataFail();

        void acceptSuccess(Object data);
    }
}
