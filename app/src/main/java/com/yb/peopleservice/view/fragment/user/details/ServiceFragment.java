package com.yb.peopleservice.view.fragment.user.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.app.MyApplication;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.CollectPresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.AMapUtil;
import com.yb.peopleservice.utils.GlideImageLoader;
import com.yb.peopleservice.view.activity.shop.SearchMapActivity;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.NumberUtil;
import cn.sts.base.view.fragment.BaseFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务内容
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/2 16:56
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceFragment extends BaseFragment implements CollectPresenter.ICollectCallback {
    @BindView(R.id.banner)
    Banner banner;


    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.collectIV)
    ImageView collectIV;
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.soldTV)
    TextView soldTV;

    ServiceListBean serviceInfo;
    private CollectPresenter collectPresenter;
    private boolean isFavorite;//是否已收藏
//    private boolean isStart;//是否点击的起点
//    private PoiItem startPoi;//起点位置
//    private PoiItem endPoi;//起点位置
//    private DriveRouteResult mDriveRouteResult;


    public float price;//计算后的价格

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
    protected void initView() {

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


        }
        collectPresenter = new CollectPresenter(getContext(), this);
        collectPresenter.getFavorite(serviceInfo.getId());
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return collectPresenter;
    }

    @OnClick({R.id.collectIV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collectIV:
                if (isFavorite) {
                    collectPresenter.addFavorite(serviceInfo.getId(), FavoriteServiceFragment.CANCEL_TYPE);
                } else {
                    collectPresenter.addFavorite(serviceInfo.getId(), FavoriteServiceFragment.SERVICE_TYPE);
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




}
