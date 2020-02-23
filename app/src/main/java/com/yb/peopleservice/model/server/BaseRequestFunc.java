package com.yb.peopleservice.model.server;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.NetworkUtils;
import com.yb.peopleservice.view.activity.login.LoginActivity;

import cn.sts.base.app.AppManager;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.exception.RequestException;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.base.model.server.request.AbstractRequestServer;
import cn.sts.base.model.server.vo.RequestResult;
import cn.sts.base.util.StringUtils;

/**
 * 类描述:请求数据统一封装类Function
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/12  11:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseRequestFunc<T> extends AbstractRequestFunc<T> {


    /**
     * 请求数据封装类
     *
     * @param requestListener 服务器请求回调监听
     */
    public BaseRequestFunc(IRequestListener requestListener) {
        super(requestListener);
    }

    /**
     * 请求数据封装类
     *
     * @param requestListener 服务器请求回调监听
     */
    public BaseRequestFunc(Context context, IRequestListener requestListener) {
        super(context, requestListener);
    }

    @Override
    public RequestResult apply(RequestResult requestResult) throws Exception {
        if (requestResult.getCode() == 401) {
            //跳转到登陆页面
            getContextSoftReference().startActivity(new Intent(getContextSoftReference(), LoginActivity.class));
            throw new RequestException("请先登录！");
        }
        if (requestResult.getCode() != 200) {
            if (StringUtils.isNotBlank(requestResult.getMsg())) {
                throw new RequestException(requestResult.getMsg());
            } else {
                if (!NetworkUtils.isConnected()) {
                    throw new RequestException("网络连接不可用，请检查后重试");
                } else {
                    throw new RequestException("Request failed!");
                }
            }
        }

        AbstractRequestServer.JSESSIONID = requestResult.getJessionId();
        return requestResult;
    }

}
