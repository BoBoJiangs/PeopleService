package com.yb.peopleservice.model.presenter.login;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.UserInfoManager;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.LoginRequest;
import com.yb.peopleservice.view.activity.login.LoginActivity;
import com.yb.peopleservice.view.activity.main.MainActivity;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.sts.base.app.AppManager;
import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 退出
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class LogoutPresenter extends AbstractPresenter<IViewCallback> {

    public LogoutPresenter(Context context, IViewCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 退出
     */
    public void logout() {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context, new IRequestListener<User>() {
            @Override
            public void onRequestSuccess(User data) {
                try {
                    AppManager.getAppManager().finishAllActivity();
                    ManagerFactory.getInstance().getUserManager().deleteAll();
//                    ManagerFactory.getInstance().getUserInfoManager().deleteAll();
//                    User user = ManagerFactory.getInstance().getUserManager().getUser();
//                    user.setPassword("");
//                    ManagerFactory.getInstance().getUserManager().saveOrUpdate(user);
                    JMessageClient.logout();
                    JPushInterface.deleteAlias(context,1);
                    JPushInterface.cleanTags(context,1);
                    context.startActivity(new Intent(context, MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
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
            public Observable getObservable(LoginRequest iRequestServer) {
                return iRequestServer.logout();
            }

            @Override
            public Class<LoginRequest> getRequestInterfaceClass() {
                return LoginRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }



}
