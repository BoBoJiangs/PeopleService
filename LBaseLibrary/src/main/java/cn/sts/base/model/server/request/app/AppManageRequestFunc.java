package cn.sts.base.model.server.request.app;

import android.content.Context;

import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;

/**
 * 应用管理公用请求方法
 * Created by weilin on 18/7/2.
 */
public abstract class AppManageRequestFunc extends AbstractRequestFunc<IAppManageURL> {

    public AppManageRequestFunc(IRequestListener requestListener) {
        super(requestListener);
    }

    public AppManageRequestFunc(Context context, IRequestListener requestListener) {
        super(context, requestListener);
    }

    @Override
    public Class<IAppManageURL> getRequestInterfaceClass() {
        return IAppManageURL.class;
    }


}
