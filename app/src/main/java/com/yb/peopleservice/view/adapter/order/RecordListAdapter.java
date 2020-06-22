package com.yb.peopleservice.view.adapter.order;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piterwilson.audio.MP3RadioStreamDelegate;
import com.piterwilson.audio.MP3RadioStreamPlayer;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.PayInfoBean;
import com.yb.peopleservice.model.database.bean.RecordBean;
import com.yb.peopleservice.model.server.BaseRequestServer;

import java.io.IOException;

import jiguang.chat.utils.ToastUtil;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/18 21:11
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class RecordListAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> implements
        MP3RadioStreamDelegate {
    MP3RadioStreamPlayer player;
    private int playIndex = -1;//记录播放的指针
    private int clickIndex;
    private Activity context;

    public RecordListAdapter(Activity context) {
        super(R.layout.adapter_record);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecordBean item) {
        helper.setText(R.id.titleTV, "音频" + (helper.getAdapterPosition() + 1));
        helper.setText(R.id.timeTV, item.getTimestamp());
        helper.getView(R.id.rootLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickIndex = helper.getAdapterPosition();
                if (playIndex == helper.getAdapterPosition()) {
                    stop();
                } else {
                    play(BaseRequestServer.getFileUrl(true) + item.getFileUri());
                }
            }
        });
        if (playIndex == helper.getAdapterPosition()) {
            helper.setBackgroundRes(R.id.imageView, R.mipmap.zanting);
        } else {
            helper.setBackgroundRes(R.id.imageView, R.mipmap.bofang);
        }
    }

    private void play(String uri) {
        stop();
        player = new MP3RadioStreamPlayer();
        player.setUrlString(uri);
        player.setDelegate(this);

        try {
            player.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stop() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onRadioPlayerPlaybackStarted(MP3RadioStreamPlayer player) {
        LogUtils.e("onRadioPlayerPlaybackStarted");
        playIndex = clickIndex;
        runOnUiThread();
    }

    @Override
    public void onRadioPlayerStopped(MP3RadioStreamPlayer player) {
        LogUtils.e("onRadioPlayerStopped");
        playIndex = -1;
        runOnUiThread();
    }

    @Override
    public void onRadioPlayerError(MP3RadioStreamPlayer player) {
        LogUtils.e("onRadioPlayerError");
        ToastUtils.showLong("播放异常");
        playIndex = -1;
        runOnUiThread();
    }

    @Override
    public void onRadioPlayerBuffering(MP3RadioStreamPlayer player) {
        LogUtils.e("onRadioPlayerBuffering");
    }

    private void runOnUiThread(){
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

}
