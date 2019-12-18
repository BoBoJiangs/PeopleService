package com.yb.peopleservice.model.server;

import cn.sts.base.model.server.request.AbstractRequestServer;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/18 14:01
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class LoginRequestServer extends BaseRequestServer {

    public volatile static LoginRequestServer requestServer;

    /**
     * 获取单例
     */
    public static LoginRequestServer getInstance() {
        if (requestServer == null) {
            synchronized (AbstractRequestServer.class) {
                if (requestServer == null) {
                    requestServer = new LoginRequestServer();
                }
            }
        }
        return requestServer;
    }

    @Override
    public String getServerURL() {
        return SERVER_URL + "sso/";
    }
}
