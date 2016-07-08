package com.example.pc.vita.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pc.vita.Fragment.FindFragment;
import com.example.pc.vita.Data.Model.UserModel;
import com.example.pc.vita.Fragment.MainFragment;
import com.example.pc.vita.Fragment.UserFragment;
import com.example.pc.vita.Fragment.YuePaiFragment;
import com.example.pc.vita.Fragment.YuePaiNavigation;
import com.example.pc.vita.R;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /*
    TODO 把两个占位用的空Fragment改成写好的最新和最热门图片页面
    TODO 图片页面支持下拉刷新，并能点开大图
    TODO 网络模型已全部写好 参看network包
    */

    private boolean isLogin=false;
    private UserModel user;

    //管理Fragment
    private FragmentManager fragmentMgr = this.getSupportFragmentManager();
    private FragmentTransaction fragmentTrs;

    //四个功能项Fragment
    private MainFragment mainFragment;
    private YuePaiNavigation yuePaiFragment;
    private FindFragment findFragment;
    private UserFragment userFragment;
    private Toolbar toolbar;
    //Fragment切换按钮
    private ImageButton btn_main;
    private ImageButton btn_find;
    private ImageButton btn_yuepai;
    private ImageButton btn_user;
    private android.support.v7.app.ActionBar actbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.m_toolbar);
        setSupportActionBar(toolbar);
        Window window = getWindow();
        //4.4版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //5.0版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        actbar=getSupportActionBar();
        initLocalData();
        initNetworkData();
        try {
            isLogin=manageLogin();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initView();

        fragmentTrs=fragmentMgr.beginTransaction();
        btn_yuepai.setSelected(true);
        toYuePai();
        fragmentTrs.commit();


    }

    private void initNetworkData() {
    }

    private void initLocalData() {
        user=new UserModel();
    }

    private boolean manageLogin() throws JSONException {
     /*   String name=user.getUsername();
        if(name==null)
            return false;*/
        return true;
    }

    private void initView() {

        btn_main=(ImageButton)findViewById(R.id.button_main);
        btn_find=(ImageButton)findViewById(R.id.button_find);
        btn_user=(ImageButton)findViewById(R.id.button_user);
        btn_yuepai=(ImageButton)findViewById(R.id.button_yuepai);

        btn_main.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        btn_user.setOnClickListener(this);
        btn_yuepai.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        fragmentTrs=fragmentMgr.beginTransaction();
        setSelected();
        switch (v.getId()) {
            case R.id.button_main:
                btn_main.setSelected(true);
                toMain();
                break;
            case R.id.button_find:
                btn_find.setSelected(true);
                toFind();
                break;
            case R.id.button_yuepai:
                btn_yuepai.setSelected(true);
                toYuePai();
                break;
            case R.id.button_user:
                btn_user.setSelected(true);
                toUser();
                break;
        }
        fragmentTrs.commit();

    }

    private void toMain(){
        actbar.show();
        if(mainFragment == null){
            mainFragment = new MainFragment();
            fragmentTrs.add(R.id.fl_content, mainFragment);
        }else{
            fragmentTrs.show(mainFragment);
        }
    }

    private void toFind(){
        actbar.show();
        if(findFragment == null){
            findFragment = new FindFragment();
            fragmentTrs.add(R.id.fl_content, findFragment);
        }else{
            btn_find.setSelected(true);
            fragmentTrs.show(findFragment);
        }
    }

    private void toYuePai(){
        actbar.hide();
        if(yuePaiFragment == null){
            yuePaiFragment = new YuePaiNavigation();
            fragmentTrs.add(R.id.fl_content, yuePaiFragment);
        }else{
            btn_yuepai.setSelected(true);
            fragmentTrs.show(yuePaiFragment);
        }
    }

    private void toUser(){
        actbar.show();
        if(userFragment == null){
            userFragment = new UserFragment();
            fragmentTrs.add(R.id.fl_content, userFragment);
        }else{
            fragmentTrs.show(userFragment);
        }
    }

    private void setSelected(){
        btn_main.setSelected(false);
        btn_find.setSelected(false);
        btn_yuepai.setSelected(false);
        btn_user.setSelected(false);
        if(mainFragment != null){
            //
            fragmentTrs.hide(mainFragment);
        }
        if(findFragment != null){
            fragmentTrs.hide(findFragment);
        }
        if(userFragment != null){
            //
            fragmentTrs.hide(userFragment);
        }
        if(yuePaiFragment != null){
            fragmentTrs.hide(yuePaiFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //重载这个函数以识别requestCode并进行相应操作
        if(requestCode == 1) {
            finish();//结束父activity自身
            //重新创建来刷新UI
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
