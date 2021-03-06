package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 收藏列表(店铺)
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FavoriteShopPresenter<T> extends AbstractQueryListPresenter<T> {
    private String type;


    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public FavoriteShopPresenter(Context context,
                                 IQueryListCallback<T> IQueryListCallback, String type) {
        super(context, IQueryListCallback);
        this.type = type;
    }

    @Override
    public void getList(boolean isShowProgress) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                return iRequestServer.getFavoriteShop();
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
