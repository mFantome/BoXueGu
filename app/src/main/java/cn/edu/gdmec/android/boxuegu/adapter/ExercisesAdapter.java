package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.MainActivity;
import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ExercisesDetailActivity;
import cn.edu.gdmec.android.boxuegu.activity.LoginActivity;
import cn.edu.gdmec.android.boxuegu.activity.UserInfoActivity;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;

/**
 * Created by student on 17/12/26.
 */

public class ExercisesAdapter extends BaseAdapter{
    private Context mContext;

    public ExercisesAdapter(Context mContext){
        this.mContext = mContext;
    }

    private List<ExercisesBean> eb1;

    /**
     * 设置数据并更新页面
     * @param eb1
     */
    public void setData(List<ExercisesBean> eb1){
        this.eb1 = eb1;
        notifyDataSetChanged();
    }

    /**
     * 获取item的总数
     * @return
     */
    @Override
    public int getCount() {
        return eb1 == null ? 0 : eb1.size();
    }

    /**
     * 根据position得到对应的Item对象
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return eb1 == null ? null : eb1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        final ViewHolder vh;
        //复用converView
        if(converView == null){
            vh = new ViewHolder();
            converView = LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item,null);
            vh.title = converView.findViewById(R.id.tv_title);
            vh.content = converView.findViewById(R.id.tv_content);
            vh.order = converView.findViewById(R.id.tv_order);
            converView.setTag(vh);
        }else {
            vh = (ViewHolder) converView.getTag();
        }
        //获取position对应的item对象
        final ExercisesBean bean = (ExercisesBean) getItem(position);
        if(bean != null){
            vh.order.setText(position + 1 + "");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);

        }
        converView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                //判断是否已经登录
                if (readLoginStatus()){
                    if(bean == null){
                        return;
                    }
                    //跳转到习题详情页
                    Intent intent = new Intent(mContext, ExercisesDetailActivity.class);
                    intent.putExtra("id",bean.id);
                    intent.putExtra("title",bean.title);
                    mContext.startActivity(intent);
                }else {
                    // 跳转到登陆界面
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    mContext.startActivity(intent);
                    //未登录
                    Toast.makeText(mContext,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
                }



            }
        });
        return converView;
    }
    class ViewHolder{
        public TextView title,content;
        public TextView order;
    }
    public boolean readLoginStatus1() {
        MainActivity rs = new MainActivity();
        boolean isLogin = rs.readLoginStatus();
        return isLogin;
    }
    //获取SharedPreferences中的登录状态
    public boolean readLoginStatus() {
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }


}
