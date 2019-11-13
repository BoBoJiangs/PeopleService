package cn.sts.base.model.server.vo;

/**
 * 版本信息
 */
public class VersionVO {

    /**
     * app唯一标识符
     */
    private String appName;
    /**
     * APP名称
     */
    private String appTxt;
    /**
     * 更新描述
     */
    private String remark;
    /**
     * 下载地址
     */
    private String url;
    /**
     * 服务器最新版本号
     */
    private String versionCode;

    /**
     * 是否是强制更新 Y/N
     */
    private String isMustUpdate;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppTxt() {
        return appTxt;
    }

    public void setAppTxt(String appTxt) {
        this.appTxt = appTxt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getIsMustUpdate() {
        return isMustUpdate;
    }

    public void setIsMustUpdate(String isMustUpdate) {
        this.isMustUpdate = isMustUpdate;
    }
}
