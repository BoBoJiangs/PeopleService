package cn.sts.platform.util;

/**
 * 支付管理工具类
 * Created by weilin on 2019-07-11.
 */
public class PayUtil {

    /**
     * 支付的URL
     */
    private static String PAY_URL;

    /**
     * 设置支付的地址
     *
     * @param url 地址
     */
    public static void setPayUrl(String url) {
        PAY_URL = url;
    }

    public static String getPayUrl() {
        return PAY_URL;
    }
}
