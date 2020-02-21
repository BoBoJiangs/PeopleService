package com.yb.peopleservice.model.bean.user.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private int groupBuy;

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

    public List<String>  getContentImgs() {
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
    }

    protected ServiceListBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.categoryId = in.readString();
        this.shopId = in.readString();
        this.price = in.readFloat();
        this.priceType = in.readInt();
        this.priceUnit = in.readString();
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
