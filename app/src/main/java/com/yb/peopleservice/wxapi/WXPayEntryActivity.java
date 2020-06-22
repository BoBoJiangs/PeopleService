package com.yb.peopleservice.wxapi;


import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.sts.platform.activity.BaseWXPayEntryActivity;

public class WXPayEntryActivity extends BaseWXPayEntryActivity implements IWXAPIEventHandler {


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
        super.onResp(resp);
    }
}