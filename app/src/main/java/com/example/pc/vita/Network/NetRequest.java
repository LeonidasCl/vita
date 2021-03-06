package com.example.pc.vita.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.pc.vita.APP;
import com.example.pc.vita.Activity.LoginActivity;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.CommonUtils;
import com.example.pc.vita.Util.UserInfoUtil;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetRequest {
	private NetworkCallbackInterface.NetRequestIterface netRequestIterface;
	private Context context;

	public NetRequest(NetworkCallbackInterface.NetRequestIterface netRequestIterface, Context context) {
		this.netRequestIterface = netRequestIterface;
		this.context = context;
	}
	/**
	 * 网络请求用的是OKHttp，这个开源项目的好处是1.Android 6.0后不支持HttpClient请求，而它使用HttpUrlConnection 2.默认支持https
	 */
	public void httpRequest(Map<String, Object> map, final String requestUrl) {
		if (!CommonUtils.getUtilInstance().isConnectingToInternet(context)) {
			Toast.makeText(context, context.getString(R.string.internet_fail_connect),Toast.LENGTH_LONG).show();
			return;
		}

		//CommonUtils.getUtilInstance().showToast(APP.context,map.toString());

		OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        FormBody.Builder builder = new FormBody.Builder();
		if (null != map && !map.isEmpty())
			for (String key : map.keySet()) {
				builder.add(key, map.get(key)+"");
			}

		Request request = new Request.Builder()
				.url(requestUrl)
				.post(builder.build())
				.build();
		try {
			//mOkHttpClient.setConnectTimeout(5000, TimeUnit.MILLISECONDS);

			mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //					Log.d("gaolei", "NetRequest-----------onFailure----------------" + e.getMessage());
                    netRequestIterface.exception(e, requestUrl);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
					Log.d("vita", "onResponse----------------------" + result);
                    //Toast.makeText(context, result,Toast.LENGTH_LONG).show();
                    //CommonUtils.getUtilInstance().showToast(APP.context,result);
                    netRequestIterface.requestFinish(result, requestUrl);
                }

			});
		}catch (Exception e){

		}
	}
}
