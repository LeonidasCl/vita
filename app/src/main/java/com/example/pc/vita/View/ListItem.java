package com.example.pc.vita.View;

import android.view.View;

/**
 * Created by Mercer on 2016/6/26.
 */

public interface ListItem {

    //设置每个View内的所有内容，格式如下（假设具体类里面有ImageView image）
    //ImageView image1 = (ImageView) view.findViewById(R.id.image1);
    //image1.setImageResource(image);
    public void getView(View view);
}
