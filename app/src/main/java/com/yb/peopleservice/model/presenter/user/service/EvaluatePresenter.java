package com.yb.peopleservice.model.presenter.user.service;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 评价列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EvaluatePresenter<T> extends AbstractQueryListPresenter<T> {
    private String commodityId;
    private String level;//传入数字，好评5，中评3，差评1，有图10 如果不传就是获取全部

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public EvaluatePresenter(Context context,
                             IQueryListCallback<T> IQueryListCallback, String commodityId) {
        super(context, IQueryListCallback);
        this.commodityId = commodityId;
    }

    public String getLevel() {
        return level == null ? "" : level;
    }

    public void setLevel(String level) {
        this.level = level;
        pageIndex = 1;
        getList(true);
    }

    @Override
    public void getList(boolean isShowProgress) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, String> map = new HashMap<>();
                map.put("commodityId", commodityId);
                if (!StringUtils.isEmpty(level)) {
                    map.put("level", level);
                }
                map.put("current", pageIndex + "");
                return iRequestServer.evaluates(map);
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
