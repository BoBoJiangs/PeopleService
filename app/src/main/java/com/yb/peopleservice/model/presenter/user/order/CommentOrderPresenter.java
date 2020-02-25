package com.yb.peopleservice.model.presenter.user.order;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.ServiceRequest;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述:订单评论
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/23 22:05
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class CommentOrderPresenter extends AbstractPresenter<CommentOrderPresenter.IOrderCallback> implements
        UploadFilePresenter.IViewUploadFile {

    private UploadFilePresenter presenter;
    private Map<String, Object> map;

    public CommentOrderPresenter(Context context, CommentOrderPresenter.IOrderCallback viewCallBack) {
        super(context, viewCallBack);
        presenter = new UploadFilePresenter(context, this);

    }

    @Override
    public void unbind() {
        super.unbind();
        presenter.unbind();
    }

    /**
     * 提交有图片的数据
     *
     * @param files
     * @param map
     */
    public void submitData(List<File> files, Map<String, Object> map) {
        this.map = map;
        presenter.uploadFile(presenter.getBuilder(files), true);
    }

    /**
     * 提交无图片的数据
     *
     * @param map
     */
    public void submitData(Map<String, Object> map) {
        this.map = map;
        evaluatesOrder(map);
    }

    /**
     * 订单评论
     */
    public void evaluatesOrder(Map<String, Object> map) {
        BaseRequestFunc<ServiceRequest> requestFunc = new BaseRequestFunc<ServiceRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().commentSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().commentFail();
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
            public Observable getObservable(ServiceRequest iRequestServer) {
                return iRequestServer.evaluatesOrder(map);
            }

            @Override
            public Class<ServiceRequest> getRequestInterfaceClass() {
                return ServiceRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    @Override
    public void uploadSuccess(List<String> files) {
        map.put("imgs", new Gson().toJson(files));
        evaluatesOrder(map);
    }

    @Override
    public void uploadFail() {

    }

    public interface IOrderCallback extends IViewCallback {


        void commentSuccess(Object data);

        void commentFail();
    }
}
