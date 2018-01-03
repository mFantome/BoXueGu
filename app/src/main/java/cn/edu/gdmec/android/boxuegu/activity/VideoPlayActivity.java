package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import cn.edu.gdmec.android.boxuegu.R;

/**
 * Created by acer on 2018/1/2.
 */

public class VideoPlayActivity extends AppCompatActivity{
    private String videoPath;
    private int position;
    private VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoPath = getIntent().getStringExtra("videoPath");
        position = getIntent().getIntExtra("position",0);
        init();
    }

    private void init() {
        videoView = (VideoView) findViewById(R.id.videoView);
        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        play();

    }

    private void play() {

        if(TextUtils.isEmpty(videoPath)){
            Toast.makeText(this,"本地没有此视频，暂时无法播放",Toast.LENGTH_SHORT).show();
            return;
        }
        if(videoPath.equals("beyond.mp4")){
            String uri = "android.resource://" + getPackageName() + "/" + R.raw.beyond ;
            videoView.setVideoPath(uri);
            videoView.start();
        }else if(videoPath.equals("video11.mp4")){
            String uri = "android.resource://" + getPackageName() + "/" + R.raw.video11 ;
            videoView.setVideoPath(uri);
            videoView.start();
        }



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //把视频详情界面传过来的被点击视频的位置传递回去
        Intent data = new Intent();
        data.putExtra("position",position);
        setResult(RESULT_OK,data);
        return super.onKeyDown(keyCode, event);
    }
}







