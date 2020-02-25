package com.yb.peopleservice.view.activity.services.order;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.app.MyApplication;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.order.ConfirmOrderPresenter;
import com.yb.peopleservice.utils.AMapUtil;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.address.AddressListActivity;
import com.yb.peopleservice.view.activity.services.ShopListActivity;
import com.yb.peopleservice.view.activity.shop.SearchMapActivity;
import com.yb.peopleservice.view.adapter.order.PayActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.yb.peopleservice.view.fragment.user.order.CouponDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.NumberUtil;
import cn.sts.base.view.widget.UtilityView;

import static com.yb.peopleservice.view.adapter.order.PayActivity.ORDER_ID;

/**
 * 项目名称:PeopleService
 * 类描述: 确认订单
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/6 15:27
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ConfirmOrderActivity extends BaseToolbarActivity implements ConfirmOrderPresenter.IConfirmCallback, RouteSearch.OnRouteSearchListener {
    @BindView(R.id.addressUV)
    UtilityView addressUV;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.numTV)
    TextView numTV;
    @BindView(R.id.totalUV)
    UtilityView totalUV;
    @BindView(R.id.couponTV)
    UtilityView couponTV;
    @BindView(R.id.moneyTV)
    TextView moneyTV;
    @BindView(R.id.payBtn)
    Button payBtn;
    @BindView(R.id.prepayTV)
    TextView prepayTV;
    @BindView(R.id.moneyNameTV)
    TextView moneyNameTV;
    @BindView(R.id.addLL)
    LinearLayout addLL;
    @BindView(R.id.resultTV)
    TextView resultTV;
    @BindView(R.id.startLocationTV)
    EditText startLocationTV;
    @BindView(R.id.endLocationTV)
    EditText endLocationTV;
    @BindView(R.id.driverTypeLL)
    LinearLayout driverTypeLL;
    @BindView(R.id.shopTV)
    TextView shopTV;
    @BindView(R.id.shopLL)
    LinearLayout shopLL;

    private boolean isStart;//是否点击的起点
    private PoiItem startPoi;//起点位置
    private PoiItem endPoi;//起点位置
    private DriveRouteResult mDriveRouteResult;
    public float price;//计算后的价格

    private ServiceListBean bean;
    private int num = 1;
    private ConfirmOrderPresenter presenter;
    private AddressListVO addressListVO;//用户选择的地址
    private OrderBean orderBean = new OrderBean();
    private CouponBean couponBean;

    @Override
    public String getTitleName() {
        return "确认订单";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        super.initView();
        if (MyApplication.aMapLocation != null) {

            LatLonPoint mStartPoint = new LatLonPoint(MyApplication.aMapLocation.getLatitude()
                    , MyApplication.aMapLocation.getLongitude());
            startLocationTV.setText(MyApplication.aMapLocation.getPoiName());
            startPoi = new PoiItem("", mStartPoint, MyApplication.aMapLocation.getPoiName(), "");
        }
    }

    @Override
    protected void initData() {

        presenter = new ConfirmOrderPresenter(this, this);
        bean = getIntent().getParcelableExtra(ServiceListBean.class.getName());
        if (bean != null) {
            couponBean = bean.getCoupons();
            if (bean.getCoupons() != null) {
                if (couponBean.getType() == 1) {
                    couponTV.setContentText("﹣" + couponBean.getMoney() + "元");
                } else {
                    couponTV.setContentText(couponBean.getDiscount() + "折");
                }
            } else {
                couponTV.setVisibility(View.GONE);
            }
            if (bean.getShop() != null) {
                shopTV.setText(bean.getShop().getName());
            }else{
                shopLL.setVisibility(View.GONE);
            }

            if (bean.getCalculatedDistance() == 1) {
                addLL.setVisibility(View.GONE);
            }
            orderBean.setAmount(num);
            orderBean.setCommodityId(bean.getId());
            presenter.getDefaultAddress();
            nameTV.setText(bean.getName());
            String priceUnit = bean.getPriceUnit();
            if (TextUtils.isEmpty(priceUnit)) {
                priceUnit = "元";
            } else {
                priceUnit = "元/" + priceUnit;
            }
            priceTV.setText(bean.getPrice() + priceUnit);
            totalUV.setContentText("¥ " + bean.getPayMoney());
            moneyTV.setText("¥ " + bean.getPayMoney());
            List<String> images = bean.getMainImg();
            ImageLoaderUtil.loadServerImage(this, images.isEmpty() ? "" : images.get(0), imageView);
            if (bean.getCalculatedDistance() == 1) {
                addressUV.setVisibility(View.GONE);
                driverTypeLL.setVisibility(View.VISIBLE);
                totalUV.setVisibility(View.GONE);
                moneyTV.setVisibility(View.GONE);
                resultTV.setVisibility(View.GONE);
            } else {
                addressUV.setVisibility(View.VISIBLE);
                driverTypeLL.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }


    @OnClick({R.id.cutIV, R.id.addIV, R.id.payBtn, R.id.addressUV,
            R.id.startLocationTV, R.id.endLocationTV, R.id.shopLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopLL:
                startActivity(new Intent(this, ShopListActivity.class)
                        .putExtra(ServiceListBean.class.getName(), bean));
                break;
            case R.id.startLocationTV:
                isStart = true;
                startActivityForResult(new Intent(this, SearchMapActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
            case R.id.endLocationTV:
                isStart = false;
                startActivityForResult(new Intent(this, SearchMapActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
            case R.id.cutIV:
                if (num > 1) {
                    num--;
                }
                updatePrice();
                break;
            case R.id.addIV:
                if (num < 10) {
                    num++;
                } else {
                    ToastUtils.showLong("数量已达上限！");
                }
                updatePrice();
                break;
            case R.id.payBtn:
                if (bean.getCalculatedDistance() == 1) {
                    if (price == 0.0) {
                        ToastUtils.showLong("请选择终点位置");
                        return;
                    }
                    orderBean.setPrice(price);
                } else {
                    if (addressListVO == null) {
                        ToastUtils.showLong("请选择地址信息");
                        return;
                    }
                }

                Map<String, Object> childMap = new HashMap<>();
                List<CouponBean> beanList = new ArrayList<>();
                if (bean.getCoupons() != null) {
                    beanList.add(bean.getCoupons());
                    childMap.put("coupons", beanList);
                }
                orderBean.setGroupBuy(bean.getGroupType());
                orderBean.setGroupId(bean.getGroupId());
                childMap.put("order", orderBean);
                presenter.placeOrder(childMap);
//                //根据状态不同显示隐藏

                break;
            case R.id.addressUV:
                startActivityForResult(new Intent(getApplicationContext(), AddressListActivity.class)
                                .putExtra("isSelect", true),
                        IntentKeyConstant.REQUEST_CODE);
                break;
        }
    }

    private void updatePrice() {
        if (couponBean != null) {
            if (couponBean.getType() == 1) {
                bean.setPayMoney(num * bean.getPrice() - couponBean.getMoney());
            } else {
                bean.setPayMoney(num * bean.getPayMoney());
            }

        }
        orderBean.setAmount(num);
        numTV.setText(num + "");
        totalUV.setContentText("¥ " + bean.getPayMoney());
        moneyTV.setText("¥ " + bean.getPayMoney());
        totalUV.setVisibility(View.VISIBLE);
        moneyTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void couponSuccess(List<CouponBean> data) {

    }

    @Override
    public void addressSuccess(List<AddressListVO> data) {
        if (data != null && !data.isEmpty()) {
            addressListVO = data.get(0);
            setAddressText();
        }

    }

    private void setAddressText() {
        orderBean.setAddressBean(addressListVO);
        addressUV.setContentText(addressListVO.getConsigneeName() + " " + addressListVO.getConsigneePhone()
                + "\n" + addressListVO.getDetailAddress() + addressListVO.getHouseNum());
    }

    @Override
    public void orderSuccess(OrderBean data) {
        ToastUtils.showLong("下单成功");
        startActivity(new Intent(getApplicationContext(), PayActivity.class)
                .putExtra(AddressListVO.class.getName(), addressListVO)
                .putExtra(OrderBean.class.getName(), data)
                .putExtra(IntentKeyConstant.DATA_KEY, bean.getPayMoney()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentKeyConstant.REQUEST_CODE && data != null) {
            addressListVO = data.getParcelableExtra(AddressListVO.class.getName());
            if (addressListVO != null) {
                setAddressText();
            }
        }
        if (resultCode == ResponseCodeConstant.BASE_RESPONSE) {
            if (data != null) {
                PoiItem poiItem = data.getParcelableExtra(PoiItem.class.getName());
                if (poiItem != null) {
                    if (isStart) {
                        startPoi = poiItem;
                        startLocationTV.setText(poiItem.getTitle());
                    } else {
                        endPoi = poiItem;
                        endLocationTV.setText(poiItem.getTitle());
                    }
                    if (startPoi != null && endPoi != null) {
                        searchRouteResult();
                    }
                }
            }

        }
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult() {
        showProgressDialog();
        RouteSearch mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        LatLonPoint mStartPoint = new LatLonPoint(startPoi.getLatLonPoint().getLatitude()
                , startPoi.getLatLonPoint().getLongitude());
        LatLonPoint mEndPoint = new LatLonPoint(endPoi.getLatLonPoint().getLatitude()
                , endPoi.getLatLonPoint().getLongitude());
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault,
                null, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        dissmissProgressDialog();
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    if (drivePath == null) {
                        return;
                    }

                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    resultTV.setText(des);
                    if (bean.getStartDistance() * 1000 >= dis) {
                        price = bean.getStartPrice();
                        resultTV.append("   预计费用：" + price + "元");
                    } else {
                        float distance = (float) (dis - bean.getStartDistance() * 1000) / 1000;
                        price = NumberUtil.convertFloat1(bean.getStartPrice() + bean.getPayMoney() * distance);

                        resultTV.append("   预计费用：" + price + "元");
                    }
                    resultTV.setVisibility(View.VISIBLE);
                    bean.setPayMoney(price);
                    updatePrice();


                } else if (result.getPaths() == null) {
                    ToastUtils.showLong("对不起，没有搜索到相关数据！");
                }

            } else {
                ToastUtils.showLong("对不起，没有搜索到相关数据！");
            }
        } else {
            ToastUtils.showLong("对不起，没有搜索到相关数据！");
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}

