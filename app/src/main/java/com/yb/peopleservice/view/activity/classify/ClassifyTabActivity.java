package com.yb.peopleservice.view.activity.classify;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.base.BaseViewPagerFragment;
import com.yb.peopleservice.view.fragment.user.classify.ServiceListFragment;
import com.yb.peopleservice.view.fragment.user.order.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

public class ClassifyTabActivity extends BaseViewPagerActivity {
    @BindView(R.id.commonTabLayout)
    SlidingTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    //    private List<Fragment> fragmentList = new ArrayList<>();
    private ClassifyListBean bean;
    private List<Fragment> fragmentList;

    @Override
    public int contentViewResID() {
        return R.layout.activity_class_tab;
    }

    @Override
    protected View getTabLayout() {
        return commonTabLayout;
    }

    @Override
    protected String[] getTabTitles() {
        bean = getIntent().getParcelableExtra(ClassifyListBean.class.getName());
        fragmentList = new ArrayList<>();
        List<ClassifyListBean> childList = bean.getChildList();
        mTitles = new String[childList.size()];
        for (int i=0;i<childList.size();i++) {
            ClassifyListBean classifyListBean = childList.get(i);
            mTitles[i] = classifyListBean.getName();
            fragmentList.add(ServiceListFragment.getInstanceFragment(classifyListBean));
        }
        if (childList.size()>3){
//            commonTabLayout.setTabSpaceEqual(false);
            commonTabLayout.setIndicatorWidthEqualTitle(true);
        }else{
            commonTabLayout.setTabSpaceEqual(true);

        }
        return mTitles;
    }

    @Override
    protected List<Fragment> getFragmentList() {

        return fragmentList;
    }

    @Override
    protected void initData() {
        viewPager.setScroll(true);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return bean.getName();
    }
}
