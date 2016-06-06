package com.example.pc.vita.Activity;

/**
 * Created by pc on 2016/3/10.
 */

import com.example.pc.vita.Data.Model.UserModel;
import com.example.pc.vita.Network.BaseNetworkRequestor;
import com.example.pc.vita.Network.LoginReciever;
import com.example.pc.vita.Network.NetworkRecieverInterface;
import com.example.pc.vita.R;

import java.util.HashMap;

public class SplashActivity extends BaseSplashActivity {

    private UserModel user;



    @Override
    public void initNetworkData() {

        user=new UserModel();
        String username=user.getUsername();
        String usertoken=user.getToken();

        //该用户登录状态未过期，开始获取网络数据
        if (username!=null){
            LoginReciever response=new LoginReciever();
            HashMap loginParams=new HashMap<String,String>();
            loginParams.put("username",username);
            loginParams.put("usertoken", usertoken);
            String result= BaseNetworkRequestor.getInstance().sendRequest(NetworkRecieverInterface.HTTP_POST,
                    "http://www.airserverseu.applinzi.com/userlogin.php", loginParams, response, getApplicationContext());

            if (result.equals(response.RETURN_LOGIN_SUCCESS)){
                //登陆成功
            }
        }
    }


    @Override
    public void initCheck() {

    }

    @Override
    public int getLogoImageResource() {
        return R.drawable.start_logo;
    }

    @Override
    public Class getNextActivityClass() {
        return MainActivity.class;
    }

}
