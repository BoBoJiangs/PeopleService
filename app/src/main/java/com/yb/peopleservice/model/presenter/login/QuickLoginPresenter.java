package com.yb.peopleservice.model.presenter.login;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.UserInfoManager;
import com.yb.peopleservice.model.database.manager.UserManager;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.shop.ServiceInfoPresenter;
import com.yb.peopleservice.model.presenter.user.personal.PersonalPresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.LoginRequestServer;
import com.yb.peopleservice.model.server.user.classify.LoginRequest;
import com.yb.peopleservice.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

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
public class QuickLoginPresenter extends AbstractPresenter<QuickLoginPresenter.ILoginCallback> implements
        CodePresenter.ICodeCallback, RegisterPresenter.IRegisCallback, PersonalPresenter.IUserCallback,
        ServiceInfoPresenter.IServiceInfoCallback{

    private CodePresenter codePresenter;
    private RegisterPresenter registerPresenter;

    private PersonalPresenter personalPresenter;
    private UserManager userManager;
    private UserInfoManager infoManager;
    private User loginBean;
    private ServiceInfoPresenter presenter;

    public QuickLoginPresenter(Context context, ILoginCallback viewCallBack) {
        super(context, viewCallBack);
        codePresenter = new CodePresenter(context, this::codeSuccess);
        registerPresenter = new RegisterPresenter(context, this);

        personalPresenter = new PersonalPresenter(context, this);
        presenter = new ServiceInfoPresenter(context, this);
        userManager = ManagerFactory.getInstance().getUserManager();
        infoManager = ManagerFactory.getInstance().getUserInfoManager();
    }

    @Override
    public void unbind() {
        super.unbind();
        codePresenter.unbind();
        registerPresenter.unbind();
        personalPresenter.unbind();
    }

    public void checkUserName(String phone) {
        registerPresenter.checkUserName(phone);
    }

    /**
     * 使用手机号与验证码获取凭证
     */
    public void getLoginVoucher(String phone, String code,String type) {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context, new IRequestListener<String>() {
            @Override
            public void onRequestSuccess(String data) {
                try {
                    quickLogin(phone, data,type);
//                    getViewCallBack().codeSuccess(data);
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
        LoginRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 使用手机号与凭证获取token(快速登录)
     */
    public void quickLogin(String phone, String code,String type) {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context,
                new IRequestListener<User>() {
                    @Override
                    public void onRequestSuccess(User data) {
                        try {
                            loginBean = data;
                            loginBean.setAccount(phone);
                            loginBean.setCode(code);
                            loginBean.setType(type);
                            userManager.deleteAll();
                            userManager.save(loginBean);
                            if (data.getAccountType().contains(UserType.CUSTOMER.getValue())) {
                                if (userManager.getUser().getInfoBean() != null) {
                                    getDataSuccess(userManager.getUser().getInfoBean());
                                } else {
                                    personalPresenter.getUserInfo();
                                }
                            } else if (data.getAccountType().contains(UserType.STAFF.getValue())) {
                                presenter.getServiceInfo();
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
                        ToastUtils.showLong(error);
                    }

                    @Override
                    public void onRequestCancel() {

                    }
                }) {
            @Override
            public Observable getObservable(LoginRequest iRequestServer) {
                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone);
                map.put("code", code);
                map.put("grant_type", "password");
                map.put("type", type);
                return iRequestServer.quickLogin(map);
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
    public void checkSuccess(Boolean data) {
        try {
            getViewCallBack().checkSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void regisSuccess(Object data) {

    }

    @Override
    public void regisFail() {

    }

    @Override
    public void codeSuccess(Object data) {
        try {
            getViewCallBack().codeSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataSuccess(UserInfoBean data) {
        loginBean.setUserId(data.getId());
        userManager.update(loginBean);

//        infoManager.deleteAll();
        UserInfoBean oldInfoBean = loginBean.getInfoBean();
        if (oldInfoBean != null) {
            data.setProvince(oldInfoBean.getProvince());
            data.setCity(oldInfoBean.getCity());
        }
        infoManager.saveOrUpdate(data);

        //注册PUSH
        ChatPresenter.getInstance().setCustomerAlias(context,
                AppUtils.formatID(data.getId()));
        //注册IM
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

    @Override
    public void serviceInfoSuccess(ServiceInfo data) {
        loginBean.setUserId(data.getId());
        userManager.update(loginBean);

        //注册IM
        ChatPresenter.getInstance().getUserInfo(AppUtils.formatID(data.getId()),
                data.getName());
        //注册PUSH
        ChatPresenter.getInstance().setServiceAlias(context, data);
        try {
            getViewCallBack().loginSuccess(loginBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serviceInfoFail() {

    }

    /**
     * 注册回调
     */
    public interface ILoginCallback extends IViewCallback {
        void codeSuccess(Object object);

        void loginSuccess(User data);

        void loginFail();

        void checkSuccess(boolean isExist);

    }
}
