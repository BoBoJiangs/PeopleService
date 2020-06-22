package com.yb.peopleservice.view.fragment.comment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.message.MessageListPresenter;
import com.yb.peopleservice.view.adapter.shop.MessageListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;
import com.yb.peopleservice.view.fragment.service.ServicePersonFragment;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务人员选择入驻店铺
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MessageListFragment extends BaseListFragment {
    public static final String RECEIVE_MSG = "1";//接收消息
    public static final String SEND_MSG = "2";//发送消息
    private MessageListPresenter presenter;
    private MessageListAdapter adapter;
    private String type;

    public static Fragment getInstanceFragment(String type) {
        MessageListFragment fragment = new MessageListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new MessageListAdapter();
    }


    @Override
    protected void initData() {
        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
    }

    @Override
    public void onPullRefresh() {
        super.onPullRefresh();
        presenter.refreshList(false);
    }

    @Override
    public void onLoadMoreRequest() {
        super.onLoadMoreRequest();
        presenter.loadMoreList();
    }


    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        type = getArguments().getString("type");
        ServiceListUIPresenter<ShopInfo> queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout, getContext());

        presenter = new MessageListPresenter(getContext(), queryListUI,type);
        presenter.refreshList(true);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        initQueryListUI();
        return presenter;
    }
}
