package cn.sts.base.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment adapter
 * Created by weilin on 17/8/26.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    /**
     * tabLayout的标题内容
     */
    private String[] titles;

    public TabFragmentAdapter(List<Fragment> fragmentList, String[] titles, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
