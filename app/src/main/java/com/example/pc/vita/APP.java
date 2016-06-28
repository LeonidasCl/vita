package com.example.pc.vita;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.pc.vita.Data.Cache.RAMimageCache;
import com.example.pc.vita.Data.Cache.SDimageCache;

import java.io.File;

/**
 * Created by pc on 2016/4/21.
 */
public class APP extends Application {

    public static Context context;
    public static RAMimageCache RAMcache;
    public static SDimageCache SDcache;


    @Override public void onCreate() {
        super.onCreate();
        context = this;
        RAMcache=new RAMimageCache();
        SDcache=new SDimageCache();
        applicationContext = this;
        getScreenDimension();
    }

    public static String cache_image_path, photo_path;
    public static File cacheImageDir, photoDir;
    private static APP instance;
    public static int screenWidth, screenHeight;
    public static String loginShare = "";
    public static Context applicationContext;
    private String myName, myPhoto;

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyPhoto() {
        return myPhoto;
    }

    public void setMyPhoto(String myPhoto) {
        this.myPhoto = myPhoto;
    }

    public static synchronized APP getInstance() {
        if (instance == null) {
            instance = new APP();
        }
        return instance;
    }

    public void getScreenDimension() {
        WindowManager mWM = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        mWM.getDefaultDisplay().getMetrics(mDisplayMetrics);
        screenWidth = mDisplayMetrics.widthPixels;
        screenHeight = mDisplayMetrics.heightPixels;
    }


    private File createCacheDir() {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            cache_image_path = sdcardDir.getPath() + "/vita/cache/images/";
            cacheImageDir = new File(cache_image_path);
            photo_path = sdcardDir.getPath() + "/vita/cache/photoes/";
            photoDir = new File(photo_path);
        } else {
            photo_path= "/vita/cache/photoes/";
            cacheImageDir = new File("/vita/cache/images");
            photoDir = new File(photo_path);
        }
        if (!cacheImageDir.exists()) {
            cacheImageDir.mkdirs();
        }
        if (!photoDir.exists()) {
            photoDir.mkdirs();
        }
        return cacheImageDir;
    }

    @Override public void onTerminate() {
        super.onTerminate();
    }

}
