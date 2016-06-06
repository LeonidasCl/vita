package com.example.pc.vita.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    private Button button_YuePai;
    private Button button_Activity;

    private ListView activityList;
    private ActivityAdapter adapter;

    private List<ActivityBrief> myList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yue_pai, container, false);

        FragmentInit(view);

        return view;
    }

    private boolean FragmentInit(View view){
        myList= new ArrayList<>();

        cleanList();
        readList();

        adapter = new ActivityAdapter(YuePaiFragment.this.getContext(),R.layout.activity_item_view,myList);

        activityList = (ListView)view.findViewById(R.id.activty_list);
        activityList.setAdapter(adapter);

        return true;
    }

    private void cleanList(){
        myList.clear();
    }

    //从服务器中读取活动列表
    private void readList(){
    }
}