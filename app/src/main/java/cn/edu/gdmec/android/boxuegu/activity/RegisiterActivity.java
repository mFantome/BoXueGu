package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

public class RegisiterActivity extends AppCompatActivity {

    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private Button btn_regisiter;
    private EditText et_username;
    private EditText et_pwd;
    private EditText et_pwd_again;
    private String username;
    private String pwd;
    private String pwdAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisiter);
//chandijdqwddd
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back = (TextView) findViewById(R.id.tv_back);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);

        btn_regisiter = (Button) findViewById(R.id.btn_regisier);
        et_username = (EditText) findViewById(R.id.et_username);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd_again = (EditText) findViewById(R.id.et_pwd_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisiterActivity.this.finish();
            }
        });
        btn_regisiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取内容
                getEditString();

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegisiterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisiterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(RegisiterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!pwd.equals(pwdAgain)){
                    Toast.makeText(RegisiterActivity.this, "两次密码必须一致", Toast.LENGTH_SHORT).show();
                    return;
                }else  if(isExistUserName(username)){
                    Toast.makeText(RegisiterActivity.this, "此用户已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(RegisiterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //保存信息
                    saveRigisiterInfo(username,pwd);
                    //注册成功后，用户名传递到LoginActivity中
                    Intent data = new Intent();
                    data.putExtra("username",username);
                    //data.putExtra("pwd",pwd);
                    setResult(RESULT_OK,data);
                    RegisiterActivity.this.finish();
                }
            }



        });
    }

    private void saveRigisiterInfo(String username, String pwd) {
        String md5pwd = MD5Utils.md5(pwd);
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(username,md5pwd);
        editor.commit(); //提交修改
    }

    private boolean isExistUserName(String username) {
        boolean has_username = false;
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPwd = sp.getString(username,"");
        if(!TextUtils.isEmpty(spPwd)){
            has_username = true;
        }
        return has_username;
    }

    private void getEditString() {
        //trim()去除空白字符串
        username = et_username.getText().toString().trim();
        pwd = et_pwd.getText().toString();
        pwdAgain = et_pwd_again.getText().toString().trim();

    }
}
