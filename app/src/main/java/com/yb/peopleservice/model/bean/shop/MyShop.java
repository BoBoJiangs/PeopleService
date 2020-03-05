package com.yb.peopleservice.model.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/20 15:53
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MyShop implements Parcelable {
    public static final int USER_DETAILS = 1;
    public static final int SHOP_DETAILS = 2;
    private String status;//状态: 1正常服务，店铺可以派单给他 3服务人员申请入驻店铺 4申请入驻拒绝 5申请离职
    private String message;
    private ShopInfo shop;
    private String id;
    private String shopId;
    private int type;//1：用户端查看详情 2：商家申请店铺

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId == null ? "" : shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeParcelable(this.shop, flags);
        dest.writeInt(this.type);
    }

    public MyShop() {
    }

    protected MyShop(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.shop = in.readParcelable(ShopInfo.class.getClassLoader());
        this.type = in.readInt();
    }

    public static final Creator<MyShop> CREATOR = new Creator<MyShop>() {
        @Override
        public MyShop createFromParcel(Parcel source) {
            return new MyShop(source);
        }

        @Override
        public MyShop[] newArray(int size) {
            return new MyShop[size];
        }
    };
}
