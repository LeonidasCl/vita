package com.example.pc.vita.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.pc.vita.Adapter.MyPagerAdapter;
import com.example.pc.vita.R;

import java.util.ArrayList;
import java.util.List;


//引导界面
//黄鑫晨 2016.03.16
//licl 优化了销毁动画-2016.06.07
public class GuideActivity extends AppCompatActivity {

    View view1, view2, view3,view4;//4张引导界面
    int screenwidth;//屏幕宽度
    Button btn_login,btn_regist;//登陆，注册按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPreference();
        initialSettings();//初始化初始设置
        setContentView(R.layout.splash);
        initialViews();//初始化界面
        initialWidgets();//初始化控件
    }

    private void checkPreference() {

                SharedPreferences settings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = settings.edit();
                int time = settings.getInt("time", 0);
                if (time==0){
                    editor.putInt("time",++time);
                    editor.commit();
                }else{

                    finish();
                    //要关掉销毁Activity的动画，不然很丑
                    overridePendingTransition(0,0);
                    Intent intent=new Intent("android.intent.action.SPLASHACTIVITY");

                    startActivity(intent);
                }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //初始化初始设置，如隐藏标题栏
    public void initialSettings(){
        //android.support.v7.app.ActionBar actionBar =getSupportActionBar();//hide action Bar
        //actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        View main ;
        main = getLayoutInflater().from(this).inflate(R.layout.splash, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);  //隐藏底部虚拟按键
        WindowManager wm = this.getWindowManager();
        screenwidth = wm.getDefaultDisplay().getWidth();//得到屏幕尺寸
    }
    //初始化控件
    public void initialWidgets(){
        View v = view4.findViewById(R.id.btn_login);//将按钮设置为透明
        btn_login= (Button)v;//注意，因为button在View4中，所以
        v.getBackground().setAlpha(60);//0~255透明度值
        //一定要在前面声明view4!!!不然会报空指针错
        v = view4.findViewById(R.id.btn_regist);//将按钮设置为透明
        btn_regist= (Button) v;
        v.getBackground().setAlpha(60);//0~255透明度值
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
              finish();
                Intent intent = new Intent("android.intent.action.LOGINACTIVITY");
     ///////***********2016-3-8 added by licl****************////
     /*********/intent.putExtra("father","GuideActivity");/////////////

                startActivity(intent);
            }
        });
    }
    //初始化几张view
    public void initialViews() {

        ViewPager viewPager_splash;//viewpager
        List<View> viewList;//把需要滑动的页卡添加到这个list中
        viewPager_splash = (ViewPager) findViewById(R.id.viewpager_splash);
        LayoutInflater lf = getLayoutInflater().from(getApplicationContext());//作用类似于findViewById()
        view1 = lf.inflate(R.layout.layout0, null);
        view2 = lf.inflate(R.layout.layout2, null);
        view3 = lf.inflate(R.layout.layout3, null);
        view4 =  lf.inflate(R.layout.layout4,null);
        viewList = new ArrayList<View>();//将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList);
        viewPager_splash.setAdapter(myPagerAdapter);//设置适配器
        //PagerAdapter主要是viewpager的适配器，
        //而viewPager则也是在android.support.v4扩展包中新添加的一个强大的控件，可以实现控件的滑动效果

    }
}



