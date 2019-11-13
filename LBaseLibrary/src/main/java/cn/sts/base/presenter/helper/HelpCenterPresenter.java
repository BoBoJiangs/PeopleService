package cn.sts.base.presenter.helper;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IQueryListCallback;
import cn.sts.base.model.server.request.app.AppManageRequestFunc;
import cn.sts.base.model.server.request.app.AppManageRequestServer;
import cn.sts.base.model.server.request.app.IAppManageURL;
import cn.sts.base.model.server.vo.HelpCenterVO;
import cn.sts.base.presenter.AbstractQueryListPresenter;
import cn.sts.base.util.AppManageUtil;
import cn.sts.base.util.Logs;
import cn.sts.base.util.StringUtils;
import io.reactivex.Observable;


/**
 * 常见问题类型列表提供者
 * Created by weilin on 2019/7/3.
 *
 * @author weilin
 */

public class HelpCenterPresenter extends AbstractQueryListPresenter<HelpCenterVO> {

    private static final String TAG = "HelpCenterPresenter";
    /**
     * 1:帮助中心类别列表 2:类别二级列表
     */
    private Long typeId;

    public HelpCenterPresenter(Context context, Long typeId,
                               IQueryListCallback<HelpCenterVO> queryListListener) {

        super(context, queryListListener);
        this.typeId = typeId;
    }

    @Override
    public void getList(boolean isShowProgress) {
        if (StringUtils.isBlank(AppManageUtil.APP_CODE)) {
            Logs.e(TAG, "APP_CODE为空，请调用LBaseUtil设置");
            return;
        }
        AppManageRequestFunc requestFunc = new AppManageRequestFunc(context, getRequestListener()) {

            @Override
            public Observable getObservable(IAppManageURL iRequestServer) {
                Map<String, Object> map = new HashMap<>();
                if (typeId == null) {
                    map.put("appName", AppManageUtil.APP_CODE);
                    return iRequestServer.getHelpTypeList(map);
                } else {
                    map.put("typeId", typeId);
                    map.put("treeName", "APP");
                    return iRequestServer.getHelpListByType(map);
                }
            }
        };
        requestFunc.setShowProgress(isShowProgress);
        AppManageRequestServer.getInstance().request(requestFunc);
    }

}
