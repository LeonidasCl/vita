package com.example.pc.vita.View;

import android.content.Context;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Mercer on 2016/6/26.
 */
public class ListFactory {
    public static boolean createList(Context context,int itemLayout,List<ListItem> theList,ListView listView){
        ListAdapter theAdapter = new ListAdapter(context,itemLayout,theList);
        listView.setAdapter(theAdapter);

        return true;
    }
}
