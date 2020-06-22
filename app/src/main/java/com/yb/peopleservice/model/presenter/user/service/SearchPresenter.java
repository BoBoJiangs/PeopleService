package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
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
public class SearchPresenter extends AbstractQueryListPresenter<ShopInfo> {

    public static final String DEFAULT = "0";//默认排序
    public static final String NUMBER = "1";//销量
    public static final String PRICE_LESS = "2";//价格由低到高
    public static final String PRICE_MORE = "3";//价格由高到低
    private String order = DEFAULT;
    private String keywords="";



    public void setOrder(String order) {
        pageIndex = 1;
        this.order = order;
    }

    public void setKeyWords(String keywords) {
        pageIndex = 1;
        this.keywords = keywords;
    }

    public String getOrder() {
        return order;
    }

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public SearchPresenter(Context context,IQueryListCallback<ShopInfo> IQueryListCallback) {
        super(context, IQueryListCallback);
    }

    @Override
    public void getList(boolean isShowProgress) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, String> map = new HashMap<>();
                map.put("current", pageIndex + "");
                map.put("keywords", keywords);
                map.put("province", AppConstant.PROVINCE);
                map.put("city", AppConstant.CITY);
                return iRequestServer.searchData(map);
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
