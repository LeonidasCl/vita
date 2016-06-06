package com.example.pc.vita.Data.MainStruct;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by AlexMercer on 2016/5/30.
 */
public class ActivityBrief{
    private int activityID;
    private String activityName;
    private ImageView mainView;
    private int joinCount;
    private int likeCount;

    public ImageView getView(){return mainView;}

    public int getID(){return activityID;}

    public String getName(){return activityName;}
}
