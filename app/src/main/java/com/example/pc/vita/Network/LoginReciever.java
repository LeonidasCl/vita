package com.example.pc.vita.Network;

import android.content.Context;


import com.example.pc.vita.Data.Model.PictureModel;
import com.example.pc.vita.Data.Model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pc on 2016/3/6.
 */
public class LoginReciever extends BaseNetworkReciever {

    private UserModel userModel;
    private PictureModel latest;
    private PictureModel hottest;
  //  private NetworkStateModel networkStateModel;

    @Override
    public void recieve(String result,Context context) throws JSONException {
        result=result.toString();
       // JSONObject networkJson=new JSONObject(result).getJSONObject("requeststate");
        JSONObject userJson=new JSONObject(result).getJSONObject("user");
        JSONObject latestJson=new JSONObject(result).getJSONObject("latest");
        JSONObject hottestJson=new JSONObject(result).getJSONObject("hottest");

      //  networkStateModel=new NetworkStateModel(context);
       // networkStateModel.save(networkJson);
        //保存获取的JSON文件
        userModel=new UserModel();
        userModel.save(userJson);

      //  latest=new PictureModel(context);
      //  latest.save(latestJson);
      //  hottest=new PictureModel(context);
      //  hottest.save(hottestJson);
    }
    //user只能从服务器获取，故不提供set方法
    public UserModel getUserModel() {
        if (userModel==null){

        }
        return userModel;
    }

    public PictureModel getLatestModel() {
        if (latest==null){

        }
        return latest;
    }

    public PictureModel gethottestModel() {
        if (hottest==null){

        }
        return hottest;
    }

  /*  public NetworkStateModel getnetworkStateModel() {
        if (networkStateModel==null){

        }
        return networkStateModel;
    }*/

}
