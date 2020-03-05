package com.yb.peopleservice.model.bean.user.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.yb.peopleservice.model.bean.shop.ShopInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/4 21:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MyCouponBean implements Parcelable {
    private CouponBean coupon;
    private ShopInfo shop;

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

    public MyCouponBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.coupon, flags);
        dest.writeParcelable(this.shop, flags);
    }

    protected MyCouponBean(Parcel in) {
        this.coupon = in.readParcelable(CouponBean.class.getClassLoader());
        this.shop = in.readParcelable(ShopInfo.class.getClassLoader());
    }

    public static final Creator<MyCouponBean> CREATOR = new Creator<MyCouponBean>() {
        @Override
        public MyCouponBean createFromParcel(Parcel source) {
            return new MyCouponBean(source);
        }

        @Override
        public MyCouponBean[] newArray(int size) {
            return new MyCouponBean[size];
        }
    };
}
