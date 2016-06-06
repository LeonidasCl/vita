package com.example.pc.vita;

import android.app.Application;
import android.content.Context;

import com.example.pc.vita.Data.Cache.RAMimageCache;
import com.example.pc.vita.Data.Cache.SDimageCache;

/**
 * Created by pc on 2016/4/21.
 */
public class APP extends Application {

    public static Context context;
    public static RAMimageCache RAMcache;
    public static SDimageCache SDcache;
    //public static Orm DBname;

    @Override public void onCreate() {
        super.onCreate();
        context = this;
        RAMcache=new RAMimageCache();
        SDcache=new SDimageCache();
    }


    @Override public void onTerminate() {
        super.onTerminate();
    }

}
