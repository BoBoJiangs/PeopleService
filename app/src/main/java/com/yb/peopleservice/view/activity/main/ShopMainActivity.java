package com.yb.peopleservice.view.activity.main;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.model.presenter.shop.RealTimeLocationPresenter;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.shop.ShopFragment;
import com.yb.peopleservice.view.fragment.shop.order.ShopOrderTabFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.AppDialog;
import cn.sts.base.view.widget.ScrollViewPager;
import jiguang.chat.activity.fragment.ConversationListFragment;

/**
 * 类描述:商家首页
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/18  17:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopMainActivity extends BaseViewPagerActivity implements OnTabSelectListener {

    private String[] mTitles = {"订单", "消息", "店铺"};
    private int[] mIconUnselectIds = {R.mipmap.tab_order_unselect, R.mipmap.tab_msg_unselect,R.mipmap.tab_map_unselect};
    private int[] mIconSelectIds = {R.mipmap.tab_order_select, R.mipmap.tab_msg_select,R.mipmap.tab_map_select};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    private ShopFragment shopFragment;
    private RealTimeLocationPresenter presenter;
    @Override
    public int contentViewResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initToolView() {
        super.initToolView();
//        rightLL.setVisibility(View.VISIBLE);
//        rightIV2.setVisibility(View.VISIBLE);
//        rightIV2.setImageResource(R.mipmap.icon_logout);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        presenter = new RealTimeLocationPresenter(this,null);
//        viewPager.setScroll(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AMapLocation location) {
        presenter.addGps(location);
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
        shopFragment = (ShopFragment) ShopFragment.getInstanceFragment();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ShopOrderTabFragment.getInstanceFragment());
        fragmentList.add(ConversationListFragment.getInstanceFragment());
        fragmentList.add(shopFragment);

        return fragmentList;
    }

    @Override
    public void onTabSelect(int position) {
        leftIV.setVisibility(View.GONE);
        viewPager.setCurrentItem(position,false);
        if (position == 0) {
            titleTV.setText("订单");
        } else if (position == 1){
            titleTV.setText("消息");
        }else{
            titleTV.setText("店铺");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shopFragment.onActivityResult(requestCode,resultCode,data);
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
