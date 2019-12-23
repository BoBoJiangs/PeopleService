package com.yb.peopleservice.model.presenter.user;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.BannerListVO;
import com.yb.peopleservice.model.bean.ClassifyListBean;
import com.yb.peopleservice.model.bean.HomeListBean;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.classify.HomeRequest;
import com.yb.peopleservice.model.server.classify.HomeRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述: 轮播图接口
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class BannerPresenter extends AbstractPresenter<BannerPresenter.IBannerCallback> {

    public BannerPresenter(Context context, IBannerCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 获取轮播图列表
     */
    public void getBannerList() {
        AbstractRequestFunc<HomeRequest> requestFunc = new AbstractRequestFunc<HomeRequest>(context, new IRequestListener<List<BannerListVO>>() {
            @Override
            public void onRequestSuccess(List<BannerListVO> data) {
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
            public Observable getObservable(HomeRequest iRequestServer) {
                return iRequestServer.getBannerList();
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
     * 分类列表回调
     */
    public interface IBannerCallback extends IViewCallback {


        void getDataSuccess(List<BannerListVO> data);

        void getDataFail();
    }
}
