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
    public static final int SERVICE_TYPE = 1;//服务类型
    public static final int SHOP_TYPE = 2;//店铺类型
    public static final int GRUOP_TYPE = 3;//团购类型

    public static final String DEFAULT = "0";//默认排序
    public static final String NUMBER = "1";//销量
    public static final String PRICE_LESS = "2";//价格由低到高
    public static final String PRICE_MORE = "3";//价格由高到低
    private String classId;//分类ID
    private int type;
    private String order = DEFAULT;


//    public ServiceListPresenter(String classId, Context context,
//                                IQueryListCallback<ShopInfo> IQueryListCallback) {
//        this(classId, context, IQueryListCallback, false);
//    }

    public void setOrder(String order) {
        pageIndex = 1;
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public ServiceListPresenter(String classId, Context context,
                                IQueryListCallback<ShopInfo> IQueryListCallback, int type) {
        super(context, IQueryListCallback);
        this.classId = classId;
        this.type = type;
    }

    @Override
    public void getList(boolean isShowProgress) {
        AbstractRequestFunc<ServiceRequest> requestFunc = new AbstractRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, String> map = new HashMap<>();
                map.put("current", pageIndex + "");
                switch (type) {
                    case SERVICE_TYPE:
                        map.put("order", order);
                        return iRequestServer.getServiceList(classId, map);
                    case SHOP_TYPE:
                        return iRequestServer.getShopList(classId);
                    default:
                        return iRequestServer.groupBuyList(map);
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
