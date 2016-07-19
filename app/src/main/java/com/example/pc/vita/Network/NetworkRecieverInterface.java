package com.example.pc.vita.Network;

import android.content.Context;

import org.json.JSONException;

/**
 * Created by pc on 2016/3/6.
 */
public interface NetworkRecieverInterface {

    /* licl 2016/3/5
    * 此接口用于保存所有网络请求的返回状态常量集
    * 最后一位：0表示正常，其余表示错误类型
    * */
    int HTTP_GET=1;
    int HTTP_POST=2;

    //网络连接失败
    String NETWORK_ERROR="00000";
    //注册成功
    String RETURN_LOGIN_SUCCESS="10000";
    //用户名或密码错误
    String RETURN_LOGIN_INVALIDUSER="10001";
    //密码错误
    String RETURN_LOGIN_INVALIDPASSWORD="10002";
    //token失效（太久未运行应用，需要重新登录）
    String RETURN_LOGIN_INVALIDTOKEN="10002";

    //下载资源文件成功
    String RETURN_FETCH_SUCCESS="10010";
    //该资源文件不存在
    String RETURN_FETCH_FILENOTEXIST="10011";

    //评论成功
    String RETURN_INTERACT_COMMENT_SUCCESS="10020";
    //评论中含有非法字符
    String RETURN_INTERACT_COMMENT_ILLIGAL="10021";

}
