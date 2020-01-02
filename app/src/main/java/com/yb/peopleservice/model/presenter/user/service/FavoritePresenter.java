package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 收藏列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FavoritePresenter<T> extends AbstractQueryListPresenter<T> {
    private String type;


    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public FavoritePresenter(Context context,
                             IQueryListCallback<T> IQueryListCallback, String type) {
        super(context, IQueryListCallback);
        this.type = type;
    }

    @Override
    public void getList(boolean isShowProgress) {
        AbstractRequestFunc<ServiceRequest> requestFunc = new AbstractRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                return iRequestServer.getFavoriteService();
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
