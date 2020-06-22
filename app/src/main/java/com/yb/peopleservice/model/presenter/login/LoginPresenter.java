package com.yb.peopleservice.model.presenter.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.yb.peopleservice.constant.enums.LoginType;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.bean.PayResult;
import com.yb.peopleservice.model.bean.PlatformLoginBean;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.UserInfoManager;
import com.yb.peopleservice.model.database.manager.UserManager;
import com.yb.peopleservice.model.eventbean.EventLoginBean;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.shop.ServiceInfoPresenter;
import com.yb.peopleservice.model.presenter.user.personal.PersonalPresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.LoginRequestServer;
import com.yb.peopleservice.model.server.user.classify.LoginRequest;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.view.activity.login.BindPhoneActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        PersonalPresenter.IUserCallback, ServiceInfoPresenter.IServiceInfoCallback {

    private PersonalPresenter personalPresenter;
    private UserManager userManager;
    private UserInfoManager infoManager;
    private User loginBean = new User();
    private ServiceInfoPresenter presenter;

    public LoginPresenter(Context context, ILoginCallback viewCallBack) {
        super(context, viewCallBack);
        personalPresenter = new PersonalPresenter(context, this);
        presenter = new ServiceInfoPresenter(context, this);
        userManager = ManagerFactory.getInstance().getUserManager();
        infoManager = ManagerFactory.getInstance().getUserInfoManager();
    }

    @Override
    public void unbind() {
        super.unbind();
        personalPresenter.unbind();
        presenter.unbind();
    }

    /**
     * 登录
     */
    public void login(String phone, String password, String type) {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context, new IRequestListener<User>() {
            @Override
            public void onRequestSuccess(User data) {
                saveUser(data, phone, password,"", type);
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
                map.put("username", phone);
                map.put("grant_type", "password");
                map.put("password", password);
                map.put("type", type);
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

    /**
     * 第三方平台登录
     *
     * @param code       授权码
     * @param personType 人员类型
     */
    public void platformLogin(String code, String personType) {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context, new IRequestListener<PlatformLoginBean>() {
            @Override
            public void onRequestSuccess(PlatformLoginBean data) {
                try {
                    if (!StringUtils.isEmpty(data.getId())) {
                        //没绑定手机号 去绑定
                        context.startActivity(new Intent(context, BindPhoneActivity.class)
                                .putExtra("userType", personType)
                                .putExtra("id", data.getId()));
                    } else {
                        quickLogin(data.getPhone(), data.getCode(), data.getPersonType());
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
                Map<String, String> map = new HashMap<>();
                map.put("code", code);
                map.put("personType", personType);
                if (LoginType.getLoginType() == LoginType.ALIPAY) {
                    //支付宝登录
                    return iRequestServer.aliPayLogin(map);
                } else {
                    //微信登录
                    return iRequestServer.wxLogin(map);
                }

            }

            @Override
            public Class<LoginRequest> getRequestInterfaceClass() {
                return LoginRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        LoginRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 使用手机号与凭证获取token(快速登录)
     */
    public void quickLogin(String phone, String code, String type) {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context,
                new IRequestListener<User>() {
                    @Override
                    public void onRequestSuccess(User data) {
                        saveUser(data, phone,"", code, type);
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
     * 处理登录成功的逻辑
     *
     * @param data
     * @param phone
     * @param password
     * @param type
     */
    private void saveUser(User data, String phone, String password,
                          String code,String type) {
        try {
            loginBean = data;
            loginBean.setAccount(phone);
            loginBean.setPassword(password);
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
    public void getDataSuccess(UserInfoBean data) {
        loginBean.setUserId(data.getId());
        userManager.update(loginBean);
        UserInfoBean oldInfoBean = loginBean.getInfoBean();
        if (oldInfoBean != null) {
            data.setProvince(oldInfoBean.getProvince());
            data.setCity(oldInfoBean.getCity());
        }
//        infoManager.deleteAll();
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

    /**
     * 获取支付宝授权码
     */
    public void authInfo() {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context,
                new IRequestListener<String>() {
                    @Override
                    public void onRequestSuccess(String data) {
                        authorPage(data);
                    }

                    @Override
                    public void onRequestFailure(String error) {
                        ToastUtils.showLong(error);
                    }

                    @Override
                    public void onRequestCancel() {

                    }
                }) {
            @Override
            public Observable getObservable(LoginRequest iRequestServer) {
                return iRequestServer.authInfo();
            }

            @Override
            public Class<LoginRequest> getRequestInterfaceClass() {
                return LoginRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        LoginRequestServer.getInstance().request(requestFunc);
    }


    /**
     * 吊起支付宝授权
     *
     * @param data
     */
    private void authorPage(String data) {

        Observable.create(new ObservableOnSubscribe<PayResult>() {
            @Override
            public void subscribe(ObservableEmitter<PayResult> emitter) throws Exception {

                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask((Activity) context);
                // 调用授权接口，获取授权结果
                PayResult payResult = new PayResult(authTask.authV2(data, true));
                emitter.onNext(payResult);
                emitter.onComplete();

            }
        })
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(PayResult res) {
                        LogUtils.e(res.toString());
                        String resultInfo = res.getResult();// 同步返回需要验证的信息
                        String resultStatus = res.getResultStatus();
                        try {
                            if (TextUtils.equals(resultStatus, "9000")) {
                                String authorCode = resultInfo.substring(
                                        resultInfo.indexOf("auth_code=") + 10, resultInfo.indexOf("&scope="));
                                EventBus.getDefault().post(new EventLoginBean(authorCode));
                            } else {
                                ToastUtils.showLong("授权失败");
                            }
                        } catch (Exception e) {
                            ToastUtils.showLong("授权失败");
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });


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


        void loginSuccess(User data);

        void loginFail();


    }
}
