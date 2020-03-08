package com.yb.peopleservice.model.presenter.user;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.HomeRequest;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 修改密码
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class UpdatePassWordPresenter extends AbstractPresenter<UpdatePassWordPresenter.IPwdCallback> {

    public UpdatePassWordPresenter(Context context, IPwdCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 修改密码
     */
    public void password(String passWord,String newPwd) {
        BaseRequestFunc<HomeRequest> requestFunc = new BaseRequestFunc<HomeRequest>(context, new IRequestListener<UserInfoBean>() {
            @Override
            public void onRequestSuccess(UserInfoBean data) {
                try {
                    getViewCallBack().updateSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().updateFail();
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
                Map<String,Object> map = new HashMap<>();
                map.put("oldPassword",passWord);
                map.put("newPassword",newPwd);
                return iRequestServer.password(map);
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
    public interface IPwdCallback extends IViewCallback {


        void updateSuccess(UserInfoBean data);

        void updateFail();
    }
}
