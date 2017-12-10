package com.mvp.lt.flyset.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mvp.lt.flyset.R;

/**
 * 可自定义是否左右滑动的ViewPager
 *
 * @author LiuTao
 */
public class NoHorizontalScrollPager extends ViewPager {
	/**
	 * 	默认不能左右滑动
	 */

	public boolean isScroll=false;

	public boolean isScroll() {
		return isScroll;
	}

	public void setScroll(boolean scroll) {
		isScroll = scroll;
	}

	public NoHorizontalScrollPager(Context context) {
		super(context);
	}

	public NoHorizontalScrollPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.NoHorizontalScrollPager);
		isScroll=array.getBoolean(R.styleable.NoHorizontalScrollPager_isScroll,false);
		array.recycle();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isScroll){
			return super.onTouchEvent(ev);
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isScroll){
			return super.onInterceptTouchEvent(ev);
		}
		return false;

	}

}
