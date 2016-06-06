package com.example.pc.vita.Task;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import com.example.pc.vita.APP;
import com.example.pc.vita.Data.Cache.RAMimageCache;
import com.example.pc.vita.Data.Cache.SDimageCache;
import com.example.pc.vita.Data.Model.PictureModel;

import java.net.URL;

/**
 * Created by pc on 2016/3/14.
 */


public final class ImgTaskManager {

    //主线程的callback，这个主线程就是main
    private CallBack callback=new CallBack();

    //内存缓存句柄
    private RAMimageCache imgCache= APP.RAMcache;

    /*
    * 处理子线程返回的消息
    * */
    private class CallBack {
        public void onImgLoaded(Drawable drawable,ImageView view,String url){
            view.setImageDrawable(drawable);
            /*开始一个保存到SD卡的任务*/
            TaskManager.addTask(new saveFileTask(drawable,url));
        }
    }

    /*保存到SD卡任务*/
    private class saveFileTask extends BaseTask{
        private  Drawable drawable;
        private String fileName;
        saveFileTask(Drawable dw,String file){
                drawable=dw;
            fileName=file;
        }
        @Override
        public void run() {
            SDimageCache cache=new SDimageCache();
            cache.put(fileName,drawable);
        }
    }

    public void loadImg(final PictureModel model,final ImageView view){
        //先尝试从内存和SD卡中获取
        Drawable drawable=model.getSrc(imgCache);
        if (drawable!=null){
            callback.onImgLoaded(drawable,view,model.getUrl());
            return;
        } else {
            //本地没有此图片，只能从网络获取
            TaskManager.addTask(new LoadTask(model.getUrl(),view));
        }
    }

    /*
    * 接收子线程返回的消息
    * 这是本线程（主线程）的handler
    * */
    private Handler handler = new Handler();

    /*
    * 从网络下载图片任务
    * */
    private class LoadTask extends BaseTask{

        LoadTask(String URL,ImageView mView){
            imgURL=URL;
            imgView=mView;
        }
        private ImageView imgView;
        private String imgURL;
        private Drawable drawable=null;
        /*
        * ******在这里进入新线程*********
        * */
        @Override
        public void run() {
            drawable = loadFromURl(imgURL);
            /*handler分发一个runnable到主线程消息队列,并不是建立子线程
            *
            * */
            handler.post(new Runnable() {
                /*
                * 放入内存并更新UI，这段代码是在主线程执行的
                * ***********在这里返回到原线程********
                *  */
                @Override
                public void run() {
                    if (callback != null) {
                        /*放入内存*/
                        imgCache.put(imgURL, drawable);
                        //imgCache.get(imgURL);
                        callback.onImgLoaded(drawable, imgView, imgURL);
                    }
                }
            });
        }
    }

    private Drawable loadFromURl(String url) {
        Drawable drawable = null;
        try {
            drawable = BitmapDrawable.createFromStream(
                    new URL(url).openStream(), "image");
        } catch (Exception e) {
        }
        return drawable;
    }

}
