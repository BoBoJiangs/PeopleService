package com.yb.peopleservice.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.blankj.utilcode.util.PermissionUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.AppDialog;

/**
 * 欢迎界面
 * Created by sts on 2018/5/16.
 *
 * @author daichao
 */

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();

    @BindView(R.id.welcomeIV)
    ImageView welcomeIV;

    /**
     * 开始进入的时间
     */
    private long startTime;

    /**
     * 默认总耗时
     */
    private static final long TOTAL_MILLISECOND = 2000;

    @Override
    public int contentViewResID() {
        return R.layout.e_activity_welcome;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        startTime = System.currentTimeMillis();
        requestAllPermission();
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //将window的背景图设置为空
//        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取权限
     */
    private void requestAllPermission() {

        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.INSTALL_PACKAGES,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
        };

        if (PermissionUtils.isGranted(perms)) {
            toLogin();
        } else {
            PermissionUtils
                    .permission(perms)
                    .rationale(new PermissionUtils.OnRationaleListener() {
                        @Override
                        public void rationale(ShouldRequest shouldRequest) {
                            //被拒绝后重新获取
                            shouldRequest.again(true);
                        }
                    })
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            //授权成功
                            toLogin();
                        }

                        @Override
                        public void onDenied(final List<String> permissionsDeniedForever, final List<String> permissionsDenied) {

                            AppDialog appDialog = new AppDialog(WelcomeActivity.this)
                                    .title(R.string.permissions_required)
                                    .message(R.string.permissions_required_info)
                                    .positiveBtn(R.string.open, new AppDialog.OnClickListener() {
                                        @Override
                                        public void onClick(AppDialog appDialog) {
                                            appDialog.dismiss();
                                            if (!permissionsDeniedForever.isEmpty()) {
                                                //永久拒绝，到设置界面
                                                PermissionUtils.launchAppDetailsSettings();
                                            } else if (!permissionsDenied.isEmpty()) {
                                                //暂时拒绝，重新请求
                                                requestAllPermission();
                                            }
                                        }
                                    })
                                    .negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
                                        @Override
                                        public void onClick(AppDialog appDialog) {
                                            appDialog.dismiss();
                                            finish();
                                        }
                                    });
                            appDialog.setCancelable(false);
                            appDialog.show();
                        }
                    })
                    .request();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestAllPermission();
    }

    /**
     * 登录
     */
    private void toLogin() {
        welcomeIV.postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, TOTAL_MILLISECOND );
    }


    /**
     * 跳转到登录的Activity
     */
    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
