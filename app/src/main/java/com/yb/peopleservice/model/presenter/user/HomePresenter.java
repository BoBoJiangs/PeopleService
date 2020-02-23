package com.yb.peopleservice.model.presenter.user;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.HomeListBean;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.HomeRequest;

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
public class HomePresenter extends AbstractPresenter<HomePresenter.IHomeCallback> {

    public HomePresenter(Context context, IHomeCallback viewCallBack) {
        super(context, viewCallBack);

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 获取首页热门服务分类
     */
    public void getHotList() {
        BaseRequestFunc<HomeRequest> requestFunc = new BaseRequestFunc<HomeRequest>(context, new IRequestListener<List<ClassifyListBean>>() {
            @Override
            public void onRequestSuccess(List<ClassifyListBean> data) {
                try {
                    getViewCallBack().getHotSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
//                try {
//                    getViewCallBack().getDataFail();
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
            public Observable getObservable(HomeRequest iRequestServer) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("parentId", parentId);
                return iRequestServer.getHotList();
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
     * 获取首页热门服务
     */
    public void getHotService() {
        BaseRequestFunc<HomeRequest> requestFunc = new BaseRequestFunc<HomeRequest>(context, new IRequestListener<List<HomeListBean>>() {
            @Override
            public void onRequestSuccess(List<HomeListBean> data) {
                try {
                    getViewCallBack().getHotShopSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
//                try {
//                    getViewCallBack().getDataFail();
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
            public Observable getObservable(HomeRequest iRequestServer) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("parentId", parentId);
                return iRequestServer.getHotService();
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
    public interface IHomeCallback extends IViewCallback {

        void getHotSuccess(List<ClassifyListBean> data);

        /**
         * 获取热销服务
         * @param data
         */
        void getHotShopSuccess(List<HomeListBean> data);

    }
}
