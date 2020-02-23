package com.yb.peopleservice.model.presenter.user.personal;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.HomeRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 获取用户信息
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PersonalPresenter extends AbstractPresenter<PersonalPresenter.IUserCallback>  {
    private LogoutPresenter logoutPresenter;
    public PersonalPresenter(Context context, IUserCallback viewCallBack) {
        super(context, viewCallBack);
        logoutPresenter = new LogoutPresenter(context,null);

    }

    @Override
    public void unbind() {
        logoutPresenter.unbind();
        super.unbind();
    }

    /**
     * 退出登录
     */
    public void logout(){
        logoutPresenter.logout();
    }

    /**
     * 用户详情
     */
    public void getUserInfo() {
        BaseRequestFunc<HomeRequest> requestFunc = new BaseRequestFunc<HomeRequest>(context, new IRequestListener<UserInfoBean>() {
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
//                Map<String, Object> map = new HashMap<>();
//                map.put("parentId", parentId);
                return iRequestServer.getUserInfo();
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
