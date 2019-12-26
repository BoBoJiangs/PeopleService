package com.yb.peopleservice.model.presenter.user;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.HomeRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 更新用户信息
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class UpdateUserPresenter extends AbstractPresenter<UpdateUserPresenter.IUserCallback> {

    public UpdateUserPresenter(Context context, IUserCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 更新用户信息
     */
    public void setUserInfo(UserInfoBean bean) {
        AbstractRequestFunc<HomeRequest> requestFunc = new AbstractRequestFunc<HomeRequest>(context, new IRequestListener<UserInfoBean>() {
            @Override
            public void onRequestSuccess(UserInfoBean data) {
                try {
                    getViewCallBack().getDataSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().getDataFail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtils.showLong(error);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(HomeRequest iRequestServer) {
                return iRequestServer.setUserInfo(bean);
            }

            @Override
            public Class<HomeRequest> getRequestInterfaceClass() {
                return HomeRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 用户详情回调
     */
    public interface IUserCallback extends IViewCallback {


        void getDataSuccess(UserInfoBean data);

        void getDataFail();
    }
}
