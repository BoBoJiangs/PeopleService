package com.yb.peopleservice.model.database.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 项目名称:PeopleService
 * 类描述: 服务人员认证信息
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/16 16:05
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceInfo implements Parcelable {


    /**
     * customerPraiseRate : 0
     * headImg : string
     * id : string
     * idCardChecked : 0
     * idCardImgBack : string
     * idCardImgFront : string
     * idCardNumber : string
     * introduction : string
     * level : 0
     * message : string
     * name : string
     * nickname : string
     * orderAcceptanceRate : 0
     * phone : string
     * refundRate : 0
     * status : 0
     * timestamp : 2019-12-19T07:12:14.882Z
     */
    private String id;
    private String headImg;
    private String idCardImgBack;
    private String idCardImgFront;
    private String idCardNumber;
    private String introduction;
    private String message;
    private String name;
    private String nickname;
    private String phone;
    private int status;
    private double longitude;
    private double latitude;
    private int orderNumber;
    private double praiseRate;
    private int level;
    private String shopId;

    public String getShopId() {
        return shopId == null ? "" : shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(double praiseRate) {
        this.praiseRate = praiseRate;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCardImgBack() {
        return idCardImgBack;
    }

    public void setIdCardImgBack(String idCardImgBack) {
        this.idCardImgBack = idCardImgBack;
    }

    public String getIdCardImgFront() {
        return idCardImgFront;
    }

    public void setIdCardImgFront(String idCardImgFront) {
        this.idCardImgFront = idCardImgFront;
    }

    public String getIdCardNumber() {
        return idCardNumber == null ? "" : idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ServiceInfo() {
    }

    @Generated(hash = 364865774)
    public ServiceInfo(String id, String headImg, String idCardImgBack,
            String idCardImgFront, String idCardNumber, String introduction,
            String message, String name, String nickname, String phone,
            int status) {
        this.id = id;
        this.headImg = headImg;
        this.idCardImgBack = idCardImgBack;
        this.idCardImgFront = idCardImgFront;
        this.idCardNumber = idCardNumber;
        this.introduction = introduction;
        this.message = message;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.headImg);
        dest.writeString(this.idCardImgBack);
        dest.writeString(this.idCardImgFront);
        dest.writeString(this.idCardNumber);
        dest.writeString(this.introduction);
        dest.writeString(this.message);
        dest.writeString(this.name);
        dest.writeString(this.nickname);
        dest.writeString(this.phone);
        dest.writeInt(this.status);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeInt(this.orderNumber);
        dest.writeDouble(this.praiseRate);
        dest.writeInt(this.level);
    }

    protected ServiceInfo(Parcel in) {
        this.id = in.readString();
        this.headImg = in.readString();
        this.idCardImgBack = in.readString();
        this.idCardImgFront = in.readString();
        this.idCardNumber = in.readString();
        this.introduction = in.readString();
        this.message = in.readString();
        this.name = in.readString();
        this.nickname = in.readString();
        this.phone = in.readString();
        this.status = in.readInt();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.orderNumber = in.readInt();
        this.praiseRate = in.readDouble();
        this.level = in.readInt();
    }

    public static final Creator<ServiceInfo> CREATOR = new Creator<ServiceInfo>() {
        @Override
        public ServiceInfo createFromParcel(Parcel source) {
            return new ServiceInfo(source);
        }

        @Override
        public ServiceInfo[] newArray(int size) {
            return new ServiceInfo[size];
        }
    };
}
