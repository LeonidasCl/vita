package com.example.pc.vita.View;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 页面头部View
 *
 */
public class PageHeadView extends MoveView {

	public PageHeadView(Context context) {

		super(context);
	}

	public PageHeadView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public PageHeadView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	public synchronized void onShowAnimation(float step) {

		if(isShowFinish()) {
			return;
		}
		updateMarginTop(getShowMoveStep(step));
	}

	public synchronized void onHideAnimation(float step) {

		if(isHideFinish()) {
			return;
		}
		updateMarginTop(-getHideMoveStep(step));
	}
}
