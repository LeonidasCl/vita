package com.example.pc.vita.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.vita.Adapter.MyPagerAdapter;
import com.example.pc.vita.R;

import java.util.ArrayList;
import java.util.List;

//TODO 改成自己的图片界面

public class HottestFragment extends Fragment {
	//hxc
     private GridView gridview;
	private ViewPager viewPager;
	private ImageView imageView;

	private List<View> views;
	private int offset = 0;
	private int currIndex = 0;
	private int bmpW;
	private View view1, view2, view3;
	private View mainView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getLayoutInflater(savedInstanceState);
		mainView = inflater.inflate(R.layout.weibolayout,null);
		InitTextView();
		viewPager = (ViewPager)mainView.findViewById(R.id.vPager);
		views = new ArrayList<View>();
		view1 = inflater.inflate(R.layout.frame1, null);
		view2 = inflater.inflate(R.layout.frame2, null);
		view3 = inflater.inflate(R.layout.frame3, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		viewPager.setAdapter(new MyPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		//  InitImageView();

		Toast.makeText(this.getActivity(), "最热门Fragment创建成功", Toast.LENGTH_SHORT).show();
	}

	//hxc
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initialSettings();

		return  inflater.inflate(R.layout.weibolayout, container, false);
	}
	private void InitTextView() {
		TextView textView1, textView2, textView3;//��������
		textView1 = (TextView)mainView.findViewById(R.id.text1);
		textView2 = (TextView) mainView.findViewById(R.id.text2);
		textView3 = (TextView)mainView. findViewById(R.id.text3);

		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
	}
	public void initialSettings(){
//		android.support.v7.app.ActionBar actionBar =getSupportActionBar();//hide action Bar
//		actionBar.hide();
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȥ����Ϣ��
	}

	private void InitViewPager() {

		// viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
@Override
public void onActivityCreated(Bundle savedInstanceState) {
	 setView();
	 setListener();
	super.onActivityCreated(savedInstanceState);
}



	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

		int one = offset * 2 + bmpW;//
		int two = one * 2;//

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);
			//������API
			currIndex=arg0;
			animation.setFillAfter(true);//
			animation.setDuration(300);
			imageView.startAnimation(animation);
//			Toast.makeText(this,"��ѡ����"+viewPager.getCurrentItem()+"ҳ��",Toast.LENGTH_SHORT).
//					show();
		}
	}
private void setListener() {
}
private void setView() {
	 gridview=(GridView)getView().findViewById(R.id.GridViewhottest);
}
	private class MyOnClickListener implements View.OnClickListener {
		private int index=0;
		public MyOnClickListener(int i){
			index=i;
		}
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	}
}
