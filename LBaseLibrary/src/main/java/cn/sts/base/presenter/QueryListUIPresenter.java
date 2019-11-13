package cn.sts.base.presenter;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.callback.IQueryListCallback;

/**
 * 列表，下拉或者加载更多的UI统一操作
 * Created by weilin on 17/4/4.
 */

public class QueryListUIPresenter<T> implements IQueryListCallback<T> {

    private BaseQuickAdapter baseQuickAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * 一页的行数
     */
    private int pageRows;

    public QueryListUIPresenter(BaseQuickAdapter baseQuickAdapter, SwipeRefreshLayout swipeRefreshLayout, int pageRows) {
        this.baseQuickAdapter = baseQuickAdapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.pageRows = pageRows;
        baseQuickAdapter.setEnableLoadMore(false);//关闭加载更多
    }

    @SuppressWarnings("unchecked")
    @Override
    public void refreshListSuccess(List<T> list) {
        swipeRefreshLayout.setRefreshing(false);//停止刷新动画
        if (list == null) {
            list = new ArrayList<>();
        }
        baseQuickAdapter.setNewData(list);
        if (list.size() < pageRows) {
            baseQuickAdapter.setEnableLoadMore(false);//关闭加载更多
        } else {
            baseQuickAdapter.setEnableLoadMore(true);//打开加载更多
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadMoreListSuccess(List<T> list) {
        baseQuickAdapter.loadMoreComplete();//停止加载更多
        swipeRefreshLayout.setEnabled(true);//打开下拉刷新
        if (list != null && list.size() > 0) {
            baseQuickAdapter.addData(list);
        }

        if (list == null || list.size() < pageRows) {
            baseQuickAdapter.setEnableLoadMore(false);//关闭加载更多
        }
    }

    @Override
    public void getListFailed(String error) {
        swipeRefreshLayout.setRefreshing(false);//停止刷新动画
        swipeRefreshLayout.setEnabled(true);//打开下拉刷新
        baseQuickAdapter.loadMoreComplete();//停止加载更多
        baseQuickAdapter.setEnableLoadMore(false);//关闭加载更多
    }

    @Override
    public void listNoMoreData() {
        swipeRefreshLayout.setRefreshing(false);//停止刷新动画
        swipeRefreshLayout.setEnabled(true);//打开下拉刷新
        baseQuickAdapter.loadMoreComplete();//停止加载更多
        baseQuickAdapter.setEnableLoadMore(false);//关闭加载更多
    }

    @Override
    public void requestListCancel() {
        baseQuickAdapter.loadMoreComplete();//停止加载更多
        swipeRefreshLayout.setRefreshing(false);//停止刷新动画
    }
}
