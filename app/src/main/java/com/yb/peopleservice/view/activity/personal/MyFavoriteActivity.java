package com.yb.peopleservice.view.activity.personal;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.user.details.EvaluateFragment;
import com.yb.peopleservice.view.fragment.user.details.ServiceContentFragment;
import com.yb.peopleservice.view.fragment.user.details.ServiceFragment;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteShopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/2 14:24
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MyFavoriteActivity extends BaseViewPagerActivity {

    private String[] mTitles = {"服务", "店铺"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    private Fragment fragment1;
    private Fragment fragment2;

    @Override
    protected View getTabLayout() {
        return commonTabLayout;
    }

    @Override
    protected String[] getTabTitles() {
        return mTitles;
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_favorite;
    }

    @Override
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragment1 = FavoriteServiceFragment.getInstanceFragment(FavoriteServiceFragment.SERVICE_TYPE);
        fragment2 = FavoriteShopFragment.getInstanceFragment(FavoriteServiceFragment.SHOP_TYPE);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        return fragmentList;
    }

    @Override
    public String getTitleName() {
        return "我的收藏";
    }

    @Override
    protected void initData() {
        viewPager.setScroll(true);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
