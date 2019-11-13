package cn.sts.base.util;

/**
 * 基础框架工具类
 * Created by weilin on 2019-07-03.
 */
public class AppManageUtil {

    /**
     * 应用唯一标识符，用于帮助中心/版本管理/支付
     */
    public static String APP_CODE;

    /**
     * 应用管理的URL
     */
    public static String APP_MANAGE_URL;

    /**
     * 设置 帮助中心/版本管理/支付 的访问信息
     *
     * @param appCode 应用唯一标识符
     * @param url     服务器地址
     */
    public static void setAppManage(String appCode, String url) {
        APP_CODE = appCode;
        APP_MANAGE_URL = url;
    }
}
