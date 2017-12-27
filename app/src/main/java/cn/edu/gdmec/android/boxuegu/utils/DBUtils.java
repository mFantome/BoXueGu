package cn.edu.gdmec.android.boxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.sqlite.SqlLiteHelper;

/**
 * Created by student on 17/12/27.
 */

public class DBUtils {

    private static SqlLiteHelper helper;
    private static SQLiteDatabase db;
    private static  DBUtils instance = null;


    private DBUtils(Context context){
        helper = new SqlLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public static DBUtils getInstance(Context context){
        if(instance ==null){
            instance = new DBUtils(context);
        }
        return instance;
    }

    public void saveUserInfo(UserBean bean){
        ContentValues cv = new ContentValues();
        cv.put("userName",bean.userName);
        cv.put("nickName",bean.nickName);
        cv.put("sex",bean.sex);
        cv.put("signature",bean.signature);

        db.insert(SqlLiteHelper.U_USERINFO,null, cv);
    }

    public UserBean getUserInfo(String userName){
        String sql = "SELECT * FROM"+SqlLiteHelper.U_USERINFO+"WHERE userName = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
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
    public void updateUserInfo(String key,String value,String userName){
        ContentValues cv = new ContentValues();
        cv.put(key,value);
        db.update(SqlLiteHelper.U_USERINFO,cv,"userName =?",new String[]{userName});
    }
}
