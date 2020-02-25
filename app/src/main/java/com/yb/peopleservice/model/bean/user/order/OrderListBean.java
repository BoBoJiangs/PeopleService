package com.yb.peopleservice.model.bean.user.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.yb.peopleservice.model.bean.shop.ServiceInfo;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述: 订单列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/8 9:18
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderListBean implements Parcelable {
    private OrderBean order;
    private ServiceListBean commodity;
    private ShopInfo shop;
    private List<CouponBean> coupons;
    private UserInfoBean customer;
    private ServiceInfo serviceStaff;

    public List<CouponBean> getCoupons() {
        if (coupons == null) {
            return new ArrayList<>();
        }
        return coupons;
    }

    public void setCoupons(List<CouponBean> coupons) {
        this.coupons = coupons;
    }

    public UserInfoBean getCustomer() {
        return customer;
    }

    public void setCustomer(UserInfoBean customer) {
        this.customer = customer;
    }

    public ServiceInfo getServiceStaff() {
        return serviceStaff;
    }

    public void setServiceStaff(ServiceInfo serviceStaff) {
        this.serviceStaff = serviceStaff;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public ServiceListBean getCommodity() {
        return commodity;
    }

    public void setCommodity(ServiceListBean commodity) {
        this.commodity = commodity;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

    public OrderListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.order, flags);
        dest.writeParcelable(this.commodity, flags);
        dest.writeParcelable(this.shop, flags);
        dest.writeTypedList(this.coupons);
        dest.writeParcelable(this.customer, flags);
        dest.writeParcelable(this.serviceStaff, flags);
    }

    protected OrderListBean(Parcel in) {
        this.order = in.readParcelable(OrderBean.class.getClassLoader());
        this.commodity = in.readParcelable(ServiceListBean.class.getClassLoader());
        this.shop = in.readParcelable(ShopInfo.class.getClassLoader());
        this.coupons = in.createTypedArrayList(CouponBean.CREATOR);
        this.customer = in.readParcelable(UserInfoBean.class.getClassLoader());
        this.serviceStaff = in.readParcelable(ServiceInfo.class.getClassLoader());
    }

    public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel source) {
            return new OrderListBean(source);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
        }
    };
}
