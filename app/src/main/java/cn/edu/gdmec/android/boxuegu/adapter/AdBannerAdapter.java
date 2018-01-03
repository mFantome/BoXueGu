package cn.edu.gdmec.android.boxuegu.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


import cn.edu.gdmec.android.boxuegu.bean.CourseBean;
import cn.edu.gdmec.android.boxuegu.fragment.AdBannerFragmet;
import cn.edu.gdmec.android.boxuegu.view.CourseView;

/**
 * Created by acer on 2018/1/1.
 */

public class AdBannerAdapter extends FragmentStatePagerAdapter implements View.OnTouchListener {

    private List<CourseBean> cadl;
    private Handler mHandler;

    public AdBannerAdapter(FragmentManager fm) {
        super(fm);
        cadl = new ArrayList<CourseBean>();
    }

    public AdBannerAdapter(FragmentManager fm, Handler handler) {
        super(fm);
        cadl = new ArrayList<CourseBean>();
        mHandler = handler;
    }

    /**
     * 设置数据，更新界面
     */
    public void setDatas(List<CourseBean> cadl){
        this.cadl = cadl;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        //防止刷新结果显示列表时出现缓存数据，重载此方法，使之默认返回POSITION_NONE
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        if(cadl.size()>0){
            args.putString("ad",cadl.get(position % cadl.size()).icon);
        }
        return AdBannerFragmet.newInstance(args);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public int getSize(){
        return cadl == null ? 0 : cadl.size();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mHandler.removeMessages(CourseView.MSG_AD_SLID);
        return false;
    }
}
