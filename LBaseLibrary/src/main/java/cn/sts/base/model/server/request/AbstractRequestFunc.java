package cn.sts.base.model.server.request;

import android.content.Context;

import cn.sts.base.R;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.exception.RequestException;
import cn.sts.base.model.server.vo.RequestResult;
import cn.sts.base.util.StringUtils;
import io.reactivex.annotations.NonNull;

/**
 * 请求数据统一封装类Function
 * Created by weilin on 16/11/16.
 */
public abstract class AbstractRequestFunc<T> extends AbstractFunc<T, RequestResult> {

    public AbstractRequestFunc() {
        super();
    }

    /**
     * 请求数据封装类
     *
     * @param requestListener 服务器请求回调监听
     */
    public AbstractRequestFunc(IRequestListener requestListener) {
        super(requestListener);
    }

    /**
     * 请求数据封装类
     *
     * @param context         context
     * @param requestListener 服务器请求回调监听
     */
    public AbstractRequestFunc(Context context, IRequestListener requestListener) {
        super(context, requestListener);
    }

    @Override
    public RequestResult apply(@NonNull RequestResult requestResult) throws Exception {
        if (requestResult.getCode() != 200) {
            if (StringUtils.isNotBlank(requestResult.getMsg())) {
                throw new RequestException(requestResult.getMsg());
            } else {
                if (getContextSoftReference() != null) {
                    throw new RequestException(getContextSoftReference().getString(R.string.exception_error));
                } else {
                    throw new RequestException("Request failed!");
                }
            }
        }

        AbstractRequestServer.JSESSIONID = requestResult.getJessionId();
        return requestResult;
    }

}
