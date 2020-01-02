package com.yb.peopleservice.view.fragment.user.favorite;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 类描述: 收藏的店铺
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/2  14:55
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FavoriteShopFragment extends BaseListFragment {

    private OrderListAdapter adapter;

    public static Fragment getInstanceFragment() {
        FavoriteShopFragment fragment = new FavoriteShopFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new OrderListAdapter(getActivity());
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
