package cn.sts.base.view.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import cn.sts.base.R;

/**
 * 基础listView
 * Created by weilin on 17/3/24.
 */

public abstract class BaseListFragment extends BaseFragment {

    public SwipeRefreshLayout swipeRefreshLayout;

    public RecyclerView recyclerView;

    public TextView noDataTV;

    private BaseQuickAdapter baseQuickAdapter;


    /**
     * 初始化适配器
     */
    public abstract BaseQuickAdapter initAdapter();

    @Override
    public int viewResID() {
        return R.layout.recycler_view;
    }

    @Override
    public void initView() {

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        noDataTV = view.findViewById(R.id.noDataTV);

        baseQuickAdapter = initAdapter();

        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onClickItem(adapter, view, position);
            }
        });
        swipeRefreshLayout.setEnabled(false);//默认不能下拉刷新
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    /**
     * 选择一项-子类实现
     */
    public void onClickItem(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * 设置下拉刷新
     */
    public void setOnRefreshListener() {
        //打开下拉刷新
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onPullRefresh();
            }
        });
        //设置下拉刷新旋转颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(30, 144, 255));
    }

    /**
     * 下拉刷新-子类实现
     */
    public void onPullRefresh() {
        //下拉刷新时禁用加载更多
        baseQuickAdapter.setEnableLoadMore(false);
    }

    /**
     * 设置加载更多
     */
    public void setLoadMoreListener() {
        baseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMoreRequest();
            }
        }, recyclerView);
    }

    /**
     * 加载更多-子类实现
     */
    public void onLoadMoreRequest() {
        //加载更多时禁用下拉刷新
        swipeRefreshLayout.setEnabled(false);
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

}
