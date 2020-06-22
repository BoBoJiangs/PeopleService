package com.yb.peopleservice.model.bean;

/**
 * 版本信息
 */
public class VersionVO {


    /**
     * id : 5fc6eb27-c384-4e13-bc13-db7c0bd3db60
     * type : 2
     * versionNumber : 1.0.3
     * link : https://www.shenghuoleida.com/android/2020/04/07/81242207-1ae6-4e6c-903a-1f842ca655e7.apk
     * detail : 更新bug
     * timestamp : 2020-04-07 11:09:50
     */

    private String id;
    private int type;
    private String versionNumber;
    private String link;
    private String detail;
    private String timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
