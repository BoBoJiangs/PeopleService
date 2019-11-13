package cn.sts.platform.server.pay;

import android.content.Context;

import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;

/**
 * 支付接口
 * Created by weilin on 2019/7/1.
 */
public abstract class AbstractPayRequestFunc extends AbstractRequestFunc<IPayRequest> {

    public AbstractPayRequestFunc(Context context, IRequestListener requestListener) {
        super(context, requestListener);
    }

    @Override
    public Class<IPayRequest> getRequestInterfaceClass() {
        return IPayRequest.class;
    }
}
