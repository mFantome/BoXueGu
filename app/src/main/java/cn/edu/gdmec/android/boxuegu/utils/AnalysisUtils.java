package cn.edu.gdmec.android.boxuegu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by student on 17/12/25.
 */

public class AnalysisUtils {
    /**
     * 解析每章习题

     public static List<ExercisesBean> getExercisesInfos(InputStream is) throws Exception {
     XmlPullParser parser = Xml.newPullParser();
     parser.setInput(is,"utf-8");
     List<ExercisesBean> exercisesInfos=null;
     ExercisesBean exercisesInfo = null;
     int type = parser.getEventType();
     while (type != XmlPullParser.END_DOCUMENT){
     switch (type){
     case XmlPullParser.START_TAG:
     if("infos".equals(parser.getName())){
     exercisesInfos = new ArrayList<ExercisesBean>();
     }else if ("exercises".equals(parser.getName())){
     exercisesInfo = new ExercisesBean();
     String ids = parser.getAttributeValue(0);
     exercisesInfo.subjectId = Integer.parseInt(ids);
     }else if ("subject".equals(parser.getName())) {

     }
     }
     }
     }*/

    public static String readLoginUserName(Context context){
        SharedPreferences sp = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userName = sp.getString("loginUserName","");
        return userName;
    }
}
