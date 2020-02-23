package com.yb.peopleservice.model.presenter.user;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.ClassifyRequest;

import java.util.List;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ClassifyPresenter extends AbstractPresenter<ClassifyPresenter.IClassCallback> {

    public ClassifyPresenter(Context context, IClassCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 获取分类列表
     * @param parentId
     */
    public void getCategoryInfo(String parentId) {
        BaseRequestFunc<ClassifyRequest> requestFunc = new BaseRequestFunc<ClassifyRequest>(context, new IRequestListener<List<ClassifyListBean>>() {
            @Override
            public void onRequestSuccess(List<ClassifyListBean> data) {
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
            public Observable getObservable(ClassifyRequest iRequestServer) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("parentId", parentId);
                return iRequestServer.getCategoryInfo();
            }

            @Override
            public Class<ClassifyRequest> getRequestInterfaceClass() {
                return ClassifyRequest.class;
            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 分类列表回调
     */
    public interface IClassCallback extends IViewCallback {


        void getDataSuccess(List<ClassifyListBean> data);

        void getDataFail();
    }
}
