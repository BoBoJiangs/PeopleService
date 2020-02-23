package com.yb.peopleservice.model.presenter.user.address;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.HomeRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 关注 喜欢列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/12  14:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class DeleteAddressPresenter extends AbstractPresenter<DeleteAddressPresenter.IDeleteCallBack> {
    public DeleteAddressPresenter(Context context, IDeleteCallBack viewCallBack) {
        super(context, viewCallBack);
    }


    @Override
    public void unbind() {
        super.unbind();
    }


    /**
     * 删除收货地址
     *
     * @param addressVO
     */
    public void deleteAddress(AddressListVO addressVO) {
        BaseRequestFunc<HomeRequest> requestFunc = new BaseRequestFunc<HomeRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().deleteSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                ToastUtils.showLong(error);
                try {
                    getViewCallBack().deleteFail(error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(HomeRequest iRequestServer) {
                return iRequestServer.deleteAddress(addressVO.getId());
            }

            @Override
            public Class<HomeRequest> getRequestInterfaceClass() {
                return HomeRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    public interface IDeleteCallBack extends IViewCallback {
        /**
         * 删除成功
         */
        void deleteSuccess();
        /**
         * 删除失败
         */
        void deleteFail(String error);

    }

}
