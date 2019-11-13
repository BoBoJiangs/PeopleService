package cn.sts.base.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.model.listener.IRequestListener;

/**
 * 列表提供者公共方法
 * Created by weilin on 17/4/6.
 */

public abstract class AbstractQueryListPresenter<T> extends AbstractPresenter<IQueryListCallback<T>> {

    /**
     * 分页-每页条数
     */
    public static final Integer ROWS = 20;

    /**
     * 是否是下拉刷新
     */
    private boolean isRefresh = false;



    /**
     * 分页加载的页码
     */
    public int pageIndex;

    /**
     * @param context            上下文
     * @param IQueryListCallback 列表操作接口
     */
    public AbstractQueryListPresenter(Context context, IQueryListCallback<T> IQueryListCallback) {
        super(context, IQueryListCallback);
    }

    /**
     * 刷新列表
     *
     * @param isShowProgress 是否显示进度条
     */
    public synchronized void refreshList(boolean isShowProgress) {
        pageIndex = 1;
        isRefresh = true;
        getList(isShowProgress);
    }

    /**
     * 更多
     */
    public synchronized void loadMoreList() {
        isRefresh = false;
        getList(false);
    }

    /**
     * 获取列表
     */
    public abstract void getList(boolean isShowProgress);

    /**
     * 服务器请求回调
     */
    private IRequestListener requestListener = new IRequestListener<List<T>>() {

        @Override
        public void onRequestSuccess(List<T> data) {
            try {
                if (pageIndex == 1 || (data != null && data.size() > 0)) {
                    if (isRefresh) {
                        getViewCallBack().refreshListSuccess(data);
                        pageIndex++;//成功取到数据页数加1
                    } else if (pageIndex > 1) {
                        getViewCallBack().loadMoreListSuccess(data);
                        pageIndex++;//成功取到数据页数加1
                    }
                } else {
                    getViewCallBack().listNoMoreData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestFailure(String error) {
            try {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                getViewCallBack().getListFailed(error);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestCancel() {
            try {
                getViewCallBack().requestListCancel();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    /**
     * 服务器请求回调
     *
     * @return 获取回调
     */
    public IRequestListener getRequestListener() {
        return requestListener;
    }

}
