package com.yb.peopleservice.model.presenter.user.order;

import android.content.Context;

import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 入驻列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderListPresenter extends AbstractQueryListPresenter<OrderListBean> {
    private int status;

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public OrderListPresenter(Context context, int status, IQueryListCallback<OrderListBean> IQueryListCallback) {
        super(context, IQueryListCallback);
        this.status = status;
    }

    @Override
    public void getList(boolean isShowProgress) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, Integer> map = new HashMap<>();
                map.put("current", pageIndex);
                if (status != OrderBean.ALL) {
                    map.put("status", status);
                }
                return iRequestServer.getOrders(map);

            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(isShowProgress);
        BaseRequestServer.getInstance().request(requestFunc);
    }
}
