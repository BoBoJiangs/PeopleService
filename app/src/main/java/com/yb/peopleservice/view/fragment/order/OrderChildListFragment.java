package com.yb.peopleservice.view.fragment.order;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseViewPagerFragment;
import com.yb.peopleservice.view.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

public class OrderChildListFragment extends BaseListFragment {

    private BaseQuickAdapter adapter;

    public static Fragment getInstanceFragment() {
        OrderChildListFragment fragment = new OrderChildListFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new BaseQuickAdapter(R.layout.item_order_layout) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, Object item) {

            }
        };
    }

    @Override
    protected void initData() {
        List<String> listData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listData.add("");
        }
        adapter.setNewData(listData);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
