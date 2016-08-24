package com.example.pc.vita.Fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.vita.APP;
import com.example.pc.vita.Activity.CreateYuePaiActivity;
import com.example.pc.vita.Data.Model.PictureModel;
import com.example.pc.vita.Network.NetworkCallbackInterface;
import com.example.pc.vita.Network.NetRequest;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.CommonUrl;
import com.google.gson.Gson;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;

/**
 * 约拍主界面（一级）
 * Created by pc on 2016/7/7.
 */
public class YuePaiFragment extends android.support.v4.app.Fragment implements  NetworkCallbackInterface.NetRequestIterface{

    private BGABanner mSideZoomBanner;
    private NetRequest requestFragment;
    private int naviContent=4;
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTrs;
    private YuePaiFragmentC yuepaiFragment;
    private YuePaiFragmentAB yuePaiFragmentAB;
    private ArrayList tips=new ArrayList();
    private ImageButton btn_yuepai_a;
    private ImageButton btn_yuepai_b;
    private ImageButton btn_yuepai_c;
    private ImageButton btn_create_yuepai;
    private float mLastTouchY;
    private float mDelY;
    private YuePaiFragmentD rankFrag;

    @Override
    public void onResume() {
        //如果之前动过还原动画flag
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yuepai, container, false);

        rankFrag =new YuePaiFragmentD();

        mSideZoomBanner = (BGABanner) view.findViewById(R.id.banner_main_zoom);
        setListener();
        loadData();
        FragmentManager childfragManager=getChildFragmentManager();
        childfragManager.beginTransaction().replace(R.id.fragment_rank, rankFrag).commit();

        mSideZoomBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int x = mSideZoomBanner.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btn_yuepai_a=(ImageButton)view.findViewById(R.id.btn_yuepai_a);
        btn_yuepai_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ViewPropertyAnimator.animate(v).scaleY(1.4f).scaleX(1.4f).setDuration(80)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ViewPropertyAnimator.animate(v).scaleY(1.2f).scaleX(1.2f)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                            if (yuePaiFragmentAB == null) {
                                                yuePaiFragmentAB = new YuePaiFragmentAB();
                                                yuePaiFragmentAB.setEventflag(1);
                                            }
                                            fragmentTrs = fragmentManager.beginTransaction();
                                            fragmentTrs.replace(R.id.fl_content, yuePaiFragmentAB);
                                            fragmentTrs.addToBackStack(null);
                                            fragmentTrs.commit();
                                            }
                                        }).setDuration(30);
                            }
                        });
            }
        });

        btn_yuepai_b=(ImageButton)view.findViewById(R.id.btn_yuepai_b);
        btn_yuepai_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ViewPropertyAnimator.animate(v).scaleY(1.4f).scaleX(1.4f).setDuration(80)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ViewPropertyAnimator.animate(v).scaleY(1.2f).scaleX(1.2f)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                if (yuePaiFragmentAB == null) {
                                                    yuePaiFragmentAB = new YuePaiFragmentAB();
                                                    yuePaiFragmentAB.setEventflag(2);
                                                }
                                                fragmentTrs = fragmentManager.beginTransaction();
                                                fragmentTrs.replace(R.id.fl_content, yuePaiFragmentAB);
                                                fragmentTrs.addToBackStack(null);
                                                fragmentTrs.commit();
                                            }
                                        }).setDuration(30);
                            }
                        });
            }
        });

        btn_yuepai_c=(ImageButton)view.findViewById(R.id.btn_yuepai_c);
        btn_yuepai_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ViewPropertyAnimator.animate(v).scaleY(1.4f).scaleX(1.4f).setDuration(80)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ViewPropertyAnimator.animate(v).scaleY(1.2f).scaleX(1.2f)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                if (yuepaiFragment == null) {
                                                    yuepaiFragment = new YuePaiFragmentC();
                                                }
                                                fragmentTrs = fragmentManager.beginTransaction();
                                                fragmentTrs.replace(R.id.fl_content, yuepaiFragment);
                                                fragmentTrs.addToBackStack(null);
                                                fragmentTrs.commit();
                                            }
                                        }).setDuration(30);
                            }
                        });
            }
        });

        btn_create_yuepai=(ImageButton)view.findViewById(R.id.btn_create_yuepai);
        btn_create_yuepai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateYuePaiActivity.class));
            }
        });

        //touchMove=rankView.findViewById(R.id.fragment_rank)

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        int x=2;
        x++;
    }

    @Override
    public void onStart(){

        /*if(rankFrag==null){
            rankFrag =new YuePaiFragmentD();
        }*/
       //rankFrag.onCreateView();
       rankFrag.getListView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(rankFrag.isRefreshing()){
                            ValueAnimator anim=ValueAnimator.ofInt(-mSideZoomBanner.getHeight(),0);
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    //mSideZoomBanner.setPadding(0,(Integer)animation.getAnimatedValue(),0,0);
                                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mSideZoomBanner.getLayoutParams();
                                    layoutParams.topMargin = (Integer) animation.getAnimatedValue();
                                    mSideZoomBanner.setLayoutParams(layoutParams);
                                    mSideZoomBanner.invalidate();
                                }
                            });
                            anim.setDuration(300);
                            anim.start();
                            rankFrag.setFreshing(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        ViewGroup.MarginLayoutParams layoutParam = (ViewGroup.MarginLayoutParams)mSideZoomBanner.getLayoutParams();
                        if (layoutParam.topMargin>=0){
                            ValueAnimator anim=ValueAnimator.ofInt(0,-mSideZoomBanner.getHeight());
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    //mSideZoomBanner.setPadding(0,(Integer)animation.getAnimatedValue(),0,0);
                                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)mSideZoomBanner.getLayoutParams();
                                    layoutParams.topMargin = (Integer)animation.getAnimatedValue();
                                    mSideZoomBanner.setLayoutParams(layoutParams);
                                    mSideZoomBanner.invalidate();
                                }
                            });
                            anim.setDuration(300);
                            anim.start();
                        }
                        break;
                }

                return false;
            }
        });

        super.onStart();
    }

    private void slip(float step) {
        //step=mSideZoomBanner.getHeight()/2;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)mSideZoomBanner.getLayoutParams();
        if (-layoutParams.topMargin<layoutParams.height)
        {
                layoutParams.topMargin -= layoutParams.height/20;
                mSideZoomBanner.setLayoutParams(layoutParams);
                mSideZoomBanner.invalidate();
            }
        }


    private void loadData() {
        fragmentManager=getFragmentManager();
        tips.clear();
        tips.add("推荐信息或广告位a");
        tips.add("推荐信息或广告位b");
        tips.add("推荐信息或广告位c");
        tips.add("推荐信息或广告位d");
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
                }

                if (position==1){
                }

               if(position==2) {
               }

                if (position==3){
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
                    Glide.with(YuePaiFragment.this)
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
