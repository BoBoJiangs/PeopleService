package com.yb.peopleservice.model.presenter.login;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.LoginRequestServer;
import com.yb.peopleservice.model.server.classify.LoginRequest;

import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 注册
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class RegisterPresenter extends AbstractPresenter<RegisterPresenter.IRegisCallback> implements CodePresenter.ICodeCallback {

    private CodePresenter codePresenter;

    public RegisterPresenter(Context context, IRegisCallback viewCallBack) {
        super(context, viewCallBack);
        codePresenter = new CodePresenter(context,this::codeSuccess);
    }

    @Override
    public void unbind() {
        super.unbind();
        codePresenter.unbind();
    }

    /**
     * 注册
     */
    public void register(Map<String, Object> map) {
        AbstractRequestFunc<LoginRequest> requestFunc = new AbstractRequestFunc<LoginRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().regisSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().regisFail();
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
                return iRequestServer.register(map);
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
    public interface IRegisCallback extends IViewCallback {


        void regisSuccess(Object data);

        void regisFail();

        void codeSuccess(Object data);
    }
}
