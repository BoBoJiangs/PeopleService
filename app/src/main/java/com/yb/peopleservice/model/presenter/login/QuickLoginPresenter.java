package com.yb.peopleservice.model.presenter.login;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.LoginRequestServer;
import com.yb.peopleservice.model.server.classify.LoginRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 快速登录
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class QuickLoginPresenter extends AbstractPresenter<QuickLoginPresenter.ILoginCallback> implements CodePresenter.ICodeCallback {

    private CodePresenter codePresenter;

    public QuickLoginPresenter(Context context, ILoginCallback viewCallBack) {
        super(context, viewCallBack);
        codePresenter = new CodePresenter(context, this::codeSuccess);
    }

    @Override
    public void unbind() {
        super.unbind();
        codePresenter.unbind();
    }

    /**
     * 使用手机号与验证码获取凭证
     */
    public void getLoginVoucher(String phone, String code) {
        AbstractRequestFunc<LoginRequest> requestFunc = new AbstractRequestFunc<LoginRequest>(context, new IRequestListener<String>() {
            @Override
            public void onRequestSuccess(String data) {
                try {
                    getViewCallBack().codeSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
//                try {
//                    getViewCallBack().regisFail();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                ToastUtils.showLong(error);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(LoginRequest iRequestServer) {
                return iRequestServer.getLoginVoucher(phone, code);
            }

            @Override
            public Class<LoginRequest> getRequestInterfaceClass() {
                return LoginRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 使用手机号与凭证获取token(快速登录)
     */
    public void quickLogin(String phone, String code) {
        AbstractRequestFunc<LoginRequest> requestFunc = new AbstractRequestFunc<LoginRequest>(context,
                new IRequestListener<LoginBean>() {
                    @Override
                    public void onRequestSuccess(LoginBean data) {
                        try {
                            User user = new User();
                            user.setAccess_token(data.getAccess_token());
                            user.setAccount(phone);
//                            user.setPassword(password);
                            user.setTokenType(data.getToken_type());
                            user.setAccountType(data.getScope());
                            ManagerFactory.getInstance().getUserManager().deleteAll();
                            ManagerFactory.getInstance().getUserManager().save(user);
                            getViewCallBack().loginSuccess(data);
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
                        ToastUtils.showLong(error);
                    }

                    @Override
                    public void onRequestCancel() {

                    }
                }) {
            @Override
            public Observable getObservable(LoginRequest iRequestServer) {
                return iRequestServer.quickLogin(phone, code);
            }

            @Override
            public Class<LoginRequest> getRequestInterfaceClass() {
                return LoginRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        LoginRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 获取验证码
     */
    public void getCode(String phone) {
        codePresenter.getCode(phone);
    }

    @Override
    public void codeSuccess(Object data) {
        try {
            getViewCallBack().codeSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册回调
     */
    public interface ILoginCallback extends IViewCallback {
        void codeSuccess(Object object);

        void loginSuccess(LoginBean data);

        void loginFail();

    }
}
