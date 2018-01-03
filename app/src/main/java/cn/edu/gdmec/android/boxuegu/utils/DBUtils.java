package cn.edu.gdmec.android.boxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;
import cn.edu.gdmec.android.boxuegu.sqlite.SqlLiteHelper;


/**
 * Created by lenovo on 2018/1/1.
 */

public class DBUtils {
    private static DBUtils instance = null;
    private static SqlLiteHelper helper;
    private static SQLiteDatabase db;

    public DBUtils(Context context) {
        helper = new SqlLiteHelper (context);
        db = helper.getWritableDatabase();
    }

    public static DBUtils getInstance(Context context){
        if( instance == null ){
            instance = new DBUtils(context);
        }
        return instance;
    }
    //保存个人资料信息
    public void saveUserInfo(UserBean bean){
        ContentValues cv = new ContentValues ();
        cv.put("userName", bean.userName);
        cv.put("nickName", bean.nickName);
        cv.put("sex", bean.sex);
        cv.put("signature", bean.signature);
        db.insert(SqlLiteHelper.U_USERINFO, null, cv);

    }
    //获取个人资料信息
    public UserBean getUserInfo(String userName){
        String sql = "SELECT * FROM " + SqlLiteHelper.U_USERINFO + " WHERE userName=?";
        Cursor cursor = db.rawQuery(sql,new String[]{userName});
        UserBean bean = null;
        while (cursor.moveToNext()){
            bean = new UserBean();
            bean.userName = cursor.getString(cursor.getColumnIndex("userName"));
            bean.nickName = cursor.getString(cursor.getColumnIndex("nickName"));
            bean.sex = cursor.getString(cursor.getColumnIndex("sex"));
            bean.signature = cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return bean;
    }
    //修改个人资料
    public void updateUserInfo(String key, String value, String userName){
        ContentValues cv = new ContentValues();
        cv.put(key,value);
        db.update(SqlLiteHelper.U_USERINFO, cv, "userName=?", new String[]{ userName });
    }


    public void saveVideoPlayList(VideoBean bean, String userName) {
        //判断如果里面已经有此记录则需要先删除重新存放
        if(hasVideoPlay(bean.chapterId,bean.chapterId,userName)){
            //删除之前存入的播放记录
            boolean isDelete = delVideoPlay(bean.chapterId,bean.videoId,userName);
            if(!isDelete){
                //没有删除成功
                return;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put("userName",userName);
        cv.put("chapterId",bean.chapterId);
        cv.put("videoId",bean.videoId);
        cv.put("videoPath",bean.videoPath);
        cv.put("title",bean.title);
        cv.put("secondTitle",bean.secondTitle);
        db.insert(SqlLiteHelper.U_VIDEO_PLAY_LIST,null,cv);
    }

    private boolean delVideoPlay(int chapterId, int videoId, String userName) {
        boolean delSuccess = false;
        int row = db.delete(SqlLiteHelper.U_VIDEO_PLAY_LIST," chapterId=? AND videoId = ? AND userName = ?",
                new String[]{chapterId  + "",videoId+"",userName});
        if (row > 0){
            delSuccess =true;

        }
        return delSuccess;
    }

    private boolean hasVideoPlay(int chapterId, int videoId, String userName) {
        boolean hasVideo = false;
        String sql = "SELECT * FROM "+SqlLiteHelper.U_VIDEO_PLAY_LIST+" WHERE chapterId=? AND videoId = ? AND userName = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{chapterId  + "",videoId+"",userName});
        if(cursor.moveToFirst()){
            hasVideo = true;
        }
        cursor.close();
        return hasVideo;
    }

    public ArrayList<VideoBean> getVideoHistory(String userName) {
        String sql = "SELECT * FROM "+SqlLiteHelper.U_VIDEO_PLAY_LIST + " WHERE userName = ?";
        Cursor cusor = db.rawQuery(sql,new String[]{userName});
        ArrayList<VideoBean> vbl = new ArrayList<>();
        VideoBean bean = null;
        while (cusor.moveToNext()){
            bean = new VideoBean();
            bean.chapterId = cusor.getInt(cusor.getColumnIndex("chapterId"));
            bean.videoId = cusor.getInt(cusor.getColumnIndex("videoId"));
            bean.videoPath = cusor.getString(cusor.getColumnIndex("videoPath"));
            bean.title = cusor.getString(cusor.getColumnIndex("title"));
            bean.secondTitle = cusor.getString(cusor.getColumnIndex("secondTitle"));
            vbl.add(bean);
            bean = null;

        }
        cusor.close();
        return vbl;
    }
}
