package com.yb.peopleservice.view.base;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yb.peopleservice.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.view.adapter.TabFragmentAdapter;
import cn.sts.base.view.fragment.BaseFragment;

/**
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/8/30  13:59
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public abstract class BaseViewPagerFragment extends LazyLoadFragment implements OnTabSelectListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    public View tabLayout;

    protected String[] mTitles;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    /**
     * 获取TAB控件
     */
    protected abstract View getTabLayout();

    /**
     * 获取标签的标题
     */
    protected abstract String[] getTabTitles();

    protected List<Fragment> fragmentList;


    /**
     * 获取对应的Fragment
     */
    protected abstract List<Fragment> getFragmentList();

    @Override
    protected void initView() {
        toolbar.setVisibility(View.GONE);
        tabLayout = getTabLayout();

        mTitles = getTabTitles();
        fragmentList = getFragmentList();

        if (tabLayout != null && getActivity() != null) {
            if (tabLayout instanceof CommonTabLayout) {

                CommonTabLayout commonTabLayout = (CommonTabLayout) tabLayout;
                commonTabLayout.setTabData(getTabEntityList());
                commonTabLayout.setOnTabSelectListener(this);

            } else if (tabLayout instanceof SegmentTabLayout) {

                SegmentTabLayout segmentTabLayout = (SegmentTabLayout) tabLayout;
                segmentTabLayout.setTabData(mTitles);
                segmentTabLayout.setOnTabSelectListener(this);

            } else if (tabLayout instanceof SlidingTabLayout) {

                SlidingTabLayout slidingTabLayout = (SlidingTabLayout) tabLayout;
                slidingTabLayout.setOnTabSelectListener(this);
                slidingTabLayout.setViewPager(viewPager, mTitles, getActivity(), (ArrayList<Fragment>) fragmentList);

            }
        }

        viewPager.setAdapter(new TabFragmentAdapter(fragmentList, mTitles, getChildFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }


    /**
     * 组装标签的实体，包括名称，图片等
     */
    protected ArrayList<CustomTabEntity> getTabEntityList() {
        ArrayList<CustomTabEntity> mTabEntityList = new ArrayList<>();
        for (String title : mTitles) {
            mTabEntityList.add(new TabEntity(title));
        }
        return mTabEntityList;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (tabLayout != null) {
            if (tabLayout instanceof CommonTabLayout) {
                ((CommonTabLayout) tabLayout).setCurrentTab(position);
            } else if (tabLayout instanceof SegmentTabLayout) {
                ((SegmentTabLayout) tabLayout).setCurrentTab(position);
            } else if (tabLayout instanceof SlidingTabLayout) {
                ((SlidingTabLayout) tabLayout).setCurrentTab(position);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }


    @Override
    public int viewResID() {
        return R.layout.activity_view_pager;
    }
}
