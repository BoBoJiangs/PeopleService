package com.yb.peopleservice.model.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/10 22:54
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
@Entity
public class RecordBean {
    @Id(autoincrement = true)
    private Long recordId;
    private String id;
    private String userId;//用户iD
    private String fileUri;//录音文件的uri
    private String orderId;//订单id
    private Integer serialNumber;//录音序号
    private String timestamp;//时间戳
    private String localUri;//本地文件地址








    @Generated(hash = 520116363)
    public RecordBean(Long recordId, String id, String userId, String fileUri,
            String orderId, Integer serialNumber, String timestamp,
            String localUri) {
        this.recordId = recordId;
        this.id = id;
        this.userId = userId;
        this.fileUri = fileUri;
        this.orderId = orderId;
        this.serialNumber = serialNumber;
        this.timestamp = timestamp;
        this.localUri = localUri;
    }

    @Generated(hash = 96196931)
    public RecordBean() {
    }






    

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileUri() {
        return fileUri == null ? "" : fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getOrderId() {
        return orderId == null ? "" : orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getTimestamp() {
        return timestamp == null ? "" : timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocalUri() {
        return this.localUri;
    }

    public void setLocalUri(String localUri) {
        this.localUri = localUri;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
}
