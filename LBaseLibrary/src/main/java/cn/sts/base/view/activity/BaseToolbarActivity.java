package cn.sts.base.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import cn.sts.base.R;

/**
 * 基础类
 *
 * @author weilin
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    /**
     * 工具栏
     */
    public Toolbar toolbar;
    /**
     * 左边按钮
     */
    public ImageView leftIV;
    /**
     * 右边按钮
     */
    public ImageView rightIV;
    /**
     * 右边文字
     */
    public TextView rightTV;
    /**
     * 标题名称
     */
    public TextView titleTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolView();
    }

    /**
     * 设置标题名称
     */
    public abstract String getTitleName();

    /**
     * 初始化工具view
     */
    public void initToolView() {
        toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            leftIV = findViewById(R.id.leftIV);
            titleTV = findViewById(R.id.titleTV);
            rightIV = findViewById(R.id.rightIV);
            rightTV = findViewById(R.id.rightTV);

            rightIV.setVisibility(View.GONE);
            rightTV.setVisibility(View.GONE);
            titleTV.setText(getTitleName());

            leftIV.setOnClickListener(onClickListener);
            rightIV.setOnClickListener(onClickListener);
            rightTV.setOnClickListener(onClickListener);
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.leftIV) {
                clickLeftListener();
            } else if (id == R.id.rightIV || id == R.id.rightTV) {
                clickRightListener();
            }
        }
    };

    /**
     * 标题栏左边按钮点击事件
     */
    public void clickLeftListener() {
        finish();
    }

    /**
     * 标题栏右边按钮点击事件，需要子类重写此方法
     */
    public void clickRightListener() {

    }

}
