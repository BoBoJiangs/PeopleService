package com.yb.peopleservice.view.fragment.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.yb.peopleservice.GlideApp;
import com.yb.peopleservice.R;
import com.yb.peopleservice.app.MyApplication;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.user.service.MapPresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.view.activity.im.ChatListActivity;
import com.yb.peopleservice.view.activity.personal.SetActivity;
import com.yb.peopleservice.view.weight.CustomPopup.MapBottomPopup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/27 14:20
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class LifeRadarMapFragment extends BaseFragment implements AMapLocationListener,
        LocationSource, MapPresenter.INearbyCallback, OnTabSelectListener {
    @BindView(R.id.mapView)
    TextureMapView mapView;
    @BindView(R.id.locationName)
    TextView locationName;
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    //自定义定位小蓝点的Marker
    private Marker locationMarker;

    private Marker moveMarker;//移动的定位marker

    private MapPresenter presenter;
    ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private LatLng latLng;
    private BitmapDescriptor descriptor1;
    private BitmapDescriptor descriptor2;
    private int selectPosition;
    private ArrayList<Marker> listMarker = new ArrayList<>();//显示附近店铺和人员的marker集合
    private View markerView;
    private boolean isLocation;//是否点击定位按钮
    private  final float mapLevel = 15;

    public static Fragment getInstanceFragment() {
        LifeRadarMapFragment fragment = new LifeRadarMapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {

        descriptor1 = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.icon_location2));
        descriptor2 = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.location_shop));
        aMap = mapView.getMap();
        // 如果要设置定位的默认状态，可以在此处进行设置
        aMap.setLocationSource(this);// 设置定位监听
        myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setOnMyLocationChangeListener(this);
        aMap.getUiSettings().setLogoBottomMargin(-60);//隐藏logo
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER));
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                startJumpAnimation();
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter(null);
            }
        });
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_life;
    }

    @Override
    protected void initView() {
        setUpMap();
    }

    @Override
    protected void initData() {
        mTabEntities.add(new TabEntity("个人", 0, 0));
        mTabEntities.add(new TabEntity("商家", 0, 0));
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(this);
        presenter = new MapPresenter(getContext(), this);

    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (moveMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = moveMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= SizeUtils.dp2px(60);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart() {

                }

                @Override
                public void onAnimationEnd() {
                    getNearbyData();
                }
            });
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            moveMarker.setAnimation(animation);
            //开始动画
            moveMarker.startAnimation();

        } else {
            Log.e("ama", "screenMarker is null");
        }
    }

    private void getNearbyData() {
        if (moveMarker != null) {
            LatLng latLng = moveMarker.getPosition();
            presenter.getNearbyData(latLng, AppConstant.SHOP_TYPE);
//            if (selectPosition == 0) {
//                presenter.getNearbyData(latLng, AppConstant.SERVICE_TYPE);
//            } else {
//                presenter.getNearbyData(latLng, AppConstant.SHOP_TYPE);
//            }
        }

    }

    @OnClick({R.id.msgIV2,R.id.locationIV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msgIV2:
                startActivity(new Intent(getContext(), ChatListActivity.class));
                break;
            case R.id.locationIV:
                if (latLng!=null){
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapLevel));
                }

                break;
        }
    }

    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        moveMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        moveMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        moveMarker.setZIndex(1);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //是指定位间隔
            mLocationOption.setInterval(5000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        // 定位回调监听
        if (aMapLocation != null) {
            MyApplication.aMapLocation = aMapLocation;
            locationName.setText(String.format("%s%s", aMapLocation.getStreet(), aMapLocation.getStreetNum()));
            latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            //展示自定义定位小蓝点
            if (locationMarker == null) {
                presenter.getNearbyData(latLng, AppConstant.SHOP_TYPE);
                //首次定位
                locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker))
                        .anchor(0.5f, 0.5f));

                //首次定位,选择移动到地图中心点并修改级别到15级
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapLevel));
                presenter.getNearbyData(latLng, AppConstant.SERVICE_TYPE);
            } else {
                LatLng curLatlng = locationMarker.getPosition();
                if (curLatlng == null || !curLatlng.equals(latLng)) {
                    locationMarker.setPosition(latLng);
                }
            }
        } else {
            Log.e("amap", "定位失败");
        }
    }

    @Override
    public void onSuccess(List<ServiceInfo> data) {
//        clearMarker();
//        for (ServiceInfo info : data) {
//            LatLng latLng = new LatLng(info.getLatitude(), info.getLongitude());
//            setMarkerView(latLng, info, false);
//        }
//        locationMarker.setPosition(latLng);
    }

    @Override
    public void shopSuccess(List<ShopInfo> data) {
        clearMarker();
        for (ShopInfo info : data) {
            getShopHeadIV(BaseRequestServer.getFileUrl(true) +
                    info.getHeadImg(), info);
        }
        locationMarker.setPosition(latLng);
    }

    private void getShopHeadIV(String headUrl, ShopInfo shopInfo) {

        Glide.with(getContext())
                .asBitmap()
                .load(headUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        markerView = View.inflate(getContext(), R.layout.marker_view, null);
                        ImageView markerIV = markerView.findViewById(R.id.markerIV);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        markerIV.setImageDrawable(circularBitmapDrawable);
                        Bitmap bitmap = convertViewToBitmap(markerView);
                        LatLng latLng = new LatLng(shopInfo.getLatitude(), shopInfo.getLongitude());
                        setMarkerView(latLng, shopInfo, bitmap);
                    }
                });
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    private void setMarkerView(LatLng latLng, Object info, Bitmap bitmap) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));

        Marker marker = aMap.addMarker(markerOption);
        marker.setObject(info);
        listMarker.add(marker);
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onTabSelect(int position) {
        selectPosition = position;
        getNearbyData();
    }

    private void clearMarker() {
        for (Marker marker : listMarker) {
            marker.remove();
        }
        listMarker.clear();
    }

    @Override
    public void onTabReselect(int position) {

    }

    // 定义 Marker 点击事件监听
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.getObject() != null) {
                if (marker.getObject() instanceof ShopInfo) {
                    ShopInfo shopInfo = (ShopInfo) marker.getObject();
                    new XPopup.Builder(getContext())
                            .hasShadowBg(false)
                            .offsetY(-SizeUtils.dp2px(70))
                            .asCustom(new MapBottomPopup(getActivity(), shopInfo))
                            .show();
                }
                if (marker.getObject() instanceof ServiceInfo) {
                    ServiceInfo serviceInfo = (ServiceInfo) marker.getObject();
                    new XPopup.Builder(getContext())
                            .hasShadowBg(false)
                            .offsetY(-SizeUtils.dp2px(70))
                            .asCustom(new MapBottomPopup(getActivity(), serviceInfo))
                            .show();
                }
            }
            return false;
        }
    };
}
