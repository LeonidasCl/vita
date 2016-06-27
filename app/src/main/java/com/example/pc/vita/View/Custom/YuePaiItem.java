package com.example.pc.vita.View.Custom;

import android.view.View;
import android.widget.TextView;

import com.example.pc.vita.R;
import com.example.pc.vita.View.ListItem;

/**
 * Created by Mercer on 2016/6/26.
 */
public class YuePaiItem implements ListItem {

    private String name;

    public YuePaiItem(String name){
        this.name = name;
    }

    @Override
    public void getView(View view) {
        TextView textView = (TextView)view.findViewById(R.id.id_yuepai_item);
        textView.setText(name);
    }
}
