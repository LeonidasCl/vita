package com.example.pc.vita.View;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.example.pc.vita.Data.Model.ActivityItem;
import com.example.pc.vita.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容视图
 */
public class ContentView extends TouchMoveView {

	//List<ListItem> list = new ArrayList<ListItem>();
	//ListView listView;
    Context context;
    View parent;

    PtrClassicFrameLayout ptrClassicFrameLayout;
    RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<String>();
    private RecyclerAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    int page = 0;

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
       /* ActivityItem apple = new ActivityItem("Apple");
        list.add(apple);
        ActivityItem banana = new ActivityItem("banana");
        list.add(banana);
        ActivityItem orange = new ActivityItem("orange");
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
        ListFactory.createList(context,R.layout.yuepai_item,list,listView);*/

        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.huodong_view_frame);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.huodong_recycler_view);

        adapter = new RecyclerAdapter(context, mData);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        mData.clear();
                        for (int i = 0; i < 17; i++) {
                            mData.add(new String("  RecyclerView item  -" + i));
                        }
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 2000);
            }
        });

        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(new String("  RecyclerView item  - add " + page));
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        //Toast.makeText(RecyclerViewActivity.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                parent.onTouchEvent(event);
                return false;
            }
        });

      /*  mRecyclerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

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
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> datas;
        private LayoutInflater inflater;

        public RecyclerAdapter(Context context, List<String> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            holder.itemTv.setText(datas.get(position));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
            View view = inflater.inflate(R.layout.huodong_item_layout, null);
            return new ChildViewHolder(view);
        }

    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTv;

        public ChildViewHolder(View view) {
            super(view);
            itemTv = (TextView) view;
        }

    }
}
