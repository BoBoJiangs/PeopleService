package cn.sts.base.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.Logs;

/**
 * Fragment基类
 */
public abstract class BaseFragment<P extends AbstractPresenter> extends Fragment {

    private static final String TAG = "BaseFragment";

    public LayoutInflater inflater;
    public View view;
    private P presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Logs.i(TAG, "onCreateView");

        if (view == null) {
            Logs.i(TAG, "onCreateView view == null");
            this.inflater = inflater;
            view = inflater.inflate(viewResID(), null);
            ButterKnife.bind(this, view);
            initView();
            initData();
        }
        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }

        if (savedInstanceState != null) {
            //恢复之前保存的数据
            presenter.onCreate(savedInstanceState);
        }

        if (presenter == null) {
            Logs.w(TAG, getClass().getName() + "------->Presenter为null，确定当前Fragment是否需要调用API或者逻辑处理？");
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.i(TAG, "onDestroyView");
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (presenter != null) {
            presenter.unbind();
        }
    }

    /**
     * view 的资源id
     */
    public abstract int viewResID();

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
