/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.yb.peopleservice.wxapi;

import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.yb.peopleservice.model.presenter.login.WeChatLoginPresenter;

import cn.sts.base.util.Logs;
import cn.sts.platform.activity.BaseWXEntryActivity;
import cn.sts.platform.presenter.login.ThirdPlatformLoginPresenter;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends BaseWXEntryActivity {



	private static String TAG = "BaseWXEntryActivity";

	private IWXAPI iwxapi;


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	public void onReq(BaseReq req) {
		super.onReq(req);
	}

	@Override
	public void onResp(BaseResp resp) {
		Logs.i(TAG, "--------微信回调 errCode=" + resp.errCode + " type=" + resp.getType());
		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			new WeChatLoginPresenter(this).wxLoginResp((SendAuth.Resp) resp);
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
