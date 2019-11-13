package cn.sts.platform.server.pay.bean;

public class WxPayParam {
    /**
     * appId : wx19ac8cdefec73b35
     * mchId : 1444981102
     * nonceStr : jwsLDDzZeq7lxZuV
     * prepayId : wx10153259253042d83fe8277d3065564464
     * sign : 853A5BC5AE0E2110BDAFA19A98904DF8
     * signType : MD5
     * timeStamp : 1557473245
     */

    private String appId;
    private String mchId;
    private String nonceStr;
    private String prepayId;
    private String sign;
    private String signType;
    private String timeStamp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
