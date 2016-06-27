package com.example.pc.vita.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Mercer on 2016/6/26.
 */
public class ListAdapter extends ArrayAdapter<ListItem> {

    private int resourceId;

    public ListAdapter(Context context, int viewResourceId, List<ListItem> objects){
        super(context,viewResourceId,objects);
        resourceId = viewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        item.getView(view);
        return view;
    }
}
