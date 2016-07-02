package com.example.pc.vita.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.vita.APP;
import com.example.pc.vita.Network.MyInterface;
import com.example.pc.vita.Network.NetRequest;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.CommonUrl;
import com.example.pc.vita.Util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


/**
 *
 */
public class LoginActivity extends AppCompatActivity implements MyInterface.NetRequestIterface{

    private TextView username;
    private TextView password;
    private ImageButton btn_login;
    private NetRequest requestFragment;
    private ProgressDialog loginProgressDlg;
    private int loginReturn;
    private DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            if(loginReturn==0){
                CommonUtils.getUtilInstance().showToast(APP.context, getString(R.string.login_success));
                finish();
            }else{
                CommonUtils.getUtilInstance().showToast(APP.context, getString(R.string.login_fail));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        username=(TextView)findViewById(R.id.login_username);
        password=(TextView)findViewById(R.id.login_password);
        btn_login=(ImageButton)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查输入格式，发弹窗请求到handler，并发网络请求
                Map map = new HashMap();
                String usrnm=username.getText().toString();
                String pwd=password.getText().toString();
                map.put("username",usrnm);
                map.put("password",pwd);
                loginProgressDlg = ProgressDialog.show(APP.context, "vita", "正在登陆中", true, false);
                requestFragment.httpRequest(map, CommonUrl.loginAccount);
            }
        });
        requestFragment=new NetRequest(this,this);

    }

    @Override
    public void requestFinish(String result, String requestUrl) {
        if (requestUrl.equals(CommonUrl.loginAccount)) {//登录请求被返回
            try {
                JSONObject object = new JSONObject(result);
                loginReturn = object.getInt("loginReturn");
                loginProgressDlg.cancel();//进度条取消
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void exception(IOException e, String requestUrl) {

    }
}

