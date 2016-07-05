package com.example.pc.vita.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private TextView forgotpassword;
    private TextView signup;
    private TextView verifycode;
    private Button btn_login;
    private NetRequest requestFragment;
    private ProgressDialog loginProgressDlg;
    private int loginReturn;
    private ImageView loginbtnimg;
    private int eventFlag=1;//1为登录 2为忘记密码 3为注册
    TranslateAnimation animationHide=new TranslateAnimation(Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f);
    TranslateAnimation animationShow=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);


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
        verifycode=(TextView)findViewById(R.id.register_verifycode);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eventFlag==1){
                //检查输入格式，发弹窗请求到handler，并发网络请求
                Map map = new HashMap();
                String usrnm=username.getText().toString();
                String pwd=password.getText().toString();
                map.put("username",usrnm);
                map.put("password",pwd);
                loginProgressDlg = ProgressDialog.show(LoginActivity.this, "vita", "正在登录", true, false);
                requestFragment.httpRequest(map, CommonUrl.loginAccount); }
                if (eventFlag==2){
                    //TODO
                }
                if (eventFlag==3){
                    //TODO
                }
            }
        });
        requestFragment=new NetRequest(this,this);
        loginbtnimg=(ImageView)findViewById(R.id.loginimgview);
        loginbtnimg.bringToFront();
        forgotpassword=(TextView)findViewById(R.id.btn_forgot);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventFlag==2){
                    eventFlag=1;
                    forgotpassword.setText("忘记密码了");
                    animationHide.setDuration(500);
                    verifycode.startAnimation(animationHide);
                    verifycode.setVisibility(View.GONE);
                    animationShow.setDuration(500);
                    password.startAnimation(animationShow);
                    password.setVisibility(View.VISIBLE);
                    return;
                }
                if(eventFlag!=2){
                eventFlag=2;
                animationHide.setDuration(500);
                password.startAnimation(animationHide);
                password.setVisibility(View.GONE);
                animationShow.setDuration(500);
                verifycode.startAnimation(animationShow);
                verifycode.setVisibility(View.VISIBLE);
                forgotpassword.setText("找回了密码");}
                return;
            }
        });
        signup=(TextView)findViewById(R.id.btn_newuser);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventFlag!=3){
                eventFlag=3;
                animationHide.setDuration(500);
                password.startAnimation(animationHide);
                password.setVisibility(View.GONE);
                animationShow.setDuration(500);
                verifycode.startAnimation(animationShow);
                verifycode.setVisibility(View.VISIBLE);
                signup.setText("老用户登录");
                    btn_login.setText("  注   册");
                    return;
                }
                if(eventFlag==3){
                    eventFlag=1;
                    animationHide.setDuration(500);
                    verifycode.startAnimation(animationHide);
                    verifycode.setVisibility(View.GONE);
                    animationShow.setDuration(500);
                    password.startAnimation(animationShow);
                    password.setVisibility(View.VISIBLE);
                    signup.setText("新用户注册");
                    btn_login.setText("  登   录");
                    return;
                }
            }
        });

        Intent intent = getIntent();
        String value = intent.getStringExtra("method");
        if (value!=null&&value.equals("register")){
            signup.performClick();
        }
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
