package cn.sts.base.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.trello.rxlifecycle3.components.RxActivity;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import cn.sts.base.app.AppManager;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.Logs;

/**
 * 基础类
 *
 * @author weilin
 */
public abstract class BaseActivity<P extends AbstractPresenter> extends RxAppCompatActivity {

    private static final String TAG = "BaseActivity";
    private P presenter;
    /**
     * 等待对话框
     */
    private ProgressDialog progDialog;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewResID());
        ButterKnife.bind(this);
        context = this;


        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (savedInstanceState != null && presenter != null) {
            //恢复之前保存的数据
            presenter.onCreate(savedInstanceState);
        }

        if (presenter == null) {
            Logs.w(TAG, getClass().getName() + "------->Presenter为null，确定当前Activity是否需要调用API或者逻辑处理？");
        }


    }

    public void showProgressDialog(){
        showProgressDialog("加载中");
    }

    /**
     * 显示进度框
     */
    public void showProgressDialog(String msg) {
        if (progDialog == null) {
            progDialog = new ProgressDialog(this);
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage(msg);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    public void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        //解除绑定
        if (presenter != null) {
            presenter.unbind();
        }
        dissmissProgressDialog();
    }

    /**
     * 设置Activity布局文件
     *
     * @return 布局文件id
     */
    protected abstract int contentViewResID();


    /**
     * 初始化界面UI等
     */
    protected abstract void initView();

    /**
     * 初始化设置数据等
     */
    protected abstract void initData();

    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取Presenter
     *
     * @return 返回Presenter
     */
    public P getPresenter() {
        return presenter;
    }

}
