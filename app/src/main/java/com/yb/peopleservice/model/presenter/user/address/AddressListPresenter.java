package com.yb.peopleservice.model.presenter.user.address;

import android.content.Context;

import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.HomeRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 地址列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/12  14:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class AddressListPresenter extends AbstractQueryListPresenter<HomeRequest> {

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public AddressListPresenter(Context context, IQueryListCallback<HomeRequest> IQueryListCallback) {
        super(context, IQueryListCallback);
    }

    @Override
    public void getList(boolean isShowProgress) {
        AbstractRequestFunc<HomeRequest> requestFunc = new AbstractRequestFunc<HomeRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(HomeRequest iRequestServer) {
                Map<String, Object> map = new HashMap<>();
                map.put("page", pageIndex);
                return iRequestServer.getMyAddressList();

            }

            @Override
            public Class<HomeRequest> getRequestInterfaceClass() {
                return HomeRequest.class;
            }
        };
        requestFunc.setShowProgress(isShowProgress);
        BaseRequestServer.getInstance().request(requestFunc);
    }
}
