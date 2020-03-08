package com.yb.peopleservice.model.presenter.login;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.UserInfoManager;
import com.yb.peopleservice.model.database.manager.UserManager;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.user.personal.PersonalPresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.LoginRequestServer;
import com.yb.peopleservice.model.server.user.classify.LoginRequest;
import com.yb.peopleservice.utils.AppUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 登录
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class LoginPresenter extends AbstractPresenter<LoginPresenter.ILoginCallback> implements
        PersonalPresenter.IUserCallback {

    private PersonalPresenter personalPresenter;
    private UserManager userManager;
    private UserInfoManager infoManager;
    private LoginBean loginBean;

    public LoginPresenter(Context context, ILoginCallback viewCallBack) {
        super(context, viewCallBack);
        personalPresenter = new PersonalPresenter(context, this);
        userManager = ManagerFactory.getInstance().getUserManager();
        infoManager = ManagerFactory.getInstance().getUserInfoManager();
    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 登录
     */
    public void login(String phone, String password) {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context, new IRequestListener<LoginBean>() {
            @Override
            public void onRequestSuccess(LoginBean data) {
                try {
                    loginBean = data;
                    User user = new User();
                    user.setAccess_token(data.getAccess_token());
                    user.setAccount(phone);
                    user.setPassword(password);
                    user.setTokenType(data.getToken_type());
                    user.setAccountType(data.getScope());
                    userManager.deleteAll();
                    userManager.save(user);
                    UserManager.user = null;
                    if (data.getScope().contains(LoginBean.USER_TYPE)) {
                        if (userManager.getUser().getInfoBean() != null) {
                            getDataSuccess(userManager.getUser().getInfoBean());
                        } else {
                            personalPresenter.getUserInfo();
                        }
                    } else {
                        getViewCallBack().loginSuccess(data);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().loginFail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtils.showLong("用户名或密码错误");
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(LoginRequest iRequestServer) {
                Map<String, Object> map = new HashMap<>();
                map.put("username", phone);
                map.put("grant_type", "password");
                map.put("password", password);
                return iRequestServer.login(map);
            }

            @Override
            public Class<LoginRequest> getRequestInterfaceClass() {
                return LoginRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        LoginRequestServer.getInstance().request(requestFunc);
    }

    @Override
    public void getDataSuccess(UserInfoBean data) {
        User user = userManager.getUser();
        user.setUserId(data.getId());
        userManager.update(user);
        infoManager.deleteAll();
        infoManager.save(data);
        ChatPresenter.getInstance().setCustomerAlias(context,
                AppUtils.formatID(data.getId()));
        ChatPresenter.getInstance().getUserInfo(AppUtils.formatID(data.getId()),
                data.getNickname());
        try {
            getViewCallBack().loginSuccess(loginBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail() {
        ToastUtils.showLong("登陆失败");
    }


    /**
     * 注册回调
     */
    public interface ILoginCallback extends IViewCallback {


        void loginSuccess(LoginBean data);

        void loginFail();


    }
}
