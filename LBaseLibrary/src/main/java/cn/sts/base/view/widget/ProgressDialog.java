package cn.sts.base.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.sts.base.R;


/**
 * 自定义对话框
 * Created by weilin on 2017/8/23.
 */
public class ProgressDialog extends Dialog {

    private ProgressBar progressBar;
    private TextView loadingTV;

    private Context context;

    public ProgressDialog(Context context) {
        super(context, R.style.dialog_progress);
        this.context = context;
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCanceledOnTouchOutside(false);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_progress, null);
        setContentView(view);

        progressBar = view.findViewById(R.id.progressBar);
        loadingTV = view.findViewById(R.id.loadingTV);
        loadingTV.setVisibility(View.GONE);
    }

    /**
     * 提示
     */
    public ProgressDialog loadingText(int title) {
        loadingTV.setVisibility(View.VISIBLE);
        loadingTV.setText(title);
        return this;
    }

    /**
     * 提示
     */
    public ProgressDialog loadingText(String title) {
        loadingTV.setVisibility(View.VISIBLE);
        loadingTV.setText(title);
        return this;
    }

    /**
     * 设置提示文本颜色
     */
    public ProgressDialog setLoadingTextColor(int color) {
        loadingTV.setVisibility(View.VISIBLE);
        loadingTV.setTextColor(color);
        return this;
    }

    /**
     * 设置是否可以取消
     */
    public ProgressDialog cancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    /**
     * 取消回调
     */
    public ProgressDialog cancelListener(final OnCancelListener onCancelListener) {
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onCancelListener.onCancel(ProgressDialog.this);
            }
        });
        return this;
    }


    @Override
    public void show() {
        super.show();
    }


    /**
     * 自定义取消监听
     */
    public interface OnCancelListener {

        /**
         * 取消
         */
        void onCancel(ProgressDialog progressDialog);
    }
}
