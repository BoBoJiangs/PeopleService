package com.yb.peopleservice.view.fragment;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;

import butterknife.BindView;
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
        LocationSource {
    @BindView(R.id.mapView)
    TextureMapView mapView;
    @BindView(R.id.locationName)
    TextView locationName;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

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
        aMap = mapView.getMap();
        // 如果要设置定位的默认状态，可以在此处进行设置
        aMap.setLocationSource(this);// 设置定位监听
        myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setOnMyLocationChangeListener(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER));
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

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
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
        if(aMapLocation != null) {
            locationName.setText(String.format("%s%s", aMapLocation.getStreet(), aMapLocation.getStreetNum()));

        } else {
            Log.e("amap", "定位失败");
        }
    }
}
