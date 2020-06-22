package cn.sts.platform.presenter.login;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.util.Logs;
import cn.sts.platform.constant.ThirdPlatformBroadcastConstant;
import cn.sts.platform.constant.ThirdPlatformConstant;
import cn.sts.platform.constant.ThirdPlatformIntentKeyConstant;
import cn.sts.platform.constant.ThirdPlatformStatusConstant;
import cn.sts.platform.server.login.AbstractTPLoginRequestFunc;
import cn.sts.platform.server.login.ITPLoginRequest;
import cn.sts.platform.server.login.TPLoginRequestServer;
import cn.sts.platform.server.login.bean.WXUser;
import cn.sts.platform.util.ThirdPlatformUtil;
import io.reactivex.Observable;

/**
 * 第三方登录
 * Created by weilin on 2019-07-01.
 */
public class ThirdPlatformLoginPresenter {

    private static final String TAG = "ThirdPlatformLoginPrese";

    private Context context;


    public ThirdPlatformLoginPresenter(Context context) {
        this.context = context;
    }

    /**
     * 微信登录
     */
    public void wxLogin() {
        Logs.e(TAG, "-----------wxLogin---------");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        //第三方程序本身用来标识其请求的唯一性，最后跳转回第三方程序时，由微信终端回传。
        req.state = String.valueOf(System.currentTimeMillis());
        if (!ThirdPlatformUtil.getIWXAPI().sendReq(req)) {
            sendLoginResult(null, ThirdPlatformStatusConstant.FAIL);
        }
    }

    /**
     * 解析微信授权后返回的数据
     *
     * @param resp 微信返回的数据
     */
    public void wxLoginResp(SendAuth.Resp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                LogUtils.a(resp.code);
//                wxLoginGetAccessToken(resp.code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                sendLoginResult(null, ThirdPlatformStatusConstant.CANCEL);
                break;
            default:
                sendLoginResult(null, ThirdPlatformStatusConstant.FAIL);
                break;
        }
    }

    /**
     * 获取微信登录access token
     *
     * @param code 授权code
     */
    private void wxLoginGetAccessToken(String code) {
        AbstractTPLoginRequestFunc requestFunc = new AbstractTPLoginRequestFunc(context, new IRequestListener<Map>() {
            @Override
            public void onRequestSuccess(Map data) {
                String openid = (String) data.get("openid");
                String token = (String) data.get("access_token");
                wxLoginGetUserInfo(openid, token);
            }

            @Override
            public void onRequestFailure(String error) {
                sendLoginResult(null, ThirdPlatformStatusConstant.FAIL);
            }

            @Override
            public void onRequestCancel() {
                sendLoginResult(null, ThirdPlatformStatusConstant.CANCEL);
            }
        }) {
            @Override
            public Observable getObservable(ITPLoginRequest iRequestServer) {
                Map<String, String> map = new HashMap<>(3);
                map.put("appid", ThirdPlatformConstant.WX_APP_ID);
                map.put("secret", ThirdPlatformConstant.WX_APP_SECRET);
                map.put("code", code);
                map.put("grant_type", "authorization_code");
                return iRequestServer.getWXAccessToken(map);
            }
        };
        requestFunc.setShowProgress(false);
        TPLoginRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 微信登录获取用户信息
     *
     * @param openId 微信开放id
     * @param token  access token
     */
    private void wxLoginGetUserInfo(String openId, String token) {
        AbstractTPLoginRequestFunc requestFunc = new AbstractTPLoginRequestFunc(context, new IRequestListener<WXUser>() {
            @Override
            public void onRequestSuccess(WXUser data) {
                sendLoginResult(data, data == null ? ThirdPlatformStatusConstant.FAIL : ThirdPlatformStatusConstant.SUCCESS);
            }

            @Override
            public void onRequestFailure(String error) {
                sendLoginResult(null, ThirdPlatformStatusConstant.FAIL);
            }

            @Override
            public void onRequestCancel() {
                sendLoginResult(null, ThirdPlatformStatusConstant.CANCEL);
            }
        }) {
            @Override
            public Observable getObservable(ITPLoginRequest iRequestServer) {
                Map<String, String> map = new HashMap<>(2);
                map.put("access_token", token);
                map.put("openid", openId);
                return iRequestServer.getUseInfo(map);
            }
        };
        requestFunc.setShowProgress(true);
        TPLoginRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 通过广播发送登录结果
     *
     * @param wxUser 微信用户信息
     * @param result ThirdPlatformStatusConstant
     */
    private void sendLoginResult(WXUser wxUser, int result) {
        switch (result) {
            case ThirdPlatformStatusConstant.SUCCESS:
                break;
            case ThirdPlatformStatusConstant.CANCEL:
                ToastUtils.showLong("授权登录已取消");
                break;
            default:
                ToastUtils.showLong("授权登录失败了");
        }
        Intent intent = new Intent(ThirdPlatformBroadcastConstant.LOGIN_FROM_WX);
        if (wxUser != null) {
            intent.putExtra(ThirdPlatformIntentKeyConstant.LOGIN_RESULT, wxUser);
        }
        intent.putExtra(ThirdPlatformIntentKeyConstant.LOGIN_RESULT_TYPE, result);
        context.sendBroadcast(intent);
    }
}
