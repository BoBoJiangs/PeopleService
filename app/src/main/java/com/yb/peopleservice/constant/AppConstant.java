package com.yb.peopleservice.constant;

import com.blankj.utilcode.util.PathUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局常量
 * Created by sts on 2018/5/7.
 *
 * @author daichao
 */

public class AppConstant {

    public static final String BEFORE_TIME = "BEFORE_TIMES";

    public static final String CHAT_PASSWORD = "123456";
    /**
     * 分页加载行数
     */
    public static final int ROWS = 20;

    /**
     * bugly 应用 id
     */
    public static String BUGLY_APP_ID = "31afacff7e";

    public static String JPUSH_KEY = "2016fa17df9b1e19a1ba9d27";

    /**
     * QQ分享的AppId
     */
//    public static final String SHARE_QQ_APP_ID = "1107832743";
    public static final String SHARE_QQ_APP_ID = "1107390031";

    /**
     * 微信分享的AppId
     */
    public static final String SHARE_WECHAT_APP_ID = "wx7fc567068d46fa17";

    /**
     * 微信分享的SECRET
     */
    public static final String WECHAT_SECRET = "0b7dc133c77f4cd4d374352061274904";

    /**
     * 图片缓存路径
     */
    public static String FILE_PATH = PathUtils.getExternalAppFilesPath() + "/imagePicker";

    /**
     * 微信支付
     */
    public static final int WECHAT_TYPE = 1;

    /**
     * 支付宝支付
     */
    public static final int ALIPAY_TYPE = 2;


    /**
     * 服务人员
     */
    public static final int SERVICE_TYPE = 1;

    /**
     * 店铺
     */
    public static final int SHOP_TYPE = 2;


    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();
    public static final String CONV_TITLE = "conv_title";
    public static final String DRAFT = "draft";
    public static final String CONV_TYPE = "conversationType"; //value使用 ConversationType
    public static final String ROOM_ID = "roomId";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String MSG_JSON = "msg_json";
    public static final String MSG_LIST_JSON = "msg_list_json";
    public static final String NAME = "name";
    public static final String ATALL = "atall";
    public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
    public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
    public static final String SEARCH_AT_APPKEY = "search_at_appkey";
}
