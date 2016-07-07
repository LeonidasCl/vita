package com.example.pc.vita.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.vita.APP;
import com.example.pc.vita.Activity.MainActivity;
import com.example.pc.vita.Data.Model.LoginDataModel;
import com.example.pc.vita.Data.Model.PictureModel;
import com.example.pc.vita.Network.MyInterface;
import com.example.pc.vita.Network.NetRequest;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.CommonUrl;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 *
 * Created by pc on 2016/7/7.
 */
public class YuePaiNavigation extends Fragment implements  MyInterface.NetRequestIterface{

    private BGABanner mSideZoomBanner;
    private NetRequest requestFragment;
    private int naviContent=4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_yuepai, container, false);
        mSideZoomBanner = (BGABanner) view.findViewById(R.id.banner_main_zoom);

        setListener();
        loadData();

        return view;
    }

    private void loadData() {

        loadData(naviContent);

    }

    private void loadData(int i) {
        requestFragment = new NetRequest(this, APP.context);
        Map map = new HashMap();
        String count=String.valueOf(i);
        map.put("userID", "15652009705");
        map.put("usetToken", "123456");
        map.put("requestNum", "100XX");
        map.put("pictureCount", count);
        requestFragment.httpRequest(map, CommonUrl.getYuepaiNavigateurl);
    }


    private void setListener() {
        mSideZoomBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onClickBannerItem(int position) {
                // Toast.makeText(App.getInstance(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void requestFinish(String result, String requestUrl) {
        if (requestUrl.equals(CommonUrl.getYuepaiNavigateurl)) {
            Gson gson = new Gson();
            PictureModel picStatusInfoObject = gson.fromJson(result, PictureModel.class);
            String errorCode = picStatusInfoObject.getErrorCode();
            if (errorCode.equals("0")){
                for (int i=0;i<naviContent;i++){
                    String currentCount="pic"+String.valueOf(i);
                    String picUrl=picStatusInfoObject.getPicUrl(currentCount);
                    Glide.with(YuePaiNavigation.this)
                            .load(picUrl)
                            .placeholder(R.drawable.holder)
                            .error(R.drawable.holder)
                            .into(mSideZoomBanner.getItemImageView(i));
                }
            }
        }
    }

    @Override
    public void exception(IOException e, String requestUrl) {
        Toast.makeText(APP.getInstance(), "获取网络数据失败！请检查网络连接！", Toast.LENGTH_SHORT).show();
    }
}
