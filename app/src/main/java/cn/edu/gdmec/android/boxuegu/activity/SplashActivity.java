package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.gdmec.android.boxuegu.R;

/**
 * Created by student on 17/12/25.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        TextView tv_version = (TextView) findViewById(R.id.tv_version);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            tv_version.setText("V"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("V");
        }

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,2000);
    }
}
