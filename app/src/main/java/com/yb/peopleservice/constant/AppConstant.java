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
    public static String BUGLY_APP_ID = "cbea7096ad";

//    public static String JPUSH_KEY = "2016fa17df9b1e19a1ba9d27";
    //IM接收方的appkey
    public static String JPUSH_KEY = "442d6e29c9adcf8ad9850a24";
    /**
     * QQ分享的AppId
     */
//    public static final String SHARE_QQ_APP_ID = "1107832743";
    public static final String SHARE_QQ_APP_ID = "1107390031";

    /**
     * 微信分享的AppId
     */
    public static final String SHARE_WECHAT_APP_ID = "wx0fe47f01f97b1710";

    /**
     * 微信分享的SECRET
     */
    public static final String WECHAT_SECRET = "d8ea6db4d2013589f0554dfd21e325dd";


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

    /**
     * 终端名称，该名称可以根据使用方业务而定，比如可以是用户名、用户手机号等唯一标识
     * <p>
     * 通常应该使用该名称查询对应终端id，确定该终端是否存在，如果不存在则创建，然后就可以开启轨迹上报，将上报的轨迹关联
     * 到该终端
     */
    public static final String TERMINAL_NAME = "service_track";

    /**
     * 服务id，请修改成您创建的服务的id
     * <p>
     * 猎鹰轨迹服务，同一个开发者账号下的key可以直接使用同账号下的sid，不再需要人工绑定
     */
    public static final long SERVICE_ID = 125396;

    public static String PROVINCE = "云南省";

    public static String CITY = "昭通市";
}
