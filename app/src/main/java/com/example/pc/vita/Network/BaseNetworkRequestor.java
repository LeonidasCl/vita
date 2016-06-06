package com.example.pc.vita.Network;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by pc on 2016/3/6.
 */
public class BaseNetworkRequestor {

    /*licl updated 2016/3/13
    *静态内部类
    * 保证线程安全
    * update:避免使用同步的方式保证线程安全可提升性能
    * */
    private static class StaticHolder implements Serializable {
        public static final BaseNetworkRequestor S_INSTANCE = new BaseNetworkRequestor();
        /*防止反序列化*/
        private Object readResolve() {
            return S_INSTANCE;
        }
    }

    /*licl updated 2016/3/13
    *避免被外部实例化
    * */
    private BaseNetworkRequestor() {}


    /*licl updated 2016/3/13
    *线程安全
    * 避免并发环境下生成多个单例类
    * */
    public static final BaseNetworkRequestor getInstance()
    {
        return StaticHolder.S_INSTANCE;
    }

    /*licl updated 2016/3/7
    *发送get请求
    * */
    private String sendGet(String url,String params){

        String result="";
        BufferedReader in=null;
        try {
            String urlName=url+"?"+params;
            URL realURL=new URL(urlName);
            URLConnection conn=realURL.openConnection();
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0b(compatible;bMSIE 6.0;bWindows NT 5.1; SV1)");
            conn.connect();

            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line=in.readLine())!=null)
                result+=line;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(in!=null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        return result;
    }


    /*licl 2016/3/7
    *发送post请求
    * */
    private String sendPost(String url,String params){

        PrintWriter out=null;
        String result="";
        BufferedReader in=null;
        try {
            URL realURL=new URL(url);
            URLConnection conn=realURL.openConnection();
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0b(compatible;bMSIE 6.0;bWindows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out=new PrintWriter(conn.getOutputStream());
            out.print(params);
            out.flush();

            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line=in.readLine())!=null)
            {result+=line;}
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        return result;
    }

    /*licl 2016/3/7
    *发送请求的接口
    * 这个方法可以被外部调用
    * */
    public String sendRequest(int type, String url, Map<String, String> paramMap, BaseNetworkReciever reciever, Context context)  {

        String result="";
        String params="";
        JSONObject json=new JSONObject();
        for(String key:paramMap.keySet()){
            try {
                json.put(key,paramMap.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        params=String.valueOf(json);
        if (type==NetworkRecieverInterface.HTTP_GET)
            result=sendGet(url, params);
        if (type==NetworkRecieverInterface.HTTP_POST)
            result=sendPost(url, params);//result为保存下来的JSON
        //用自定义的解码函数解析这个JSON包并将数值存入
        try {
            reciever.recieve(result,context);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            return reciever.requestResult(result);

    }


}
