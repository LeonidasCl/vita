package com.example.pc.vita.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.pc.vita.Adapter.ActivityAdapter;
import com.example.pc.vita.Data.MainStruct.ActivityBrief;
import com.example.pc.vita.R;

import java.util.ArrayList;
import java.util.List;

public class YuePaiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yue_pai, container, false);
     /*   view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 尝试自己处理触摸事件, 完成处理 (不需要其他 View 再处理), 则返回 true
                event.getRawY();
                return false;
            }
        });*/
        return view;
    }



}