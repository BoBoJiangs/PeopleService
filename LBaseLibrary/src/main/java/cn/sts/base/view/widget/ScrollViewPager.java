package cn.sts.base.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
     * 子View能滑动时是否优先滑动子View的视图
     */
    private boolean isChildScroll = true;

    /**
     * 子View能滑动时是否优先滑动子View的视图
     *
     * @param isChildScroll 是否
     */
    public void setChildScroll(boolean isChildScroll) {
        this.isChildScroll = isChildScroll;
    }

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
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;

        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;

        }
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (isChildScroll) {
            return false;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
