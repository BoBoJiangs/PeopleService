package com.yb.peopleservice.model.presenter;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.presenter.login.CodePresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.LoginRequestServer;
import com.yb.peopleservice.model.server.user.classify.LoginRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 忘记密码
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PasswordResetPresenter extends AbstractPresenter<PasswordResetPresenter.IResetPassword> implements CodePresenter.ICodeCallback {
    private CodePresenter codePresenter;
    public PasswordResetPresenter(Context context, IResetPassword viewCallBack) {
        super(context, viewCallBack);
        codePresenter = new CodePresenter(context,this);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    public void getCode(String phone){
        codePresenter.getCode(phone);
    }

    /**
     * 忘记密码
     */
    public void resetPass(final String userAccount, String password, String smsCode) {
        BaseRequestFunc<LoginRequest> requestFunc = new BaseRequestFunc<LoginRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().resetPasswordSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                ToastUtils.showLong("重置密码失败");
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(LoginRequest iRequestServer) {
                final Map<String, Object> map = new HashMap<>();
                map.put("phone", userAccount);
                map.put("password", password);
                map.put("code", smsCode);
                return iRequestServer.resetPass(map);
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
    public void codeSuccess(Object data) {
        try {
            getViewCallBack().codeSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证码回调
     */
    public interface IResetPassword extends IViewCallback {
        /**
         * 设置密码成功
         */
        void resetPasswordSuccess();

        /**
         * 设置密码失败
         */
        void resetPasswordFailed(String error);

        void codeSuccess(Object data);

    }
}
