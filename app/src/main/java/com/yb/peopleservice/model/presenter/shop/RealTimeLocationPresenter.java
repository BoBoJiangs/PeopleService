package com.yb.peopleservice.model.presenter.shop;

import android.accounts.Account;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.presenter.record.RecordPresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 实时上报位置信息（10分钟一次）
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class RealTimeLocationPresenter extends AbstractPresenter<IViewCallback> {
    private RecordPresenter recordPresenter;

    public RealTimeLocationPresenter(Context context, IViewCallback viewCallBack) {
        super(context, viewCallBack);
        this.context = context;
        recordPresenter = new RecordPresenter(context, null);
    }

    @Override
    public void unbind() {
        super.unbind();
    }

    public void queryFileData() {
        recordPresenter.queryFileData();
    }

    /**
     * 实时上报
     */
    public void addGps(AMapLocation location) {
        User user = ManagerFactory.getInstance().getUserManager().getUser();
        //登录的是用户不上传经纬度信息
        if (user.getAccountType().contains(UserType.CUSTOMER.getValue())) {
            return;
        }
        long beforeTime = SPUtils.getInstance().getLong(AppConstant.BEFORE_TIME, 0);
        if (beforeTime != 0) {
            long afterTime = location.getTime();
            if (Math.abs(afterTime - beforeTime) < 1000 * 60 * 10) {
                return;
            }
        }
        SPUtils.getInstance().put(AppConstant.BEFORE_TIME, location.getTime());
        LogUtils.i("开始上报");
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, null) {
            @Override
            public Observable getObservable(ServiceRequest iRequestServer) {
                Map<String, Object> map = new HashMap<>();
                map.put("latitude", location.getLatitude());
                map.put("longitude", location.getLongitude());
                if (user.getAccountType().contains(UserType.STAFF.getValue())) {
                    return iRequestServer.addGps(map);
                } else {
                    return iRequestServer.addShopGps(map);
                }

            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }


}
