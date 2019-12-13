package com.yb.peopleservice.model.server.file;

import android.content.Context;

import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.AbstractRequestFunc;

/**
 * 类描述:上架商品
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/12  11:10
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public abstract class UploadRequestFunc extends AbstractRequestFunc<UploadRequest> {

    public UploadRequestFunc(Context context, IRequestListener requestListener) {
        super(context, requestListener);
    }

    @Override
    public Class<UploadRequest> getRequestInterfaceClass() {
        return UploadRequest.class;
    }
}
