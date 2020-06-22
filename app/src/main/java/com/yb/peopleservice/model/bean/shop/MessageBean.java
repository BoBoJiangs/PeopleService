package com.yb.peopleservice.model.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/14 23:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MessageBean implements Parcelable {

    /**
     * content : string
     * id : string
     * personType : platform
     * sendType : system
     * targetId : string
     * timestamp : 2020-03-14T15:10:15.984Z
     * title : string
     */

    private String content;
    private String id;
    private String personType;
    private String sendType;
    private String targetId;
    private String timestamp;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.id);
        dest.writeString(this.personType);
        dest.writeString(this.sendType);
        dest.writeString(this.targetId);
        dest.writeString(this.timestamp);
        dest.writeString(this.title);
    }

    public MessageBean() {
    }

    protected MessageBean(Parcel in) {
        this.content = in.readString();
        this.id = in.readString();
        this.personType = in.readString();
        this.sendType = in.readString();
        this.targetId = in.readString();
        this.timestamp = in.readString();
        this.title = in.readString();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel source) {
            return new MessageBean(source);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };
}
