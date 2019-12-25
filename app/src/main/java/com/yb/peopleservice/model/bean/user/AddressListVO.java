package com.yb.peopleservice.model.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目名称:Flower
 * 类描述: 地址列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/12 15:29
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class AddressListVO implements Parcelable {
    private String id;
    private String consigneeName;
    private String isDefault;// Y/N
    private String houseNum;//门牌号
    private String consigneePhone;
    private String detailAddress;


    public String getHouseNum() {
        return houseNum == null ? "" : houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsigneeName() {
        return consigneeName == null ? "" : consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getIsDefault() {
        return isDefault == null ? "" : isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getConsigneePhone() {
        return consigneePhone == null ? "" : consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getDetailAddress() {
        return detailAddress == null ? "" : detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public AddressListVO() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.consigneeName);
        dest.writeString(this.isDefault);
        dest.writeString(this.consigneePhone);
        dest.writeString(this.detailAddress);
        dest.writeString(this.houseNum);
    }

    protected AddressListVO(Parcel in) {
        this.id = in.readString();
        this.consigneeName = in.readString();
        this.isDefault = in.readString();
        this.consigneePhone = in.readString();
        this.detailAddress = in.readString();
        this.houseNum = in.readString();
    }

    public static final Creator<AddressListVO> CREATOR = new Creator<AddressListVO>() {
        @Override
        public AddressListVO createFromParcel(Parcel source) {
            return new AddressListVO(source);
        }

        @Override
        public AddressListVO[] newArray(int size) {
            return new AddressListVO[size];
        }
    };
}
