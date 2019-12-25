package com.yb.peopleservice.model.presenter.user.address;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.classify.HomeRequest;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 管理收货地址
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/12  14:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class AddressManagerPresenter extends AbstractPresenter<AddressManagerPresenter.IManagerCallBack> implements
        DeleteAddressPresenter.IDeleteCallBack {

    private DeleteAddressPresenter deleteAddressPresenter;

    public AddressManagerPresenter(Context context, IManagerCallBack viewCallBack) {
        super(context, viewCallBack);
        deleteAddressPresenter = new DeleteAddressPresenter(context, this);
    }


    @Override
    public void unbind() {
        super.unbind();
        deleteAddressPresenter.unbind();
    }


    /**
     * 新增/修改收货地址
     *
     * @param addressVO
     */
    public void addAddress(AddressListVO addressVO) {
        AbstractRequestFunc<HomeRequest> requestFunc = new AbstractRequestFunc<HomeRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                ToastUtils.showLong(error);
                try {
                    getViewCallBack().onFail(error);
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
                if (!TextUtils.isEmpty(addressVO.getId())) {
                    return iRequestServer.updateAddress(addressVO);
                } else {
                    return iRequestServer.addAddress(addressVO);
                }

            }

            @Override
            public Class<HomeRequest> getRequestInterfaceClass() {
                return HomeRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 删除收货地址
     *
     * @param addressVO
     */
    public void deleteAddress(AddressListVO addressVO) {
        deleteAddressPresenter.deleteAddress(addressVO);
    }

    @Override
    public void deleteSuccess() {
        try {
            getViewCallBack().deleteSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFail(String error) {
        try {
            getViewCallBack().deleteFail(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IManagerCallBack extends IViewCallback {
        /**
         * 删除成功
         */
        void deleteSuccess();

        /**
         * 删除失败
         */
        void deleteFail(String error);

        /**
         * 成功
         */
        void onSuccess();
        /**
         * 失败
         */
        void onFail(String error);

    }

}
