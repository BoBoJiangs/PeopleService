package com.yb.peopleservice.model.presenter.user.order;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.eventbean.EventRecorderBean;
import com.yb.peopleservice.model.presenter.record.RecordPresenter;
import com.yb.peopleservice.model.presenter.track.AMapTrackPresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.user.OrderRequest;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;

import static com.yb.peopleservice.model.eventbean.EventRecorderBean.START;
import static com.yb.peopleservice.model.eventbean.EventRecorderBean.STOP;

/**
 * 项目名称:PeopleService
 * 类描述: 订单状态操作
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderStatePresenter extends AbstractPresenter<OrderStatePresenter.IOrderCallback> implements
        AMapTrackPresenter.TrackCallBack {

    private RecordPresenter presenter;
    private String userId;
    private AMapTrackPresenter trackPresenter;

    public OrderStatePresenter(Context context, IOrderCallback viewCallBack) {
        super(context, viewCallBack);
        presenter = new RecordPresenter(context, null);
        User user = ManagerFactory.getInstance().getUserManager().getUser();
        if (user != null) {
            userId = user.getUserId();
            trackPresenter = new AMapTrackPresenter((Activity) context, this::getTackId);
        }

    }

    @Override
    public void unbind() {
        super.unbind();
    }

    /**
     * 接受/拒绝 服务
     */
    public void acceptOrder(OrderBean orderBean, boolean isAccept, int orderStatus) {
        BaseRequestFunc<OrderRequest> requestFunc = new BaseRequestFunc<OrderRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    if (!StringUtils.isEmpty(userId)) {
                        if (OrderBean.ASSIGN == orderStatus && isAccept) {
                            //接受订单后开始上报轨迹
                            startTrack(orderBean);
                        }
                        if (OrderBean.WAITING == orderStatus) {
                            //通知录音服务开始录音(服务人员点击抵达按钮时开始录音)
                            EventBus.getDefault().post(new EventRecorderBean(userId, START, orderBean.getId()));
                        }
                        if (OrderBean.DOING == orderStatus) {
                            //通知录音服务结束录音(服务人员点击服务完成时结束录音)
                            EventBus.getDefault().post(new EventRecorderBean(userId, STOP, orderBean.getId()));
                            //开始上传音频文件
                            presenter.queryFileData();

                            //停止轨迹服务
                            trackPresenter.stopTrack();
                        }
                    }
                    getViewCallBack().acceptSuccess(data);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().acceptFail();
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
            public Observable getObservable(OrderRequest iRequestServer) {
                switch (orderStatus) {
                    case OrderBean.ASSIGN:
                        if (isAccept) {
                            return iRequestServer.acceptOrder(orderBean.getId());
                        } else {
                            return iRequestServer.refuseOrder(orderBean.getId());
                        }

                    case OrderBean.WAITING:
                        return iRequestServer.arriveOrder(orderBean.getId());
                    case OrderBean.ARRIVED://开始服务
                        return iRequestServer.startOrder(orderBean.getId());
                    case OrderBean.DOING://服务完成
                        return iRequestServer.endOrder(orderBean.getId());
                }
                return iRequestServer.arriveOrder(orderBean.getId());
            }

            @Override
            public Class<OrderRequest> getRequestInterfaceClass() {
                return OrderRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    public void startTrack(OrderBean orderBean) {
        if (orderBean.getCalculatedDistance() == 1) {
            //驾驶类服务创建轨迹
            trackPresenter.startTrack(orderBean.getId(), true);
        } else {
            //非驾驶类值上报散点轨迹
            trackPresenter.startTrack(orderBean.getId(), false);
        }

    }

    boolean isStart = true;

    public void startRecord(String orderId) {
        if (isStart) {
            isStart = false;
            //通知录音服务开始录音(服务人员点击抵达按钮时开始录音)
            EventBus.getDefault().post(new EventRecorderBean(userId, START, orderId));
        } else {
            isStart = true;
            //通知录音服务结束录音(服务人员点击服务完成时结束录音)
            EventBus.getDefault().post(new EventRecorderBean(userId, STOP, orderId));
            //开始上传音频文件
            presenter.queryFileData();
        }

    }

    /**
     * 处理退款申请
     *
     * @param orderId
     */
    public void refundOrder(String orderId, String refundStatus) {
        BaseRequestFunc<OrderRequest> requestFunc = new BaseRequestFunc<OrderRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    getViewCallBack().acceptSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().acceptFail();
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
            public Observable getObservable(OrderRequest iRequestServer) {
                switch (refundStatus) {
                    case OrderListAdapter.BUTTON_TEXT12:
                        return iRequestServer.confirmRefund(orderId);
                    case OrderListAdapter.BUTTON_TEXT13:
                        return iRequestServer.closeRefund(orderId);
                }
                return iRequestServer.confirmRefund(orderId);
            }

            @Override
            public Class<OrderRequest> getRequestInterfaceClass() {
                return OrderRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    @Override
    public void getTackId(String orderId, long trackId) {
        BaseRequestFunc<OrderRequest> requestFunc = new BaseRequestFunc<OrderRequest>(context, null) {
            @Override
            public Observable getObservable(OrderRequest iRequestServer) {
                return iRequestServer.addOrderTrackId(orderId, trackId + "");
            }

            @Override
            public Class<OrderRequest> getRequestInterfaceClass() {
                return OrderRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 分类列表回调
     */
    public interface IOrderCallback extends IViewCallback {


        void acceptSuccess(Object data);

        void acceptFail();
    }
}
