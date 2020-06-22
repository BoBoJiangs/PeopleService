package com.yb.peopleservice.view.activity.common;

import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.query.entity.Point;
import com.amap.api.track.query.entity.Track;
import com.amap.api.track.query.model.LatestPointRequest;
import com.amap.api.track.query.model.LatestPointResponse;
import com.amap.api.track.query.model.QueryTerminalRequest;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackRequest;
import com.amap.api.track.query.model.QueryTrackResponse;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.callback.SimpleOnTrackListener;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;

/**
 * 除轨迹点查询以外的简单查询接口示例
 */
public class OtherSearchActivity extends BaseToolbarActivity {

    private AMapTrackClient aMapTrackClient;

    private Marker locationMarker;
    private String userId;
    @BindView(R.id.activity_other_search_map)
    TextureMapView mapView;
    private List<Polyline> polylines = new LinkedList<>();
    private List<Marker> endMarkers = new LinkedList<>();
    private long trackId;//轨迹ID
    private boolean isTrack;//是否查看轨迹
    private OrderBean orderBean;
    private int positionType;//查驾车类终起点位置 1起点 2终点


    @Override
    public String getTitleName() {
        return "位置";
    }

    private void showLocationOnMap(LatLng latLng) {

        if (locationMarker != null) {
            locationMarker.remove();
        }

        mapView.getMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        locationMarker = mapView.getMap().addMarker(markerOptions);
    }

    private String pointToString(Point point) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return "{lat: " + point.getLat() + ", lng: " + point.getLng() +
                ", 上传时间: " + sdf.format(new Date(point.getTime())) +
                ", 定位精度" + point.getAccuracy() + ", 其他属性参考文档...}";
    }

    private void appendLogText(String text) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_other_search;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        orderBean = getIntent().getParcelableExtra(OrderBean.class.getName());
        isTrack = getIntent().getBooleanExtra("isTrack", false);
        positionType = getIntent().getIntExtra("positionType", 0);
        if (positionType == 1) {
            LatLng latLng = new LatLng(orderBean.getStartLatitude(), orderBean.getStartLongitude());
            showLocationOnMap(latLng);
        } else if (positionType == 2) {
            LatLng latLng = new LatLng(orderBean.getEndLatitude(), orderBean.getEndLongitude());
            showLocationOnMap(latLng);
        } else {
            if (isTrack) {
                trackId = orderBean.getTrajectoryId();
            }

            userId = orderBean.getServiceStaffId();
            aMapTrackClient = new AMapTrackClient(getApplicationContext());

            if (isTrack) {
                queryTerminalTrack();
            } else {
                queryLatestPoint();
            }
        }

    }

    /**
     * 查询订单轨迹
     */
    private void queryTerminalTrack() {
        clearTracksOnMap();
        // 先查询terminal id，然后用terminal id查询轨迹
        // 查询符合条件的所有轨迹，并分别绘制
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(AppConstant.SERVICE_ID, userId), new SimpleOnTrackListener() {
            @Override
            public void onQueryTerminalCallback(final QueryTerminalResponse queryTerminalResponse) {
                if (queryTerminalResponse.isSuccess()) {
                    if (queryTerminalResponse.isTerminalExist()) {
                        long tid = queryTerminalResponse.getTid();
                        // 搜索最近12小时以内上报的属于某个轨迹的轨迹点信息，散点上报不会包含在该查询结果中
                        QueryTrackRequest queryTrackRequest = new QueryTrackRequest(
                                AppConstant.SERVICE_ID,
                                tid,
                                trackId,     // 轨迹id，不指定，查询所有轨迹，注意分页仅在查询特定轨迹id时生效，查询所有轨迹时无法对轨迹点进行分页
                                TimeUtils.string2Millis(orderBean.getSetOutTime()),
                                TimeUtils.string2Millis(orderBean.getFinishTime()));
                        aMapTrackClient.queryTerminalTrack(queryTrackRequest, new SimpleOnTrackListener() {
                            @Override
                            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {
                                if (queryTrackResponse.isSuccess()) {
                                    List<Track> tracks = queryTrackResponse.getTracks();
                                    if (tracks != null && !tracks.isEmpty()) {
                                        boolean allEmpty = true;
                                        for (Track track : tracks) {
                                            List<Point> points = track.getPoints();
                                            if (points != null && points.size() > 0) {
                                                allEmpty = false;
                                                drawTrackOnMap(points);
                                            }
                                        }
                                        if (allEmpty) {
                                            ToastUtils.showLong("未获取到轨迹");
                                            LogUtils.a("所有轨迹都无轨迹点，请尝试放宽过滤限制，如：关闭绑路模式");
                                        } else {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("共查询到").append(tracks.size()).append("条轨迹，每条轨迹行驶距离分别为：");
                                            for (Track track : tracks) {
                                                sb.append(track.getDistance()).append("m,");
                                            }
                                            sb.deleteCharAt(sb.length() - 1);
                                            LogUtils.a(sb.toString());
                                        }
                                    } else {
                                        ToastUtils.showLong("未获取到轨迹");
                                    }
                                } else {
                                    ToastUtils.showLong("未获取到轨迹");
                                }
                            }
                        });
                    } else {
                        ToastUtils.showLong("Terminal不存在");
                    }
                } else {
                    LogUtils.a(queryTerminalResponse.getErrorMsg());
                }
            }
        });
    }

    /**
     * 查询实时位置
     */
    private void queryLatestPoint() {
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(AppConstant.SERVICE_ID, userId),
                new SimpleOnTrackListener() {
                    @Override
                    public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {
                        if (queryTerminalResponse.isSuccess()) {
                            long terminalId = queryTerminalResponse.getTid();
                            if (terminalId > 0) {
                                aMapTrackClient.queryLatestPoint(new LatestPointRequest(AppConstant.SERVICE_ID, terminalId), new SimpleOnTrackListener() {
                                    @Override
                                    public void onLatestPointCallback(LatestPointResponse latestPointResponse) {
                                        if (latestPointResponse.isSuccess()) {
                                            Point point = latestPointResponse.getLatestPoint().getPoint();
                                            LogUtils.e("查询实时位置成功，实时位置：" + pointToString(point));
                                            showLocationOnMap(new LatLng(point.getLat(), point.getLng()));
                                        } else {
                                            LogUtils.e("查询实时位置失败，" + queryTerminalResponse.getErrorMsg());
                                        }
                                    }
                                });
                            } else {
                                LogUtils.e("终端不存在，请先使用轨迹上报示例页面创建终端和上报轨迹");
                            }
                        } else {
                            LogUtils.e("终端查询失败，" + queryTerminalResponse.getErrorMsg());
                        }
                    }
                });
    }

    private void drawTrackOnMap(List<Point> points) {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE).width(20);
        if (points.size() > 0) {
            // 起点
            Point p = points.get(0);
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            endMarkers.add(mapView.getMap().addMarker(markerOptions));
        }
        if (points.size() > 1) {
            // 终点
            Point p = points.get(points.size() - 1);
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            endMarkers.add(mapView.getMap().addMarker(markerOptions));
        }
        for (Point p : points) {
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            polylineOptions.add(latLng);
            boundsBuilder.include(latLng);
        }
        Polyline polyline = mapView.getMap().addPolyline(polylineOptions);
        polylines.add(polyline);
        mapView.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
    }

    private void clearTracksOnMap() {
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
        for (Marker marker : endMarkers) {
            marker.remove();
        }
        endMarkers.clear();
        polylines.clear();
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


}
