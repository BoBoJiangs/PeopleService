package com.yb.peopleservice.view.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.sts.base.view.fragment.BaseFragment;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 项目名称:Exam
 * 类描述: fragment懒加载
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/8/14 9:07
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public abstract class LazyLoadFragment extends BaseFragment {
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    /**
     * 懒加载
     */
    public abstract void fetchData();

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 判断懒加载条件
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
        }
    }
}
