package com.example.pc.vita.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pc.vita.Activity.ModelActivity;
import com.example.pc.vita.Activity.PhotographerActivity;
import com.example.pc.vita.Activity.activityActivity;
import com.example.pc.vita.R;

public class YuePaiFragmentC extends Fragment {

    private Activity yuepai;
    private View navibar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yuepai=this.getActivity();
        View view = inflater.inflate(R.layout.fragment_yue_pai_c, container, false);

     /*   view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 尝试自己处理触摸事件, 完成处理 (不需要其他 View 再处理), 则返回 true
                event.getRawY();
                return false;
            }
        });*/

        Button toPhotograph=(Button)view.findViewById(R.id.button_yaopai);
        toPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(yuepai, PhotographerActivity.class));
            }
        });

        Button toModel=(Button)view.findViewById(R.id.button_beipai);
        toModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(yuepai, ModelActivity.class));
            }
        });

        Button toActivity=(Button)view.findViewById(R.id.button_activity);
        toActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(yuepai, activityActivity.class));
            }
        });

        navibar=yuepai.findViewById(R.id.fragment_list);
        navibar.setVisibility(View.GONE);
        
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navibar.setVisibility(View.VISIBLE);
    }

}