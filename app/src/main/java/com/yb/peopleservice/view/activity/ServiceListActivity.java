package com.yb.peopleservice.view.activity;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.sts.base.presenter.AbstractPresenter;

public class ServiceListActivity extends BaseListActivity {
    private BaseQuickAdapter adapter;

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new BaseQuickAdapter(R.layout.adapter_service) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, Object item) {

            }
        };
    }

    @Override
    public String getTitleName() {
        return "";
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

    @Override
    public void onClickItem(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(this, ServiceDetailsActivity.class));
    }
}
