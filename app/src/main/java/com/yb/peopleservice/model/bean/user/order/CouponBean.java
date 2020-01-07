package com.yb.peopleservice.model.bean.user.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目名称:PeopleService
 * 类描述: 优惠券列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/7 11:18
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class CouponBean implements Parcelable {

    /**
     * amount : 0
     * description : string
     * discount : 0
     * discountCondition : 0
     * effectiveRange : 0
     * endTime : 2020-01-07T03:16:17.801Z
     * id : string
     * money : 0
     * name : string
     * overlayUse : 0
     * shopId : string
     * startTime : 2020-01-07T03:16:17.801Z
     * status : 0
     * timestamp : 2020-01-07T03:16:17.801Z
     * type : 0
     */

    private int amount;
    private String description;
    private int discount;
    private int discountCondition;
    private int effectiveRange;
    private String endTime;
    private String id;
    private int money;
    private String name;
    private int overlayUse;
    private String shopId;
    private String startTime;
    private int status;
    private String timestamp;
    private int type;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscountCondition() {
        return discountCondition;
    }

    public void setDiscountCondition(int discountCondition) {
        this.discountCondition = discountCondition;
    }

    public int getEffectiveRange() {
        return effectiveRange;
    }

    public void setEffectiveRange(int effectiveRange) {
        this.effectiveRange = effectiveRange;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOverlayUse() {
        return overlayUse;
    }

    public void setOverlayUse(int overlayUse) {
        this.overlayUse = overlayUse;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.amount);
        dest.writeString(this.description);
        dest.writeInt(this.discount);
        dest.writeInt(this.discountCondition);
        dest.writeInt(this.effectiveRange);
        dest.writeString(this.endTime);
        dest.writeString(this.id);
        dest.writeInt(this.money);
        dest.writeString(this.name);
        dest.writeInt(this.overlayUse);
        dest.writeString(this.shopId);
        dest.writeString(this.startTime);
        dest.writeInt(this.status);
        dest.writeString(this.timestamp);
        dest.writeInt(this.type);
    }

    public CouponBean() {
    }

    protected CouponBean(Parcel in) {
        this.amount = in.readInt();
        this.description = in.readString();
        this.discount = in.readInt();
        this.discountCondition = in.readInt();
        this.effectiveRange = in.readInt();
        this.endTime = in.readString();
        this.id = in.readString();
        this.money = in.readInt();
        this.name = in.readString();
        this.overlayUse = in.readInt();
        this.shopId = in.readString();
        this.startTime = in.readString();
        this.status = in.readInt();
        this.timestamp = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel source) {
            return new CouponBean(source);
        }

        @Override
        public CouponBean[] newArray(int size) {
            return new CouponBean[size];
        }
    };
}
