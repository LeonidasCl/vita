package com.example.pc.vita.Network;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pc on 2016/3/6.
 */
public class BaseNetworkReciever implements NetworkRecieverInterface {

    /*
    * 发送请求后返回请求结果
    * */
    public String requestResult(String result) {
        result=result.toString();
        String code="00000";//五个零表示网络连接失败
        JSONObject networkJson;
        try {
            networkJson=new JSONObject(result).getJSONObject("requeststate");
            code=networkJson.getString("returncode");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return code;
    }

    public String resultDecode(){
        //TODO 解析出具体的错误并logout
        return null;
    }

    @Override
    public void recieve(String result,Context context) throws JSONException {

    }
}
