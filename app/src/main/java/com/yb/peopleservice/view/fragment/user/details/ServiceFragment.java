package com.yb.peopleservice.view.fragment.user.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.app.MyApplication;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.presenter.user.service.CollectPresenter;
import com.yb.peopleservice.model.presenter.user.service.ServicePresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.AMapUtil;
import com.yb.peopleservice.utils.GlideImageLoader;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.services.ServiceDetailsActivity;
import com.yb.peopleservice.view.activity.shop.SearchMapActivity;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;
import com.yb.peopleservice.view.fragment.user.order.CouponDialogFragment;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.NumberUtil;
import cn.sts.base.view.fragment.BaseFragment;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述: 服务内容
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/2 16:56
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceFragment extends BaseFragment implements
        CollectPresenter.ICollectCallback, ServicePresenter.IServiceCallback, NestedScrollView.OnScrollChangeListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.groupLL)
    LinearLayout groupLL;

    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.collectIV)
    ImageView collectIV;
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.soldTV)
    TextView soldTV;
    @BindView(R.id.activityUV)
    UtilityView activityUV;
    @BindView(R.id.topLL)
    LinearLayout topLL;
    @BindView(R.id.contentRv)
    RecyclerView contentRv;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    protected ServiceListBean serviceInfo;
    private CollectPresenter collectPresenter;
    private ServicePresenter presenter;
    private boolean isFavorite;//是否已收藏
    List<CouponBean> couponList;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    private int topHeight;
    ServiceDetailsActivity activity;

    public static Fragment getInstanceFragment(ServiceListBean serviceInfo) {
        ServiceFragment fragment = new ServiceFragment();
        if (serviceInfo != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ServiceListBean.class.getName(), serviceInfo);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_service;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        groupLL.setVisibility(View.GONE);
        scrollView.setOnScrollChangeListener(this);
        activity = (ServiceDetailsActivity) getActivity();
    }

    private void initContentView() {
        contentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_image) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.imageView);
                ImageLoaderUtil.loadPiblicImage(mContext, item, imageView);
            }
        };
        contentRv.setAdapter(adapter);
        List<String> images = serviceInfo.getContentImgs();
        adapter.setNewData(images);
        View view = inflater.inflate(R.layout.view_service_content, null);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(serviceInfo.getContentText());
        adapter.setFooterView(view);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CouponBean bean) {
        if (bean != null) {
            serviceInfo.setCoupons(bean);
            if (bean.getType() == 1) {
                activityUV.setContentText("﹣" + bean.getMoney() + "元");
            } else {
                activityUV.setContentText(bean.getDiscount() + "折");
            }
        }

    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            serviceInfo = getArguments().getParcelable(ServiceListBean.class.getName());
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            banner.setBannerAnimation(Transformer.Accordion);
            //设置轮播时间
            banner.setDelayTime(3000);
            List<String> bannerImags = new ArrayList<>();
            List<String> imags = serviceInfo.getMainImg();
            for (String imag : imags) {
                imag = BaseRequestServer.getFileUrl(true) + imag;
                bannerImags.add(imag);
            }
            //banner设置方法全部调用完毕时最后调用
            banner.update(bannerImags);
            nameTV.setText(serviceInfo.getName());
            String priceUnit = serviceInfo.getPriceUnit();
            if (TextUtils.isEmpty(priceUnit)) {
                priceUnit = "元";
            } else {
                priceUnit = "元/" + priceUnit;
            }
            priceTV.setText(serviceInfo.getPrice() + priceUnit);
            soldTV.setText("已售：" + serviceInfo.getTotalSold());

            initContentView();

        }
        collectPresenter = new CollectPresenter(getContext(), this);
        collectPresenter.getFavorite(serviceInfo.getId());
        presenter = new ServicePresenter(getContext(), this);
        topHeight = SizeUtils.getMeasuredHeight(topLL);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return collectPresenter;
    }

    @OnClick({R.id.collectIV, R.id.activityUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collectIV:
                if (isFavorite) {
                    collectPresenter.addFavorite(serviceInfo.getId(), FavoriteServiceFragment.CANCEL_TYPE);
                } else {
                    collectPresenter.addFavorite(serviceInfo.getId(), FavoriteServiceFragment.SERVICE_TYPE);
                }
                break;
            case R.id.activityUV:
                if (couponList != null) {
                    if (couponList.isEmpty()) {
                        ToastUtils.showLong("暂无优惠券可用");
                        return;
                    }
                    showCouponView(couponList);
                } else {
                    presenter.getCouponList(serviceInfo.getId());
                }
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    @Override
    public void collectSuccess(FavoriteBean favoriteBean) {
        collectIV.setBackgroundResource(R.mipmap.icon_favorite1);
        isFavorite = true;
    }

    @Override
    public void cancelSuccess() {
        collectIV.setBackgroundResource(R.mipmap.icon_favorite);
        isFavorite = false;
    }

    @Override
    public void isCollect(FavoriteBean data) {
        collectIV.setVisibility(View.VISIBLE);
        if (data == null) {
            isFavorite = false;
            collectIV.setBackgroundResource(R.mipmap.icon_favorite);
        } else {
            isFavorite = true;
            collectIV.setBackgroundResource(R.mipmap.icon_favorite1);
        }
    }


    @Override
    public void groupSuccess(List<GroupBean> favoriteBean) {

    }

    @Override
    public void couponSuccess(List<CouponBean> data) {
        if (data != null && !data.isEmpty()) {
            showCouponView(data);
        } else {
            ToastUtils.showLong("暂无优惠券可用");
            couponList = new ArrayList<>();
        }

    }

    private void showCouponView(List<CouponBean> data) {
        CouponDialogFragment couponDialogFragment = new CouponDialogFragment(data);
        couponDialogFragment.show(getFragmentManager(), CouponDialogFragment.class.getSimpleName());
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY > topHeight) {
            if (activity!=null){
                activity.commonTabLayout.setCurrentTab(1);
            }
        }else{
            activity.commonTabLayout.setCurrentTab(0);
        }
    }
}
