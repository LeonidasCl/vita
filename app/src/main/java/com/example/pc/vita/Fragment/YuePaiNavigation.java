package com.example.pc.vita.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.vita.APP;
import com.example.pc.vita.Data.Model.PictureModel;
import com.example.pc.vita.Network.MyInterface;
import com.example.pc.vita.Network.NetRequest;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.CommonUrl;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;

/**
 *
 * Created by pc on 2016/7/7.
 */
public class YuePaiNavigation extends android.support.v4.app.Fragment implements  MyInterface.NetRequestIterface{

    private BGABanner mSideZoomBanner;
    private NetRequest requestFragment;
    private int naviContent=4;
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTrs;
    private activityFragment yuepaiFragment;
    private YuePaiFragmentA yuePaiFragmentA;
    private ArrayList tips=new ArrayList();
    private TextView yuepainavi;
    private Toolbar toolbar;
    public static int yuepaiStatus=0;//设置当前的约拍子页面种类：0-未选择 1-约模特 2-约摄影师 3-活动 4-榜单


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_yuepai, container, false);

        mSideZoomBanner = (BGABanner) view.findViewById(R.id.banner_main_zoom);
        yuepainavi=(TextView)view.findViewById(R.id.yuepainavi);

        setListener();
        loadData();
        mSideZoomBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int x=mSideZoomBanner.getCurrentItem();
                if (x==0){
                    yuepainavi.setText("约模特");
                }
                if (x==1){
                    yuepainavi.setText("约摄影");
                }
                if (x==2){
                    yuepainavi.setText("活动");
                }
                if (x==3){
                    yuepainavi.setText("榜单");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void loadData() {
        fragmentManager=getFragmentManager();
        tips.clear();
        tips.add("我要拍摄，我想约模特");
        tips.add("我想被拍，我要约摄影师");
        tips.add("发起或加入一个线下活动");
        tips.add("查看榜单，围观大牛");
        mSideZoomBanner.setViewsAndTips(getViews(naviContent), tips);
        loadData(naviContent);

    }

    private void loadData(int i) {
        requestFragment = new NetRequest(this, APP.context);
        Map map = new HashMap();
        String count=String.valueOf(i);
        map.put("userID", "15652009705");
        map.put("usetToken", "123456");
        map.put("requestNum", "100XX");
        map.put("pictureCount", count);
        //requestFragment.httpRequest(map, CommonUrl.getYuepaiNavigateurl);
    }


    private void setListener() {
        mSideZoomBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onClickBannerItem(int position) {

                if (position==0){
                    if (yuePaiFragmentA == null) {
                        yuePaiFragmentA = new YuePaiFragmentA();
                    }

                    fragmentTrs = fragmentManager.beginTransaction();
                    fragmentTrs.replace(R.id.fl_content, yuePaiFragmentA);
                    fragmentTrs.addToBackStack(null);
                    fragmentTrs.commit();
                }

                if (position==1){

                }

               if(position==2) {//如果是活动界面
                if (yuepaiFragment == null) {
                    yuepaiFragment = new activityFragment();
                }

                fragmentTrs = fragmentManager.beginTransaction();
                fragmentTrs.replace(R.id.fl_content, yuepaiFragment);
                fragmentTrs.addToBackStack(null);
                fragmentTrs.commit();
               }

                if (position==3){//榜单界面

                }
            }
        });
    }


    @Override
    public void requestFinish(String result, String requestUrl) {
        if (requestUrl.equals(CommonUrl.getYuepaiNavigateurl)) {
            Gson gson = new Gson();
            PictureModel picStatusInfoObject = gson.fromJson(result, PictureModel.class);
            String errorCode = picStatusInfoObject.getErrorCode();
            if (errorCode.equals("0")){
                for (int i=0;i<naviContent;i++){
                    String currentCount="pic"+String.valueOf(i);
                    String picUrl=picStatusInfoObject.getPicUrl(currentCount);
                    Glide.with(YuePaiNavigation.this)
                            .load(picUrl)
                            .placeholder(R.drawable.holder)
                            .error(R.drawable.holder)
                            .into(mSideZoomBanner.getItemImageView(i));
                }
            }
        }
    }

    @Override
    public void exception(IOException e, String requestUrl) {
        Toast.makeText(APP.getInstance(), "获取网络数据失败！请检查网络连接！", Toast.LENGTH_SHORT).show();
    }

    private List<ImageView> getViews(int count) {
        List<ImageView> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add(BGABannerUtil.getItemImageView(getContext(), R.drawable.holder));
        }
        return views;
    }
}
