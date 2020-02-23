package com.yb.peopleservice.model.bean.user.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.yb.peopleservice.model.database.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述: 评价
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/22 16:45
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EvaluateBean implements Parcelable {

    /**
     * id : 8275f5a-3e77-4bb7-b1c1-e61f4f0eee8b
     * text : 服务质量还不错服务质量还不错服务质量还不错服务质量还不错服务质量还不错服务质量还不错服务质量还不错服务质量还不错服务质量还不错服务质量还不错
     * imgs : ["/imgs/2019/12/22/183b18ac-70fe-4af5-8f4b-a6cdf062677c.png"]
     * type : 1
     * sourceType : 3
     * sourceId : 9541869a-5c14-4b0f-b10d-4df0bc36c6f0
     * timestamp : 2020-01-14 12:19:56
     * commodityId : 21bb9730-473d-426c-96af-1872ee226c9d
     * orderId : a42bf189-64ae-4d26-9691-18ca28deaf3b
     * level : 5
     * customer : {"id":"9541869a-5c14-4b0f-b10d-4df0bc36c6f0","nickname":"星星","orderNumber":14,"member":0,"headImg":"/imgs/2019/12/26/6885a259-1d32-46d8-b20c-ca7db3e56889.png","status":1}
     */

    private String id;
    private String text;
    private String imgs;
    private int type;
    private int sourceType;
    private String sourceId;
    private String timestamp;
    private String commodityId;
    private String orderId;
    private int level;
    private UserInfoBean customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImgs() {
        List<String> imagList = GsonUtils.fromJson(imgs, new TypeToken<List<String>>() {
        }.getType());
        if (imagList != null) {
            return imagList;
        } else {
            return new ArrayList<>();
        }
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        dest.writeString(this.text);
        dest.writeString(this.imgs);
        dest.writeInt(this.type);
        dest.writeInt(this.sourceType);
        dest.writeString(this.sourceId);
        dest.writeString(this.timestamp);
        dest.writeString(this.commodityId);
        dest.writeString(this.orderId);
        dest.writeInt(this.level);
        dest.writeParcelable(this.customer, flags);
    }

    public EvaluateBean() {
    }

    protected EvaluateBean(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
        this.imgs = in.readString();
        this.type = in.readInt();
        this.sourceType = in.readInt();
        this.sourceId = in.readString();
        this.timestamp = in.readString();
        this.commodityId = in.readString();
        this.orderId = in.readString();
        this.level = in.readInt();
        this.customer = in.readParcelable(UserInfoBean.class.getClassLoader());
    }

    public static final Creator<EvaluateBean> CREATOR = new Creator<EvaluateBean>() {
        @Override
        public EvaluateBean createFromParcel(Parcel source) {
            return new EvaluateBean(source);
        }

        @Override
        public EvaluateBean[] newArray(int size) {
            return new EvaluateBean[size];
        }
    };
}
