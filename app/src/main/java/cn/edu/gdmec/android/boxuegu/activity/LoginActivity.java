package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_main_title;
    private TextView tv_back;
    private TextView tv_regisiter;
    private TextView tv_find_pwd;
    private TextView btn_login;
    private EditText et_user_name;
    private EditText et_pwd;
    private String username;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_regisiter = (TextView) findViewById(R.id.tv_regisiter);
        tv_find_pwd = (TextView) findViewById(R.id.tv_find_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_user_name = ((EditText) findViewById(R.id.et_user_name));
        et_pwd = ((EditText) findViewById(R.id.et_pwd));



        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();

            }
        });
        tv_regisiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisiterActivity.class);
                startActivityForResult(intent,1);

            }
        });
        tv_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //跳转到找回密码界面
            }
        });

        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_user_name.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                //输入的密码
                String md5Psw = MD5Utils.md5(pwd);
                //取出的密码
                String spPwd = readPsw(username);

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5Psw.equals(spPwd)){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存状态和登录名
                    saveLoginStatus(true,username);
                    Intent data = new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    return;
                }else if(!TextUtils.isEmpty(spPwd) && !md5Psw.equals(spPwd)){
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void saveLoginStatus(boolean status, String username) {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",status);
        editor.putString("loginUserName",username);
        editor.commit();

    }

    //取出密码方法
    private String readPsw(String username) {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);

        return  sp.getString(username,"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null){
            String username = data.getStringExtra("userName");
            //登录成功登录页面获取用户名
            if(!TextUtils.isEmpty(username)){
                et_user_name.setText(username);
                et_user_name.setSelection(username.length());
            }
        }
    }
}
