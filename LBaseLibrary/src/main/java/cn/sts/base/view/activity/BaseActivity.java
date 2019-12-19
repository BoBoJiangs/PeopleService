package cn.sts.base.view.activity;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewResID());
        ButterKnife.bind(this);

        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (savedInstanceState != null) {
            //恢复之前保存的数据
            presenter.onCreate(savedInstanceState);
        }

        if (presenter == null) {
            Logs.w(TAG, getClass().getName() + "------->Presenter为null，确定当前Activity是否需要调用API或者逻辑处理？");
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
