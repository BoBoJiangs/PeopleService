package com.yb.peopleservice.constant;

import com.blankj.utilcode.util.PathUtils;

/**
 * 全局常量
 * Created by sts on 2018/5/7.
 *
 * @author daichao
 */

public class AppConstant {

    /**
     * 分页加载行数
     */
    public static final int ROWS = 20;

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
    public static String FILE_PATH = PathUtils.getExternalAppFilesPath() + "/image";
}
