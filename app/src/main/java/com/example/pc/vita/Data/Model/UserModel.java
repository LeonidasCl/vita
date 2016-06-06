package com.example.pc.vita.Data.Model;

/**
 * Created by pc on 2016/3/7.
 */
public class UserModel extends BaseDataModel {

    //用户名
    private String username;

    //服务器发放的自动登录许可token，每次登录更新，有效期一周
    private String token;


    public String getUsername()  {
        if (username==null)
        {
            username=getString("username");
            return username;
        }
        else
        {
            return username;
        }
    }

    public String getToken() {
        if (token==null)
        {
            token=getString("usertoken");
            return token;
        }
        else
        {
            return token;
        }
    }
}
