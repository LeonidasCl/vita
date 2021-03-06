package com.example.pc.vita.Activity;

import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.pc.vita.Fragment.FragmentCreateYuePaiA;
import com.example.pc.vita.Fragment.FragmentCreateYuePaiB;
import com.example.pc.vita.R;

/**
 *
 * Created by pc on 2016/6/28.
 */
public class CreateYuePaiActivity extends AppCompatActivity implements
        ActionBar.TabListener {
    private Fragment fragment;
    private int position=-1;
    private static final String SELECTED_ITEM = "selected_item";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_yuepai);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // 设置ActionBar的导航方式：Tab导航
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // 依次添加三个Tab页，并为三个Tab标签添加事件监听器
        actionBar.addTab(actionBar.newTab().setText("约模特").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("约摄影师").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("发起活动").setTabListener(this));
        actionBar.setTitle("发起约拍/创建活动");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        if (savedInstanceState.containsKey(SELECTED_ITEM))
        {
            // 选中前面保存的索引对应的Fragment页
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(SELECTED_ITEM));
        }
    }
    //监听返回键，有弹出层时关闭弹出层，否则停止activity
    @Override
    public void onBackPressed() {
        int state;
        switch (position){
            case 0:
                FragmentCreateYuePaiA fraga=(FragmentCreateYuePaiA)fragment;
                /*state=fraga.getEdit_photo_fullscreen_layout().getVisibility();
                if (state== View.GONE)
                    finish();
                else
                    fraga.hideEditPhotoLayout();*/
                break;
            case 1:
                FragmentCreateYuePaiB fragb=(FragmentCreateYuePaiB)fragment;
                state=fragb.getEdit_photo_fullscreen_layout().getVisibility();
                if (state== View.GONE)
                    finish();
                else
                    fragb.hideEditPhotoLayout();
                break;
            case 2:
                FragmentCreateYuePaiB fragc=(FragmentCreateYuePaiB)fragment;
                state=fragc.getEdit_photo_fullscreen_layout().getVisibility();
                if (state== View.GONE)
                    finish();
                else
                    fragc.hideEditPhotoLayout();
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        // 将当前选中的Fragment页的索引保存到Bundle中
        outState.putInt(SELECTED_ITEM, getSupportActionBar().getSelectedNavigationIndex());
    }

    // 当指定Tab被选中时激发该方法
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragment=null;
        position=tab.getPosition();
        switch (position){
            case 0:
                fragment = new FragmentCreateYuePaiA();
                break;
            case 1:
                fragment=new FragmentCreateYuePaiB();
                break;
            case 2:
                fragment=new FragmentCreateYuePaiB();
                break;
        }
        // 创建一个Bundle对象，用于向Fragment传入参数
        //Bundle args = new Bundle();
        // args.putInt(FragmentCreateYuePaiA.ARG_SECTION_NUMBER, tab.getPosition() + 1);
        // 向fragment传入参数
        //fragment.setArguments(args);
        // 获取FragmentTransaction对象
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // 使用fragment代替该Activity中的container组件
        ft.replace(R.id.container, fragment);
        // 提交事务
        ft.commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}
