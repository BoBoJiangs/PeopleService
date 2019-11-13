package cn.sts.base.view.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import cn.sts.base.R;

/**
 * RecyclerView列表基础类
 *
 * @author weilin
 */
public abstract class BaseListActivity extends BaseToolbarActivity {

    public LinearLayout rootLL;

    public SwipeRefreshLayout swipeRefreshLayout;

    public RecyclerView recyclerView;

    public TextView noDataTV;

    public BaseQuickAdapter baseQuickAdapter;

    @Override
    public int contentViewResID() {
        return R.layout.activity_recycler_view;
    }

    /**
     * 初始化适配器
     */
    public abstract BaseQuickAdapter initAdapter();

    @Override
    public void initView() {

        rootLL = findViewById(R.id.rootLL);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        noDataTV = findViewById(R.id.noDataTV);

        baseQuickAdapter = initAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);

        if (isSetOnItemClickListener()) {
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    onClickItem(adapter, view, position);
                }
            });
        }
        if (swipeRefreshLayout != null) {
            //默认不能下拉刷新
            swipeRefreshLayout.setEnabled(false);
        }
    }

    public boolean isSetOnItemClickListener() {
        return true;
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
        if (swipeRefreshLayout != null) {
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
        if (swipeRefreshLayout != null) {
            //加载更多时禁用下拉刷新
            swipeRefreshLayout.setEnabled(false);
        }
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

    }


}
