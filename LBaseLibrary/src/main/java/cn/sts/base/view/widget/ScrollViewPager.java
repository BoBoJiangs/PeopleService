package cn.sts.base.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 自定ViewPager
 * Created by weilin on 2018/3/7.
 */

public class ScrollViewPager extends ViewPager {
    /**
     * 是否可以进行滑动
     */
    private boolean isScroll = true;

    /**
     * 是否可以进行滑动
     *
     * @param isScroll 是否
     */
    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll;
    }
}
