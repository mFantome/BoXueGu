package cn.edu.gdmec.android.boxuegu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_nickName;
    private TextView tv_nickName;
    private RelativeLayout rl_sex;
    private TextView tv_sex;
    private RelativeLayout rl_signatrue;
    private TextView tv_signature;
    private TextView tv_userName;
    private String spUserName;
    private TextView tv_back;

    private static final int CHANGE_NICKNAME = 1;
    private static final int CHANGE_SIGNATURE= 2;
    private String new_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName = AnalysisUtils.readLoginUserName(this);

        init();
        initData();
        setlistener();
    }

    private void setlistener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signatrue.setOnClickListener(this);
    }

    public void enterActivityForResult(Class<?> to, int requestCode,Bundle b){
        Intent i = new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requestCode);
    }

    //从数据库中获取数据
    private void initData() {
        UserBean bean = null;
        
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        if(bean ==null){
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = "嗨呀好帅啊";
            bean.sex = "男";
            bean.signature= "问答精灵";
            DBUtils.getInstance(this).saveUserInfo(bean);

        }
        setValue(bean);
    }

    private void setValue(UserBean bean){
        tv_nickName.setText(bean.nickName);
        tv_sex.setText(bean.sex);
        tv_userName.setText(bean.userName);
        tv_signature.setText(bean.signature);

    }

    private void init(){
        tv_back = (TextView)findViewById(R.id.tv_back);
        TextView tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人资料");
        RelativeLayout rl_title_bar = (RelativeLayout)findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4ff"));

        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        tv_nickName = (TextView)findViewById(R.id.tv_nickName);

        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_sex = (TextView)findViewById(R.id.tv_sex);

        rl_signatrue = (RelativeLayout) findViewById(R.id.rl_signatrue);
        tv_signature = (TextView)findViewById(R.id.tv_signatrue);

        tv_userName = (TextView)findViewById(R.id.tv_username);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.rl_nickName:
                String name = tv_nickName.getText().toString();
                Bundle bdName = new Bundle();
                bdName.putString("content",name);
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bdName);
                break;
            case R.id.rl_sex:
                String sex = tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_signatrue:
                String signature = tv_nickName.getText().toString();
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content",signature);
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_SIGNATURE,bdSignature);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGE_NICKNAME:
                if(data != null){
                    new_info = data.getStringExtra("nickName");
                    if(TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tv_nickName.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName", new_info,spUserName);
                }
                break;
            case CHANGE_SIGNATURE:
                if(data != null){
                    new_info = data.getStringExtra("signature");
                    if(TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tv_signature.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature", new_info,spUserName);
                }
                break;
        }
    }

    //修改性别
    private void sexDialog(String sex) {

        int sexFlag = 0;
        if("男".equals(sex)){
            sexFlag = 0;
        }else if("女".equals(sex)){
            sexFlag = 1;

        }

        final String items[] = {"男","女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items,sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],Toast.LENGTH_LONG).show();
                setSex(items[which]);
            }


        });
        builder.show();
    }

    private void setSex(String sex) {
        tv_sex.setText(sex);
        DBUtils.getInstance(this).updateUserInfo("sex",sex,spUserName);
    }
}
