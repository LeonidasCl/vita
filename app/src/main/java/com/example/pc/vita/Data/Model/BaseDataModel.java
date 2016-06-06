package com.example.pc.vita.Data.Model;

import com.example.pc.vita.APP;
import com.example.pc.vita.Data.Cache.ACache;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pc on 2016/3/7.
 */
public abstract class BaseDataModel {

    BaseDataModel(){
        this.mCache = ACache.get(APP.context);
    }

    private ACache mCache;

    /*这个方法完成自定义数据模型的缓存*/
    public boolean save(JSONObject jsonObject){
        String classname=getClass().getName();
        if(mCache!=null){
            mCache.put(classname, jsonObject,ACache.TIME_HOUR);
            return true;
        }
        return false;
    }
    /*直接调用用于销毁数据模型，例如注销用户
    * 这个方法可以被外部调用
    * */
    public boolean delete(){
       return mCache.remove(getClass().getName());
    }

    /*获取子类数据模型的JSON包
    * 返回的JSON中包含该模型的所有键值对
    * 这个方法不能被外部调用
    * 开发者获取/建立数据模型后
    * 在本类的公共方法getString中直接按键名获取相应值
    * */
    private JSONObject getJson(){
        JSONObject jsonObject = mCache.getAsJSONObject(getClass().getName());
        if (jsonObject==null)
        {

        }
        return jsonObject;
    }

    /*对建立、获取的数据模型实例
    * 直接调用这个函数
    * 可以获得相应键值
    * */
    public String getString(String varname) {
        JSONObject jsonObject=getJson();
        if(jsonObject!=null)
        {
            String str= null;
            try {
                str = jsonObject.getString(varname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return str;
        }
        else return varname+" is null";
    }

}
