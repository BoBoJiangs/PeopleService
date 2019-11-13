package cn.sts.platform.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.sts.base.util.Logs;
import cn.sts.platform.constant.ThirdPlatformBroadcastConstant;
import cn.sts.platform.constant.ThirdPlatformIntentKeyConstant;
import cn.sts.platform.constant.ThirdPlatformPayConstant;
import cn.sts.platform.constant.ThirdPlatformStatusConstant;
import cn.sts.platform.presenter.pay.PayPresenter;
import cn.sts.platform.util.ThirdPlatformUtil;

/**
 * 微信回调基类
 */
public class BaseWXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "BaseWXPayEntryActivity";

    private IWXAPI iwxapi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = ThirdPlatformUtil.getIWXAPI();
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Logs.i(TAG, "--------微信-onReq---------------");
    }

    @Override
    public void onResp(BaseResp resp) {
        Logs.i(TAG, "--------微信回调-onResp-----------");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int payResult;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    payResult = ThirdPlatformStatusConstant.SUCCESS;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    payResult = ThirdPlatformStatusConstant.CANCEL;
                    break;
                default:
                    payResult = ThirdPlatformStatusConstant.FAIL;
                    break;
            }
            new PayPresenter(this).sendPayResult(ThirdPlatformPayConstant.WX_PAY, payResult);
        }
        finish();
    }

}