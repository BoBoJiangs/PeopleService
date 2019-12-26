package com.yb.peopleservice.view.fragment.user;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.presenter.user.ClassifyPresenter;
import com.yb.peopleservice.view.activity.ServiceListActivity;
import com.yb.peopleservice.view.adapter.user.classify.ClassifyAdapter;
import com.yb.peopleservice.view.adapter.user.classify.ClassifyChildAdapter;

import java.util.List;

import butterknife.BindView;
import cn.sts.base.view.fragment.BaseFragment;

public class ClassifyFragment extends BaseFragment<ClassifyPresenter> implements
        BaseQuickAdapter.OnItemClickListener, ClassifyPresenter.IClassCallback {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    private ClassifyAdapter adapter;
    private ClassifyChildAdapter childAdapter;
    private ClassifyPresenter presenter;
    private boolean isFirst = true;//首次进入请求一级和二级分类
    private int clickIndex = 0;//记录点击的position

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
        //获取一级分类
        presenter.getCategoryInfo("0");
        childAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifyListBean classifyListBean = childAdapter.getItem(position);
                if (classifyListBean != null) {
                    startActivity(new Intent(getContext(), ServiceListActivity.class)
                            .putExtra(ClassifyListBean.class.getName(),classifyListBean));
                }

            }
        });
//        for (int i = 0; i < 5; i++) {
//            ClassifyListBean bean = new ClassifyListBean();
//            List<ClassifyListBean> childList = new ArrayList<>();
//            for (int j = 0; j < new Random().nextInt(9) + 1; j++) {
//                ClassifyListBean childBean = new ClassifyListBean();
//                childList.add(childBean);
//            }
//            bean.setName(i + "");
//            bean.setChildList(childList);
//            listData.add(bean);
//        }
//        adapter.setNewData(listData);
    }

    @Override
    protected ClassifyPresenter createPresenter() {
        return presenter = new ClassifyPresenter(getContext(), this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter a, View view, int position) {
        clickIndex = position;
        ClassifyListBean bean = adapter.getItem(position);
        if (bean != null) {
            adapter.setName(bean.getName());
            if (bean.getChildList().isEmpty()) {
                presenter.getCategoryInfo(bean.getParentId());
            } else {
                childAdapter.setNewData(bean.getChildList());
            }
        }

//        childAdapter.setNewData(bean.getChildList());
    }

    @Override
    public void getDataSuccess(List<ClassifyListBean> data) {
        if (!data.isEmpty()) {
            adapter.setName(data.get(0).getName());
            adapter.setNewData(data);
            childAdapter.setNewData(data.get(0).getChildList());
//            if (isFirst) {
//                isFirst = false;
//                adapter.setName(data.get(0).getName());
//                adapter.setNewData(data);
//            } else {
//                adapter.getData().get(clickIndex).setChildList(data);
//                childAdapter.setNewData(data);
//            }

        }

    }

    @Override
    public void getDataFail() {

    }
}
