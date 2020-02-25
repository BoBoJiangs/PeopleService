package com.yb.peopleservice.model.bean.user.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.order.CouponBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述: 服务详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/24 15:46
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceListBean implements Parcelable {

    /**
     * id : b4272549-fd34-4afe-8d91-c0a7db740719
     * name : 家庭保洁
     * categoryId : 7cfce586-6494-42cc-b7a0-01f3068b3d66
     * shopId : 2aee461e-eccc-4f66-b80a-ddf1bf9b0f37
     * price : 50
     * priceType : 2
     * mainImgs : ["/imgs/2019/12/22/6ff6aeca-7063-4081-8504-c501205b65ad.png"]
     * contentImgs : ["/imgs/2019/12/22/7e6a92f7-27d9-4b87-b837-edf312623f37.png"]
     * contentText : 家庭是否灰尘太多了呢？没关系，有我们保洁人员来为你服务。
     * status : 1
     * selfSupport : 1
     * lastMonthSold : 0
     * totalSold : 0
     * timestamp : 2019-12-22 13:04:42
     * favorite : 0
     * calculatedDistance : 0
     * startPrice : 0
     * startDistance : 0
     * groupBuy : 0
     */

    private String id;
    private String name;
    private String categoryId;
    private String shopId;
    private float price;
    private int priceType;
    private String priceUnit;
    private String mainImg;


    private String mainImgs;
    private String contentImgs;
    private String contentText;
    private int status;//状态 1正常，2店铺下架，4删除
    private int selfSupport;
    private int lastMonthSold;
    private int totalSold;//累计已售数量
    private String timestamp;
    private int favorite;
    private int calculatedDistance;
    private float startPrice;//起步价
    private int startDistance;//起步距离 整数
    private int groupBuy;//开启团购模式，0否 1是
    private int groupType;//开启团购模式，0否 1是
    private float groupBuyPrice;//团购价
    private int groupSize;//参与团购的人数
    private String startTime;//团购开始时间
    private String endTime;//团购结束时间
    private float payMoney;//用户最后支付的金额
    private float praiseRate;//好评率
    private ShopInfo shop;//店铺
    private CouponBean coupons;//优惠券
    private String groupId;

    public String getMainImage() {
        return mainImg;
    }
    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }
    public String getGroupId() {
        return groupId == null ? "" : groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public CouponBean getCoupons() {
        return coupons;
    }

    public void setCoupons(CouponBean coupons) {
        this.coupons = coupons;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

    public float getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(float praiseRate) {
        this.praiseRate = praiseRate;
    }

    public float getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(float payMoney) {
        this.payMoney = payMoney;
    }

    public float getGroupBuyPrice() {
        return groupBuyPrice;
    }

    public void setGroupBuyPrice(float groupBuyPrice) {
        this.groupBuyPrice = groupBuyPrice;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public String getMainImgs() {
        return mainImgs;
    }

    public List<String> getMainImg() {
        List<String> imags = GsonUtils.fromJson(mainImgs, new TypeToken<List<String>>() {
        }.getType());
        if (imags != null) {
            return imags;
        } else {
            return new ArrayList<>();
        }

    }

    public void setMainImgs(String mainImgs) {
        this.mainImgs = mainImgs;
    }

    public List<String> getContentImgs() {
        List<String> imags = GsonUtils.fromJson(contentImgs, new TypeToken<List<String>>() {
        }.getType());
        if (imags != null) {
            return imags;
        } else {
            return new ArrayList<>();
        }
    }

    public void setContentImgs(String contentImgs) {
        this.contentImgs = contentImgs;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSelfSupport() {
        return selfSupport;
    }

    public void setSelfSupport(int selfSupport) {
        this.selfSupport = selfSupport;
    }

    public int getLastMonthSold() {
        return lastMonthSold;
    }

    public void setLastMonthSold(int lastMonthSold) {
        this.lastMonthSold = lastMonthSold;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    /**
     * 是否需要计算距离的订单
     * @return
     */
    public boolean isDistance() {
        return calculatedDistance == 1;
    }

    public int getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(int calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }

    public float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(float startPrice) {
        this.startPrice = startPrice;
    }

    public int getStartDistance() {
        return startDistance;
    }

    public void setStartDistance(int startDistance) {
        this.startDistance = startDistance;
    }

    //是否支持团购
    public boolean isGrop() {
        return groupBuy == 1;
    }

    public int getGroupBuy() {
        return groupBuy;
    }

    public void setGroupBuy(int groupBuy) {
        this.groupBuy = groupBuy;
    }

    public String getPriceUnit() {
        return priceUnit == null ? "" : priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public ServiceListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.categoryId);
        dest.writeString(this.shopId);
        dest.writeFloat(this.price);
        dest.writeInt(this.priceType);
        dest.writeString(this.priceUnit);
        dest.writeString(this.mainImg);
        dest.writeString(this.mainImgs);
        dest.writeString(this.contentImgs);
        dest.writeString(this.contentText);
        dest.writeInt(this.status);
        dest.writeInt(this.selfSupport);
        dest.writeInt(this.lastMonthSold);
        dest.writeInt(this.totalSold);
        dest.writeString(this.timestamp);
        dest.writeInt(this.favorite);
        dest.writeInt(this.calculatedDistance);
        dest.writeFloat(this.startPrice);
        dest.writeInt(this.startDistance);
        dest.writeInt(this.groupBuy);
        dest.writeInt(this.groupType);
        dest.writeFloat(this.groupBuyPrice);
        dest.writeInt(this.groupSize);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeFloat(this.payMoney);
        dest.writeFloat(this.praiseRate);
        dest.writeParcelable(this.shop, flags);
        dest.writeParcelable(this.coupons, flags);
        dest.writeString(this.groupId);
    }

    protected ServiceListBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.categoryId = in.readString();
        this.shopId = in.readString();
        this.price = in.readFloat();
        this.priceType = in.readInt();
        this.priceUnit = in.readString();
        this.mainImg = in.readString();
        this.mainImgs = in.readString();
        this.contentImgs = in.readString();
        this.contentText = in.readString();
        this.status = in.readInt();
        this.selfSupport = in.readInt();
        this.lastMonthSold = in.readInt();
        this.totalSold = in.readInt();
        this.timestamp = in.readString();
        this.favorite = in.readInt();
        this.calculatedDistance = in.readInt();
        this.startPrice = in.readFloat();
        this.startDistance = in.readInt();
        this.groupBuy = in.readInt();
        this.groupType = in.readInt();
        this.groupBuyPrice = in.readFloat();
        this.groupSize = in.readInt();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.payMoney = in.readFloat();
        this.praiseRate = in.readFloat();
        this.shop = in.readParcelable(ShopInfo.class.getClassLoader());
        this.coupons = in.readParcelable(CouponBean.class.getClassLoader());
        this.groupId = in.readString();
    }

    public static final Creator<ServiceListBean> CREATOR = new Creator<ServiceListBean>() {
        @Override
        public ServiceListBean createFromParcel(Parcel source) {
            return new ServiceListBean(source);
        }

        @Override
        public ServiceListBean[] newArray(int size) {
            return new ServiceListBean[size];
        }
    };
}
