package com.example.pc.vita.Data.Cache;

import android.graphics.drawable.Drawable;

/**
 * Created by pc on 2016/4/20.
 */
public class RAMimageCache extends LruMethodCache<String,Drawable> {

    /*
    * 一级图片缓存(内存)
    * */
  /*  public Map<String, SoftReference<Drawable>> imgCache = new HashMap<String, SoftReference<Drawable>>();


    public SoftReference<Drawable> getImgCache(String url){
        if(imgCache.containsKey(url))
        {
           // this.url=url;
            return  imgCache.get(url);
        }
        return null;
    }*/


}
