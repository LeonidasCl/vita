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

public class activityFragment extends Fragment {

    private Activity yuepai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yuepai=this.getActivity();
        View view = inflater.inflate(R.layout.fragment_huo_dong, container, false);

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
        return view;
    }


}