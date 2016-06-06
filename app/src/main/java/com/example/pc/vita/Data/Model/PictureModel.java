package com.example.pc.vita.Data.Model;

import android.graphics.drawable.Drawable;

import com.example.pc.vita.Data.Cache.CacheInterface;

/**
 * Created by pc on 2016/3/7.
 */
public class PictureModel  {

   // RAMimageCache RAMimgCache;
  //  SDimageCache SDimgCache;
    CacheInterface<String,Drawable> imgCache;

    public PictureModel(String URL) {
        url=URL;
        src=null;
      //  RAMimgCache= APP.RAMcache;
     //   SDimgCache=new SDimageCache();
    }

    private String url;
    private Drawable src;


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Drawable getSrc(CacheInterface<String,Drawable> cache) {

     /*   String superClass=cache.getClass().getName();
        if (superClass.equals("com.example.pc.courseapp.Data.Cache.RAMimageCache")){
            RAMimgCache=(RAMimageCache)cache;
        }else if (superClass.equals("com.example.pc.courseapp.Data.Cache.SDimageCache")){
            SDimgCache=(SDimageCache)cache;
        }*/


        /*检查内存中是否有缓存*/
    /*    if (RAMimgCache.get(url)!=null) {
            Drawable soft = RAMimgCache.get(url);
            return soft;
        }
        //内存中没有，尝试从外存中获取
        Drawable drawable=SDimgCache.get(url);
        if (drawable!=null){
            return drawable;
    }*/
        imgCache=cache;
        Drawable drawable=imgCache.get(url);
        if (drawable!=null){
            return drawable;
        }
        //本地没有此图片，只能从网络获取
        //TaskManager.addTask(new LoadTask(url, view));
        return null;
    }

    /*预留的依赖注入接口*/
    public void setSrc(Drawable src) {

    }
}


