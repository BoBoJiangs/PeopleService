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
 * 类描述: 入驻列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceListPresenter extends AbstractQueryListPresenter<ShopInfo> {
    private String classId;//分类ID
    private boolean isShop;

    public ServiceListPresenter(String classId, Context context,
                                IQueryListCallback<ShopInfo> IQueryListCallback) {
        this(classId,context,IQueryListCallback,false);
    }

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public ServiceListPresenter(String classId, Context context,
                                IQueryListCallback<ShopInfo> IQueryListCallback,boolean isShop) {
        super(context, IQueryListCallback);
        this.classId = classId;
        this.isShop = isShop;
    }

    @Override
    public void getList(boolean isShowProgress) {
        AbstractRequestFunc<ServiceRequest> requestFunc = new AbstractRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, Integer> map = new HashMap<>();
                map.put("current", pageIndex);
                if (isShop){
                    return iRequestServer.getShopList(classId);
                }else {
                    return iRequestServer.getServiceList(classId);
                }


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
