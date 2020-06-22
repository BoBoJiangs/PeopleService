package com.yb.peopleservice.view.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.user.BannerListVO;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.HomeListBean;
import com.yb.peopleservice.model.bean.user.JsonBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.UserManager;
import com.yb.peopleservice.model.presenter.user.BannerPresenter;
import com.yb.peopleservice.model.presenter.user.HomePresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.GlideImageLoader;
import com.yb.peopleservice.view.activity.im.ChatListActivity;
import com.yb.peopleservice.view.activity.search.SearchActivity;
import com.yb.peopleservice.view.adapter.user.HomeListAdapter;
import com.yb.peopleservice.view.weight.ItemDragCallback;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.cache.Sp;
import cn.sts.base.view.fragment.BaseListFragment;

import static com.yb.peopleservice.model.bean.user.HomeListBean.CONTENT_TYPE;
import static com.yb.peopleservice.model.bean.user.HomeListBean.PAGE_TYPE;
import static com.yb.peopleservice.model.bean.user.HomeListBean.SPAN_SIZE_ONE;
import static com.yb.peopleservice.model.bean.user.HomeListBean.TITLE_TYPE;

/**
 * 类描述:首页
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/20  16:37
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class HomeFragment extends BaseListFragment implements BannerPresenter.IBannerCallback, HomePresenter.IHomeCallback {
    @BindView(R.id.locationName)
    TextView locationName;
    private HomeListAdapter adapter;
    private HeaderViewHolder headerViewHolder;
    List<HomeListBean> listData = new ArrayList<>();
    private BannerPresenter presenter;
    private HomePresenter homePresenter;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private UserInfoBean userInfoBean;
    private UserManager userManager;

    public static Fragment getInstanceFragment() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        adapter = new HomeListAdapter(listData, getContext());
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3);
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initData() {
        initHeaderView();
        userManager = ManagerFactory.getInstance().getUserManager();
        User user = userManager.getUser();
        if (user != null) {
            userInfoBean = userManager.getUser().getInfoBean();
            if (userInfoBean != null && !StringUtils.isEmpty(userInfoBean.getProvince())
                    && !StringUtils.isEmpty(userInfoBean.getCity())) {
                AppConstant.PROVINCE = userInfoBean.getProvince();
                AppConstant.CITY = userInfoBean.getCity();
            }
        }
        locationName.setText(AppConstant.CITY);
        listData.add(new HomeListBean(PAGE_TYPE, SPAN_SIZE_ONE));
        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        adapter.setSpanSizeLookup((gridLayoutManager, position) ->
                listData.get(position).getSpanSize());
        adapter.setNewData(listData);
        DraggableController mDraggableController = adapter.getDraggableController();
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };


        ItemDragCallback mItemDragAndSwipeCallback = new ItemDragCallback(mDraggableController);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        mDraggableController.enableDragItem(mItemTouchHelper);
        mDraggableController.setOnItemDragListener(listener);

        presenter = new BannerPresenter(getContext(), this);
        homePresenter = new HomePresenter(getContext(), this);
        presenter.getBannerList();
        homePresenter.getHotList();
        homePresenter.getHotService();
    }

    @OnClick({R.id.searchLL, R.id.rightIV2, R.id.locationName})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchLL:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.rightIV2:
                startActivity(new Intent(getContext(), ChatListActivity.class));
                break;
            case R.id.locationName:
                initJsonData();
                break;
        }

    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = AppUtils.getJson(getContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = GsonUtils.fromJson(JsonData, new TypeToken<List<JsonBean>>() {
        }.getType());//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

        }
        showPickerView();
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String province = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String city = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                AppConstant.PROVINCE = province;
                AppConstant.CITY = city;
                if (userInfoBean != null) {
                    userInfoBean.setProvince(province);
                    userInfoBean.setCity(city);
                    ManagerFactory.getInstance().getUserInfoManager().update(userInfoBean);
                }
                locationName.setText(city);
                homePresenter.getHotList();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    @Override
    protected HomePresenter createPresenter() {

        return homePresenter;
    }

    /**
     * 设置轮播图片集合
     *
     * @param bannerListVO
     */
    public void setBannerList(List<BannerListVO> bannerListVO) {
        //设置图片集合
        List<String> images = new ArrayList<>();
        for (BannerListVO banner : bannerListVO) {
            images.add(BaseRequestServer.getFileUrl(true) + banner.getMobileImg());
        }


        //设置轮播时间
        headerViewHolder.banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        headerViewHolder.banner.update(images);
    }

    /**
     * 初始化头部轮播控件
     */
    private void initHeaderView() {
        //设置图片加载器
        View headerView = View.inflate(getActivity(), R.layout.e_banner, null);
        headerViewHolder = new HeaderViewHolder(headerView, getActivity());
        adapter.addHeaderView(headerView);
    }

    @Override
    public void getDataSuccess(List<BannerListVO> data) {
        setBannerList(data);
    }

    @Override
    public void getDataFail() {

    }

    @Override
    public void getHotSuccess(List<ClassifyListBean> data) {
        HomeListBean homeListBean = adapter.getItem(0);
        if (data != null && homeListBean != null) {
            homeListBean.setClassList(data.size() > 20 ? data.subList(0, 20) : data);
            adapter.setData(0, homeListBean);
        }

    }

    @Override
    public void getHotShopSuccess(List<HomeListBean> data) {
        for (HomeListBean bean : data) {
            bean.setItemType(TITLE_TYPE);
            bean.setSpanSize(SPAN_SIZE_ONE);
        }
        adapter.addData(data);
    }


    static class HeaderViewHolder {
        private Context context;
        @BindView(R.id.banner)
        Banner banner;

        HeaderViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
            initBanner();
        }


        public void initBanner() {
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            banner.setBannerAnimation(Transformer.Accordion);
        }

//        @Override
//        public void onPageSizeChanged(int pageSize) {
//            indicator.initIndicator(pageSize);
//        }
//
//        @Override
//        public void onPageSelect(int pageIndex) {
//            indicator.setSelectedPage(pageIndex);
//        }
    }
}
