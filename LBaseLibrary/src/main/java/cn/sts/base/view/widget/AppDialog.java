package cn.sts.base.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sts.base.R;


/**
 * 自定义对话框
 * Created by weilin on 2017/8/23.
 */
public class AppDialog extends Dialog {

    private TextView titleTV;
    private TextView messageTV;

    private LinearLayout btnLL;
    private Button negativeBtn;
    private Button centerBtn;
    private Button positiveBtn;

    private View bottomLineView;
    private View line1View;
    private View line2View;

    private Context context;

    public AppDialog(Context context) {
        super(context, R.style.dialog_app);
        this.context = context;
        initView();
    }

    public TextView getTitleTV() {
        return titleTV;
    }

    public TextView getMessageTV() {
        return messageTV;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().getAttributes().width = dm.widthPixels * 4 / 5;
        this.setCanceledOnTouchOutside(false);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_app, null);
        setContentView(view);

        titleTV = (TextView) view.findViewById(R.id.dialog_title_tv);
        messageTV = (TextView) view.findViewById(R.id.dialog_info_tv);

        btnLL = (LinearLayout) view.findViewById(R.id.btn_ll);
        negativeBtn = (Button) view.findViewById(R.id.dialog_left_btn);
        centerBtn = (Button) view.findViewById(R.id.dialog_center_btn);
        positiveBtn = (Button) view.findViewById(R.id.dialog_right_btn);

        bottomLineView = view.findViewById(R.id.bottom_line_view);
        line1View = view.findViewById(R.id.line_view_1);
        line2View = view.findViewById(R.id.line_view_2);

        negativeBtn.setOnClickListener(cancelListener);
        negativeBtn.setVisibility(View.GONE);
        centerBtn.setVisibility(View.GONE);
        positiveBtn.setVisibility(View.GONE);
    }

    /**
     * 标题
     */
    public AppDialog title(String title) {
        titleTV.setVisibility(View.VISIBLE);
        titleTV.setText(title);
        return this;
    }

    /**
     * 标题
     */
    public AppDialog title(int title) {
        titleTV.setVisibility(View.VISIBLE);
        titleTV.setText(title);
        return this;
    }

    /**
     * 信息
     */
    public AppDialog message(String message) {
        messageTV.setVisibility(View.VISIBLE);
        messageTV.setText(message);
        return this;
    }

    /**
     * 信息
     */
    public AppDialog message(int message) {
        messageTV.setVisibility(View.VISIBLE);
        messageTV.setText(message);
        return this;
    }

    /**
     * 消极按钮
     */
    public AppDialog negativeBtn(String negative, final OnClickListener onClickListener) {
        if (negative != null && negative.length() > 0) {
            negativeBtn.setText(negative);
        }
        negativeBtn.setVisibility(View.VISIBLE);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 消极按钮
     */
    public AppDialog negativeBtn(int negative, final OnClickListener onClickListener) {
        negativeBtn.setText(negative);
        negativeBtn.setVisibility(View.VISIBLE);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 中间按钮
     */
    public AppDialog centerBtn(String center, final OnClickListener onClickListener) {
        if (center != null && center.length() > 0) {
            centerBtn.setText(center);
        }
        centerBtn.setVisibility(View.VISIBLE);
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 中间按钮
     */
    public AppDialog centerBtn(int center, final OnClickListener onClickListener) {
        centerBtn.setText(center);
        centerBtn.setVisibility(View.VISIBLE);
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 积极按钮
     */
    public AppDialog positiveBtn(String positive, final OnClickListener onClickListener) {
        if (positive != null && positive.length() > 0) {
            positiveBtn.setText(positive);
        }
        positiveBtn.setVisibility(View.VISIBLE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 积极按钮
     */
    public AppDialog positiveBtn(int positive, final OnClickListener onClickListener) {
        positiveBtn.setText(positive);
        positiveBtn.setVisibility(View.VISIBLE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    @Override
    public void show() {
        super.show();
        if (titleTV.getText().toString().length() == 0) {
            titleTV.setVisibility(View.GONE);
        } else {
            titleTV.setVisibility(View.VISIBLE);
        }

        if (messageTV.getText().toString().length() == 0) {
            messageTV.setVisibility(View.GONE);
        } else {
            messageTV.setVisibility(View.VISIBLE);
        }

        int negativeState = negativeBtn.getVisibility();
        int positiveState = positiveBtn.getVisibility();
        int centerState = centerBtn.getVisibility();

        btnLL.setVisibility(View.VISIBLE);
        if (negativeState == View.GONE && centerState == View.GONE && positiveState == View.GONE) {
            btnLL.setVisibility(View.GONE);
            bottomLineView.setVisibility(View.GONE);
        } else {
            btnLL.setVisibility(View.VISIBLE);
            bottomLineView.setVisibility(View.VISIBLE);
        }

        if (negativeState == View.VISIBLE && centerState == View.GONE && positiveState == View.GONE
                || negativeState == View.GONE && centerState == View.VISIBLE && positiveState == View.GONE
                || negativeState == View.GONE && centerState == View.GONE && positiveState == View.VISIBLE) {
            line1View.setVisibility(View.GONE);
            line2View.setVisibility(View.GONE);
        } else if (negativeState == View.VISIBLE && centerState == View.VISIBLE && positiveState == View.GONE) {
            line1View.setVisibility(View.VISIBLE);
            line2View.setVisibility(View.GONE);
        } else if (negativeState == View.GONE && centerState == View.VISIBLE && positiveState == View.VISIBLE
                || negativeState == View.VISIBLE && centerState == View.GONE && positiveState == View.VISIBLE) {
            line1View.setVisibility(View.GONE);
            line2View.setVisibility(View.VISIBLE);
        } else {
            line1View.setVisibility(View.VISIBLE);
            line2View.setVisibility(View.VISIBLE);
        }

    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            AppDialog.this.dismiss();
        }
    };

    /**
     * 自定义对话框点击监听
     */
    public interface OnClickListener {

        /**
         * 点击
         */
        void onClick(AppDialog appDialog);
    }

}
