package com.yb.peopleservice.view.activity.main;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.bugly.beta.Beta;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.presenter.VersionPresenter;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.push.TagAliasOperatorHelper;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.view.activity.login.LoginActivity;
import com.yb.peopleservice.view.activity.personal.MyOrderActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.user.classify.ClassifyFragment;
import com.yb.peopleservice.view.fragment.user.HomeFragment;
import com.yb.peopleservice.view.fragment.user.LifeRadarMapFragment;
import com.yb.peopleservice.view.fragment.user.order.OrderTabFragment;
import com.yb.peopleservice.view.fragment.user.PersonalFragment;
import com.yb.peopleservice.view.fragment.user.order.QuickOrderFragment;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

import static com.yb.peopleservice.push.TagAliasOperatorHelper.ACTION_SET;

public class MainActivity extends BaseViewPagerActivity {

    private String[] mTitles = {"首页", "分类", "生活雷达", "快速下单", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_class_unselect,
            R.mipmap.tab_map_unselect, R.mipmap.tab_order_unselect, R.mipmap.tab_center_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_class_select,
            R.mipmap.tab_map_select, R.mipmap.tab_order_select, R.mipmap.tab_center_select};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    //    @BindView(R.id.viewPager)
//    ScrollViewPager viewPager;
//    @BindView(R.id.frameLayout)
//    FrameLayout frameLayout;
    private User user;

    @Override
    public int contentViewResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolView() {
        super.initToolView();
        toolbar.setVisibility(View.GONE);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.base_text_color).init();
    }

    @Override
    public void initView() {
        super.initView();
        viewPager.setOffscreenPageLimit(5);
        new VersionPresenter(this).checkVersion();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean isPaySuccess = intent.getBooleanExtra("isPaySuccess",false);
        if (isPaySuccess){
            commonTabLayout.setCurrentTab(0);
            onTabSelect(0);
            startActivity(new Intent(this, MyOrderActivity.class));
        }else{
            commonTabLayout.setCurrentTab(0);
            viewPager.setCurrentItem(0,false);
        }
    }

    @Override
    protected void initData() {
//        commonTabLayout.setTabData(getTabEntityList(), this, R.id.frameLayout,
//                getFragmentList());
//        commonTabLayout.setOnTabSelectListener(this);
        user = ManagerFactory.getInstance().getUserManager().getUser();
        if (user != null && user.getInfoBean() != null) {
            new ChatPresenter().getUserInfo(AppUtils.
                    formatID(user.getInfoBean().getId()),user.getInfoBean().getNickname());
        }
//        Beta.checkUpgrade(false,false);
    }


    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return "首页";
    }

    protected ArrayList<CustomTabEntity> getTabEntityList() {
        ArrayList<CustomTabEntity> mTabEntityList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntityList.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return mTabEntityList;
    }

    @Override
    protected View getTabLayout() {
        return commonTabLayout;
    }

    @Override
    protected String[] getTabTitles() {
        return mTitles;
    }

    protected ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.getInstanceFragment());
        fragmentList.add(ClassifyFragment.getInstanceFragment());
        fragmentList.add(LifeRadarMapFragment.getInstanceFragment());
        fragmentList.add(QuickOrderFragment.getInstanceFragment());
        fragmentList.add(PersonalFragment.getInstanceFragment());

        return fragmentList;
    }

    @Override
    public void onTabSelect(int position) {
        viewPager.setCurrentItem(position, false);
        if (position == 0 || position == 4) {
            ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarDarkFont(false, 0.2f)
                    .statusBarColor(R.color.base_text_color).init();
        } else {
            ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarDarkFont(true, 0.2f)
                    .statusBarColor(R.color.white).init();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
