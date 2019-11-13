package cn.sts.platform.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.sts.base.util.Logs;
import cn.sts.platform.presenter.login.ThirdPlatformLoginPresenter;
import cn.sts.platform.util.ThirdPlatformUtil;

/**
 * 微信回调基类
 */
public class BaseWXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static String TAG = "BaseWXEntryActivity";

    private IWXAPI iwxapi;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iwxapi = ThirdPlatformUtil.getIWXAPI();

        try {
            Intent intent = getIntent();
            iwxapi.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Logs.i(TAG, "--------微信--onReq----------");
    }

    @Override
    public void onResp(BaseResp resp) {

        Logs.i(TAG, "--------微信回调 errCode=" + resp.errCode + " type=" + resp.getType());
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            new ThirdPlatformLoginPresenter(this).wxLoginResp((SendAuth.Resp) resp);
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            //注：微信分享用户取消，微信也返回的分享成功
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtils.showLong("分享成功");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtils.showLong("取消分享");
                    break;
                default:
                    ToastUtils.showLong("分享失败");
                    break;
            }
        }

        finish();
    }

}