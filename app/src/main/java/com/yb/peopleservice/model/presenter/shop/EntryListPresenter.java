package com.yb.peopleservice.model.presenter.shop;

import android.content.Context;

import com.yb.peopleservice.model.database.bean.ShopInfo;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.shop.ShopRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import cn.sts.base.presenter.QueryListUIPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 入驻列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EntryListPresenter extends AbstractQueryListPresenter<ShopInfo> {

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public EntryListPresenter(Context context, IQueryListCallback<ShopInfo> IQueryListCallback) {
        super(context, IQueryListCallback);
    }

    @Override
    public void getList(boolean isShowProgress) {
        AbstractRequestFunc<ShopRequest> requestFunc = new AbstractRequestFunc<ShopRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ShopRequest iRequestServer) {
                Map<String, Object> map = new HashMap<>();
                map.put("current", pageIndex);
                return iRequestServer.getShops(map);

            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(isShowProgress);
        BaseRequestServer.getInstance().request(requestFunc);
    }
}
