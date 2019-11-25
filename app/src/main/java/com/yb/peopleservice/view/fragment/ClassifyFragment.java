package com.yb.peopleservice.view.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.ClassifyListBean;
import com.yb.peopleservice.view.adapter.ClassifyAdapter;
import com.yb.peopleservice.view.adapter.ClassifyChildAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;

public class ClassifyFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    List<ClassifyListBean> listData = new ArrayList<>();
    private ClassifyAdapter adapter;
    private ClassifyChildAdapter childAdapter;

    public static Fragment getInstanceFragment() {
        ClassifyFragment fragment = new ClassifyFragment();
        return fragment;
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initView() {
        adapter = new ClassifyAdapter();
        childAdapter = new ClassifyChildAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(childAdapter);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 5; i++) {
            ClassifyListBean bean = new ClassifyListBean();
            List<ClassifyListBean> childList = new ArrayList<>();
            for (int j = 0; j < new Random().nextInt(9) + 1; j++) {
                ClassifyListBean childBean = new ClassifyListBean();
                childList.add(childBean);
            }
            bean.setName(i + "");
            bean.setChildList(childList);
            listData.add(bean);
        }
        adapter.setNewData(listData);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter a, View view, int position) {
        ClassifyListBean bean = adapter.getItem(position);
        adapter.setName(bean.getName());
        childAdapter.setNewData(bean.getChildList());
    }
}
