package com.yb.peopleservice.view.fragment.user.details;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.adapter.EvaluateAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务评价
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/2 16:56
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EvaluateFragment extends BaseListFragment {
    private EvaluateAdapter adapter;

    public static Fragment getInstanceFragment() {
        EvaluateFragment fragment = new EvaluateFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new EvaluateAdapter();
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_evaluate;
    }

    @Override
    public void initView() {
        super.initView();
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
