package com.example.pc.vita.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.example.pc.vita.Activity.ActivityIntroduceActivity;
import com.example.pc.vita.Data.MainStruct.ActivityBrief;

import java.util.List;

import com.example.pc.vita.R;

/**
 * Created by AlexMercer on 2016/5/30.
 */
public class ActivityAdapter extends ArrayAdapter<ActivityBrief> {

    private int resrouceID;
    Context context;

    public ActivityAdapter(Context context, int resource, List<ActivityBrief> objects) {
        super(context, resource, objects);
        resrouceID = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ActivityBrief activity = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resrouceID, null);
        ImageButton mainView = (ImageButton)view.findViewById(R.id.activity_view);
        mainView.setBackground(activity.getView().getDrawable());

        ImageButton mainButton = (ImageButton)view.findViewById(R.id.activity_view);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityIntroduceActivity.class);
                intent.putExtra("activityID",activity.getID());
                intent.putExtra("activityName",activity.getName());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
