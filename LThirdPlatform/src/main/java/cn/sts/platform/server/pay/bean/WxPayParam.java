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

    private String appid;
    private String partnerid;
    private String noncestr;
    private String prepayid;
    private String sign;
    private String signtype;
    private String timestamp;

    public String getAppId() {
        return appid;
    }

    public void setAppId(String appId) {
        this.appid = appId;
    }

    public String getMchId() {
        return partnerid;
    }

    public void setMchId(String mchId) {
        this.partnerid = mchId;
    }

    public String getNonceStr() {
        return noncestr;
    }

    public void setNonceStr(String nonceStr) {
        this.noncestr = nonceStr;
    }

    public String getPrepayId() {
        return prepayid;
    }

    public void setPrepayId(String prepayId) {
        this.prepayid = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signtype;
    }

    public void setSignType(String signType) {
        this.signtype = signType;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timeStamp;
    }

}
