package com.yb.peopleservice.model.presenter.login;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yb.peopleservice.model.eventbean.EventLoginBean;

import org.greenrobot.eventbus.EventBus;

import cn.sts.platform.constant.ThirdPlatformStatusConstant;
import cn.sts.platform.presenter.login.ThirdPlatformLoginPresenter;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/24 23:02
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class WeChatLoginPresenter extends ThirdPlatformLoginPresenter {
    public WeChatLoginPresenter(Context context) {
        super(context);
    }

    @Override
    public void wxLoginResp(SendAuth.Resp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                EventBus.getDefault().post(new EventLoginBean(resp.code));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.showLong("授权登录已取消");
                break;
            default:
                ToastUtils.showLong("授权登录失败了");
                break;
        }
    }
}
