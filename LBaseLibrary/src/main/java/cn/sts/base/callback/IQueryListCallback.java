package cn.sts.base.callback;

import java.util.List;

import cn.sts.base.callback.IViewCallback;

/**
 * 列表操作统一接口
 * Created by weilin on 17/4/6.
 */

public interface IQueryListCallback<T> extends IViewCallback {
    /**
     * 刷新成功
     */
    void refreshListSuccess(List<T> list);

    /**
     * 加载更多成功
     */
    void loadMoreListSuccess(List<T> list);

    /**
     * 获取列表失败
     */
    void getListFailed(String error);

    /**
     * 没有更多数据
     */
    void listNoMoreData();

    /**
     * 取消
     */
    void requestListCancel();

}
