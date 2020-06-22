package com.yb.peopleservice.model.presenter.message;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.bean.shop.MessageBean;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.shop.ShopRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import io.reactivex.Observable;

/**
 * 类描述: 消息列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19  17:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MessageListPresenter extends AbstractQueryListPresenter<ShopInfo> {

    private User user;
    private String type;

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public MessageListPresenter(Context context,
                                IQueryListCallback<ShopInfo> IQueryListCallback, String type) {
        super(context, IQueryListCallback);
        user = ManagerFactory.getInstance().getUserManager().getUser();
        this.type = type;
    }


    @Override
    public void getList(boolean isShowProgress) {
        BaseRequestFunc<ShopRequest> requestFunc = new BaseRequestFunc<ShopRequest>(context, getRequestListener()) {
            @Override
            public Observable getObservable(ShopRequest iRequestServer) {
                Map<String, String> map = new HashMap<>();
                map.put("current", pageIndex + "");
                map.put("type", type);
                return iRequestServer.getUserMessage(map);
            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(isShowProgress);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    public void addMessage(MessageBean bean) {
        BaseRequestFunc<ShopRequest> requestFunc = new BaseRequestFunc<ShopRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    EventBus.getDefault().post(bean);
                    ToastUtils.showLong("发布成功");

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            public Observable getObservable(ShopRequest iRequestServer) {
                return iRequestServer.addMessage(bean);

            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }


}
