package com.yb.peopleservice.model.presenter.shop;

import android.content.Context;

import com.yb.peopleservice.model.bean.shop.PersonListBean;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.shop.ShopRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 服务人员列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PersonPresenter extends AbstractQueryListPresenter<PersonListBean> {

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public PersonPresenter(Context context, IQueryListCallback<PersonListBean> IQueryListCallback) {
        super(context, IQueryListCallback);
    }

    @Override
    public void getList(boolean isShowProgress) {
        AbstractRequestFunc<ShopRequest> requestFunc = new AbstractRequestFunc<ShopRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ShopRequest iRequestServer) {
                return iRequestServer.shopPerson();

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
