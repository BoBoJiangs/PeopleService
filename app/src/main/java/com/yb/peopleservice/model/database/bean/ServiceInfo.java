package com.yb.peopleservice.model.database.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.StringUtils;
import com.yb.peopleservice.model.bean.shop.ShopInfo;

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
    private String id;//服务人员ID
    private String address;//住址
    private String headImg;//头像uri
    private String idCardImgBack;//身份证人像页面的uri
    private String idCardImgFront;//身份证国徽页面的uri
    private String idCardNumber;//身份证号码
    private String introduction;//介绍
    private String jobStatus;//就业状态 0未入职 1正常服务，店铺可以派单给他 2申请入驻店铺 3申请离职
    private String message;
    private String name;//姓名
    private String nickname;
    private String phone;//手机号
    private int status;//状态:1正常 0禁用 2新注册 3已提交信息等待审核 4已提交信息审核不通过
    private String longitude;
    private String latitude;//服务人员位置纬度
    private int orderNumber;//累计完成订单数量
    private double praiseRate;//好评率 如0.99即好评率99%
    private int level;//每个完成的订单星级1-5的加总
    private int refund;//退款数
    private String sex;//性别 男 女
    private String shopId;//成功入驻以后的店铺的id
    private ShopInfo shop;//所在的店铺信息
    private String type;//人员类型 0最高管理员1运营人员2客服人员(店铺参数)
    private int age;
    private String birthday;
    private String province;//省
    private String city;//市
    private int stars;

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getProvince() {
        return province == null ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobStatus() {
        return jobStatus == null ? "" : jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

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

    public Double getLongitude() {
        if (StringUtils.isEmpty(longitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(longitude);
        }
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        if (StringUtils.isEmpty(latitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(latitude);
        }
    }

    public void setLatitude(String latitude) {
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
        dest.writeString(this.address);
        dest.writeString(this.headImg);
        dest.writeString(this.idCardImgBack);
        dest.writeString(this.idCardImgFront);
        dest.writeString(this.idCardNumber);
        dest.writeString(this.introduction);
        dest.writeString(this.jobStatus);
        dest.writeString(this.message);
        dest.writeString(this.name);
        dest.writeString(this.nickname);
        dest.writeString(this.phone);
        dest.writeInt(this.status);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeInt(this.orderNumber);
        dest.writeDouble(this.praiseRate);
        dest.writeInt(this.level);
        dest.writeInt(this.refund);
        dest.writeString(this.sex);
        dest.writeString(this.shopId);
        dest.writeParcelable(this.shop, flags);
        dest.writeString(this.type);
        dest.writeInt(this.age);
        dest.writeString(this.birthday);
        dest.writeString(this.province);
        dest.writeString(this.city);
    }

    protected ServiceInfo(Parcel in) {
        this.id = in.readString();
        this.address = in.readString();
        this.headImg = in.readString();
        this.idCardImgBack = in.readString();
        this.idCardImgFront = in.readString();
        this.idCardNumber = in.readString();
        this.introduction = in.readString();
        this.jobStatus = in.readString();
        this.message = in.readString();
        this.name = in.readString();
        this.nickname = in.readString();
        this.phone = in.readString();
        this.status = in.readInt();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.orderNumber = in.readInt();
        this.praiseRate = in.readDouble();
        this.level = in.readInt();
        this.refund = in.readInt();
        this.sex = in.readString();
        this.shopId = in.readString();
        this.shop = in.readParcelable(ShopInfo.class.getClassLoader());
        this.type = in.readString();
        this.age = in.readInt();
        this.birthday = in.readString();
        this.province = in.readString();
        this.city = in.readString();
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
