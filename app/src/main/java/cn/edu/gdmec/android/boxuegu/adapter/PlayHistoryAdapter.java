package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.VideoPlayActivity;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;

/**
 * Created by acer on 2018/1/2.
 */

public class PlayHistoryAdapter extends BaseAdapter{
    private Context context;
    private List<VideoBean> vbl;

    public PlayHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VideoBean> vbl){
        this.vbl = vbl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vbl == null?0:vbl.size();
    }

    @Override
    public Object getItem(int i) {
        return vbl == null?0:vbl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        final ViewHolder vh;
        if(converView == null){
            vh = new ViewHolder();
            converView = LayoutInflater.from(context).inflate(R.layout.play_history_list_item,null);
            vh.tv_title = converView.findViewById(R.id.tv_adapter_title);
            vh.tv_video_title = converView.findViewById(R.id.tv_video_title);
            vh.iv_icon = converView.findViewById(R.id.iv_video_icon);
            converView.setTag(vh);
        }else {
            vh = (ViewHolder) converView.getTag();
        }
        final VideoBean bean = (VideoBean) getItem(position);
        if(bean!=null){
            vh.tv_title.setText(bean.title);
            vh.tv_video_title.setText(bean.secondTitle);
            switch (bean.chapterId){
                case 1:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon1);
                    break;
                case 2:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon2);
                    break;
                case 3:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon3);
                    break;
                case 4:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon4);
                    break;
                case 5:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon5);
                    break;
                case 6:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon6);
                    break;
                case 7:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon7);
                    break;
                case 8:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon8);
                    break;
                case 9:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon9);
                    break;
                case 10:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon10);
                    break;
            }
        }
        converView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(bean == null) return;
                //跳转到视频播放界面
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("videoPath" , bean.videoPath);
                context.startActivity(intent);
            }
        });
        return converView;
    }
    class ViewHolder{
        TextView tv_title;
        TextView tv_video_title;
        ImageView iv_icon;
    }
}
