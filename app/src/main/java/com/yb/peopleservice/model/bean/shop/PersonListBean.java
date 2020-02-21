package com.yb.peopleservice.model.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目名称:PeopleService
 * 类描述: 指派人员列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/16 16:05
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PersonListBean implements Parcelable {


    /**
     * id : d57ec5bf-ebd4-46ee-94dc-9d2e4e3a4aab
     * shopId : 2aee461e-eccc-4f66-b80a-ddf1bf9b0f37
     * serviceStaffId : 86063929-86e3-4464-8ad5-e34e1d82b2bb
     * status : 1
     * timestamp : 2019-12-27 15:30:29
     * serviceStaff : {"id":"86063929-86e3-4464-8ad5-e34e1d82b2bb","name":"丘处机","headImg":"/imgs/2019/12/22/dcd8cc26-cbed-4b5d-984b-0c1ac8673a9e.png","orderReceived":0,"refund":0,"orderNumber":2,"level":61,"praiseRate":3,"phone":"13688888","introduction":"上门服务人员","status":1,"idCardImgFront":"/imgs/2019/12/17/9713df2f-907f-4c0b-a749-5deea4dda56d.jpg","idCardImgBack":"/imgs/2019/12/17/a860746b-88c4-4e2e-aa88-d855502129d9.jpg","timestamp":"2019-12-17 12:24:32","longitude":"104.065731","latitude":"30"}
     */

    private String id;
    private String shopId;
    private String serviceStaffId;
    private int status;
    private String timestamp;
    private ServiceInfo serviceStaff;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getServiceStaffId() {
        return serviceStaffId;
    }

    public void setServiceStaffId(String serviceStaffId) {
        this.serviceStaffId = serviceStaffId;
    }

    public ServiceInfo getServiceStaff() {
        return serviceStaff;
    }

    public void setServiceStaff(ServiceInfo serviceStaff) {
        this.serviceStaff = serviceStaff;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.shopId);
        dest.writeString(this.serviceStaffId);
        dest.writeInt(this.status);
        dest.writeString(this.timestamp);
        dest.writeParcelable(this.serviceStaff, flags);
    }

    public PersonListBean() {
    }

    protected PersonListBean(Parcel in) {
        this.id = in.readString();
        this.shopId = in.readString();
        this.serviceStaffId = in.readString();
        this.status = in.readInt();
        this.timestamp = in.readString();
        this.serviceStaff = in.readParcelable(ServiceInfo.class.getClassLoader());
    }

    public static final Creator<PersonListBean> CREATOR = new Creator<PersonListBean>() {
        @Override
        public PersonListBean createFromParcel(Parcel source) {
            return new PersonListBean(source);
        }

        @Override
        public PersonListBean[] newArray(int size) {
            return new PersonListBean[size];
        }
    };
}
