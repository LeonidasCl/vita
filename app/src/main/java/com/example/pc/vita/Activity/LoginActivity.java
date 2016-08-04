package com.example.pc.vita.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.vita.APP;
import com.example.pc.vita.Network.NetworkCallbackInterface;
import com.example.pc.vita.Network.NetRequest;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.CommonUrl;
import com.example.pc.vita.Util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
public class LoginActivity extends AppCompatActivity implements NetworkCallbackInterface.NetRequestIterface{

    private TextView username;
    private TextView password;
    private TextView forgotpassword;
    private TextView signup;
    private TextView verifycode;
    private Button btn_login;
    private Button btn_verifycode;
    private NetRequest requestFragment;
    private ProgressDialog loginProgressDlg;
    private int loginReturn;
    private ImageView loginbtnimg;
    private CountDownTimer timeCount;
    private int eventFlag=1;//1为登录 2为忘记密码 3为验证注册 4为最终注册
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

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
                Map map = new HashMap();
                if (eventFlag==1){
                //检查输入格式，发弹窗请求到handler，并发网络请求
                String usrnm=username.getText().toString();
                String pwd=password.getText().toString();
                map.put("username",usrnm);
                map.put("password",pwd);
                loginProgressDlg = ProgressDialog.show(LoginActivity.this, "vita", "处理中", true, false);
                requestFragment.httpRequest(map, CommonUrl.loginAccount); }
                if (eventFlag==2){
                    //TODO
                }
                if (eventFlag==3){
                    //TODO
                }
                if (eventFlag==4){
                    //TODO
                }
            }
        });
        btn_verifycode=(Button)findViewById(R.id.btn_verify_code);
        btn_verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCount.start();
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
                    signup.setVisibility(View.VISIBLE);
                    eventFlag=1;
                    forgotpassword.setText("忘记密码了");
                    animationHide.setDuration(500);
                    verifycode.startAnimation(animationHide);
                    verifycode.setVisibility(View.GONE);
                    animationShow.setDuration(500);
                    password.startAnimation(animationShow);
                    btn_verifycode.setVisibility(View.GONE);
                    password.setVisibility(View.VISIBLE);
                    return;
                }
                if(eventFlag!=2){
                signup.setVisibility(View.INVISIBLE);
                eventFlag=2;
                animationHide.setDuration(500);
                password.startAnimation(animationHide);
                password.setVisibility(View.GONE);
                animationShow.setDuration(500);
                verifycode.startAnimation(animationShow);
                verifycode.setVisibility(View.VISIBLE);
                btn_verifycode.setVisibility(View.VISIBLE);
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
                    forgotpassword.setVisibility(View.INVISIBLE);
                    btn_verifycode.setVisibility(View.VISIBLE);
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
                    btn_verifycode.setVisibility(View.GONE);
                    forgotpassword.setVisibility(View.VISIBLE);
                    btn_login.setText("  登   录");
                    return;
                }
            }
        });
        timeCount=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //btn_verifycode.setBackgroundColor(getResources().getColor(R.drawable.shape_verifycode));
                btn_verifycode.setBackground(getResources().getDrawable(R.drawable.shape_verifycode_clicked));
                btn_verifycode.setClickable(false);
                btn_verifycode.setText("重发 "+millisUntilFinished/1000+"s");
            }

            @Override
            public void onFinish() {
                btn_verifycode.setText(R.string.text_verify_code);
                //btn_verifycode.setBackgroundColor(getResources().getColor(R.color.gold));
                btn_verifycode.setBackground(getResources().getDrawable(R.drawable.shape_verifycode));
                btn_verifycode.setClickable(true);
            }
        };

        Intent intent = getIntent();
        String value = intent.getStringExtra("method");
        if (value!=null&&value.equals("register")){
            signup.performClick();
        }
    }

    @Override
    public void requestFinish(String result, String requestUrl) {
        if (requestUrl.equals(CommonUrl.loginAccount)) {//返回登录请求
            try {
                JSONObject object = new JSONObject(result);
                loginReturn = object.getInt("loginReturn");
                loginProgressDlg.cancel();//进度条取消
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestUrl.equals(CommonUrl.loginAccount)) {//返回了验证码
            try {
                JSONObject object = new JSONObject(result);
                loginReturn = object.getInt("loginReturn");
                loginProgressDlg.cancel();//进度条取消
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestUrl.equals(CommonUrl.loginAccount)) {//返回了验证结果
            try {
                JSONObject object = new JSONObject(result);
                //object.getString();
                loginReturn = object.getInt("loginReturn");
                loginProgressDlg.cancel();//进度条取消

                btn_verifycode.setVisibility(View.GONE);
                forgotpassword.setVisibility(View.GONE);
                signup.setEnabled(false);
                username.setHint("请指定昵称");
                password.setHint("请设置密码");
                btn_login.setText("  注   册");
                eventFlag=4;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestUrl.equals(CommonUrl.loginAccount)) {//返回注册结果
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
