package com.yb.peopleservice.model.bean.user.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.yb.peopleservice.model.bean.PersonalListBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;

/**
 * 项目名称:PeopleService
 * 类描述:团购列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/22 11:37
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class GroupBean implements Parcelable {

    /**
     * id : b9addfcf-ea21-4cbc-bdfe-7bba4c53e842
     * size : 1
     * targetSize : 5
     * timestamp : 2020-02-22 02:40:15
     * endtime : 2020-02-23 09:50:04
     * status : 1
     * commodityId : 21bb9730-473d-426c-96af-1872ee226c9d
     * customerId : 292a31fc-15f4-4af6-bf36-bdc9a4134346
     * customer : {"id":"292a31fc-15f4-4af6-bf36-bdc9a4134346","name":"西部旺仔","nickname":"西部旺仔","orderNumber":0,"member":0,"headImg":"https://wx.qlogo.cn/mmopen/vi_32/My3R1ibeBzSUYl1OXMlpnW9ibYhKCa2ql2vicaH1dA8NMYxsc6dAzqYusAmjicc20tkln3PBQ20F3WYXibjfn6YNfug/132","status":1}
     */

    private String id;
    private int size;//当下人数
    private int targetSize;//目标人数
    private String timestamp;
    private String endtime;
    private int status;//团购状态 0时间到了未满人，被关闭 1凑人中 2凑满人
    private String commodityId;
    private String customerId;
    private UserInfoBean customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(int targetSize) {
        this.targetSize = targetSize;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public UserInfoBean getCustomer() {
        return customer;
    }

    public void setCustomer(UserInfoBean customer) {
        this.customer = customer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.size);
        dest.writeInt(this.targetSize);
        dest.writeString(this.timestamp);
        dest.writeString(this.endtime);
        dest.writeInt(this.status);
        dest.writeString(this.commodityId);
        dest.writeString(this.customerId);
        dest.writeParcelable(this.customer, flags);
    }

    public GroupBean() {
    }

    protected GroupBean(Parcel in) {
        this.id = in.readString();
        this.size = in.readInt();
        this.targetSize = in.readInt();
        this.timestamp = in.readString();
        this.endtime = in.readString();
        this.status = in.readInt();
        this.commodityId = in.readString();
        this.customerId = in.readString();
        this.customer = in.readParcelable(UserInfoBean.class.getClassLoader());
    }

    public static final Creator<GroupBean> CREATOR = new Creator<GroupBean>() {
        @Override
        public GroupBean createFromParcel(Parcel source) {
            return new GroupBean(source);
        }

        @Override
        public GroupBean[] newArray(int size) {
            return new GroupBean[size];
        }
    };
}
