package com.example.pc.vita.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import com.example.pc.vita.R;
import com.example.pc.vita.View.Custom.YuePaiItem;
import java.util.ArrayList;
import java.util.List;

/**
 * 内容视图
 */
public class ContentView extends TouchMoveView {

	List<ListItem> list = new ArrayList<ListItem>();
	ListView listView;
    Context context;
    View parent;

	public ContentView(Context context) {
		super(context);
        this.context=context;
        parent=this;
	}

	public ContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
        this.context=context;
        parent=this;
	}

	public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
        this.context=context;
        //parent=this;
	}

    //这个init必须由父view在inflate完成后调用
	public void init() {
        YuePaiItem apple = new YuePaiItem("Apple");
        list.add(apple);
        YuePaiItem banana = new YuePaiItem("banana");
        list.add(banana);
        YuePaiItem orange = new YuePaiItem("orange");
        list.add(orange);
        list.add(orange);
        list.add(banana);list.add(banana);list.add(apple);list.add(orange);list.add(banana);list.add(orange);
        list.add(apple);list.add(orange);list.add(apple);list.add(orange);list.add(banana);list.add(orange);
        list.add(apple);list.add(banana);list.add(orange);list.add(apple);list.add(orange);list.add(apple);
        list.add(banana);list.add(banana);list.add(apple);list.add(orange);list.add(apple);list.add(orange);


        listView = (ListView)findViewById(R.id.yuepai_list_view);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 只好在这里显示调用父view的动画，因为listView的事件往上冒泡时不知道传到哪里去了
                parent.onTouchEvent(event);
                return false;
            }
        });


        ListFactory.createList(context,R.layout.yuepai_item,list,listView);
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

	/**
	 * 获取当前视图在展示过程中已经滑离初始化位置的距离
	 * @return
	 */
	public int getShowOffset() {

		return mHideStopMarginTop - getMarginTop();
	}


	/**
	 * 获取当前视图在恢复过程中已经滑离展示停止位置的距离
	 * @return
	 */
	public int getHideOffset() {
		return getMarginTop() - mShowStopMarginTop;
	}

   /* @Override
    //在顶部导航按钮还在的时候要拦截触摸事件让动画显示
    public boolean onInterceptTouchEvent(MotionEvent ev){
        //return  super.onInterceptTouchEvent(ev);
        return false;
    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        boolean ret=super.dispatchTouchEvent(ev);
        return ret;
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
       super.onTouchEvent(event);
        return false;
    }*/

}
