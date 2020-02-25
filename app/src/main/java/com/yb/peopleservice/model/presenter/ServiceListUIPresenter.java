package com.yb.peopleservice.model.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.utils.AppUtils;

import java.util.List;

import cn.sts.base.presenter.QueryListUIPresenter;

import static cn.sts.base.presenter.AbstractQueryListPresenter.ROWS;

/**
 * 项目名称:Flower
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/11 14:54
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceListUIPresenter<T> extends QueryListUIPresenter<T> {
    private BaseQuickAdapter baseQuickAdapter;
    View emptyView;
    protected View footerView;

    public ServiceListUIPresenter(BaseQuickAdapter baseQuickAdapter,
                                  SwipeRefreshLayout swipeRefreshLayout, Context context) {
        super(baseQuickAdapter, swipeRefreshLayout, ROWS);
        this.baseQuickAdapter = baseQuickAdapter;
        emptyView = View.inflate(context, R.layout.view_nodata, null);
        footerView = View.inflate(context, R.layout.view_no_more_date, null);
    }

    public ServiceListUIPresenter(BaseQuickAdapter baseQuickAdapter,
                                  SwipeRefreshLayout swipeRefreshLayout, int pageRows, Context context) {
        super(baseQuickAdapter, swipeRefreshLayout, pageRows);
        this.baseQuickAdapter = baseQuickAdapter;
        emptyView = View.inflate(context, R.layout.view_nodata, null);
    }

    public void setEmptyText(String text) {
        if (emptyView != null) {
            TextView textView = emptyView.findViewById(R.id.textView);
            textView.setText(text);
        }

    }

    public void setEmptyView(Context context, int drawableId, String text) {
        emptyView = AppUtils.getEmptyView(context, drawableId, text);
    }

    @Override
    public void refreshListSuccess(List<T> list) {
        super.refreshListSuccess(list);
        if (list == null) {
            baseQuickAdapter.setEmptyView(emptyView);
            return;
        }
        if (list.size() < ROWS) {
            if (baseQuickAdapter.getData().isEmpty()) {
                baseQuickAdapter.setEmptyView(emptyView);
            } else {
                baseQuickAdapter.setFooterView(footerView);
            }

        } else {
            baseQuickAdapter.removeAllFooterView();
        }
    }

    @Override
    public void listNoMoreData() {
        super.listNoMoreData();


    }
}
