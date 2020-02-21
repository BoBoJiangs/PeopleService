package cn.sts.base.view.activity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.R;
import cn.sts.base.view.adapter.TabFragmentAdapter;

/**
 * 基础ViewPager
 * Created by weilin on 2018/2/28.
 */

public abstract class BaseViewPagerActivity extends BaseToolbarActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener {

    public ViewPager viewPager;
    public View tabLayout;

    protected String[] mTitles;

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
    public void initView() {

        tabLayout = getTabLayout();
        viewPager = findViewById(R.id.viewPager);

        mTitles = getTabTitles();
        fragmentList = getFragmentList();

        if (tabLayout != null) {
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
                slidingTabLayout.setViewPager(viewPager, mTitles, this, (ArrayList<Fragment>) fragmentList);

            }
        }

        viewPager.setAdapter(new TabFragmentAdapter(fragmentList, mTitles, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

    /**
     * 组装标签的实体，包括名称，图片等
     */
    protected ArrayList<CustomTabEntity> getTabEntityList() {
        return new ArrayList<>();
    }


    @Override
    public int contentViewResID() {
        return R.layout.activity_view_pager;
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

        showViewPagerTitle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        viewPager.setCurrentItem(position);
        showViewPagerTitle(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    /**
     * 显示主标题
     *
     * @param position ViewPager序号
     */
    public void showViewPagerTitle(int position) {
        if (titleTV != null) {
            titleTV.setText(mTitles[position]);
        }
    }
}
