package cn.sts.platform.server.login;

import android.content.Context;

import java.util.Map;

import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractFunc;
import cn.sts.base.model.server.request.AbstractRequestFunc;
import cn.sts.platform.server.pay.IPayRequest;

/**
 * 第三方登录
 * Created by weilin on 2019/7/1.
 */
public abstract class AbstractTPLoginRequestFunc extends AbstractFunc<ITPLoginRequest, Object> {

    public AbstractTPLoginRequestFunc(Context context, IRequestListener requestListener) {
        super(context, requestListener);
    }

    @Override
    public Class<ITPLoginRequest> getRequestInterfaceClass() {
        return ITPLoginRequest.class;
    }
}
