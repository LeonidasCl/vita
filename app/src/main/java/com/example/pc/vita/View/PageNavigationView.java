package com.example.pc.vita.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.pc.vita.R;

/**
 * 导航按钮
 */
public class PageNavigationView extends TouchMoveView {

	public PageNavigationView(Context context) {

		super(context);
	}

	public PageNavigationView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public PageNavigationView(Context context, AttributeSet attrs,
			int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	public synchronized void onShowAnimation(float step) {

		if(isShowFinish()) {
			return;
		}
		updateMarginTop(-getShowMoveStep(step));
	}

	public synchronized void onHideAnimation(float step) {

		if(isHideFinish()) {
			return;
		}
		updateMarginTop(getHideMoveStep(step));
	}

	@Override
	public synchronized boolean isShowFinish() {

		return getMarginTop() <= mShowStopMarginTop ? true : false;
	}

	@Override
	public synchronized boolean isHideFinish() {
		return getMarginTop() >= mHideStopMarginTop ? true : false;
	}

	public void init(){


	}

/*	@Override
    public boolean onTouchEvent(MotionEvent event) {
       super.onTouchEvent(event);
        return false;
    }*/

}
