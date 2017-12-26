package cn.edu.gdmec.android.boxuegu.bean;

/**
 * Created by student on 17/12/25.
 */

public class ExercisesBean {
    public int id;//每章习题id
    public String title;//每章习题标题
    public String content;//每章习题的数目
    public int background;//每章习题前面的序号背景
    public int subjectId;//每道习题的id
    public String subject;//每道习题的题干
    public String a;//A选项
    public String b;//
    public String c;//
    public String d;//
    public int answer;//每道习题的正确答案
    public int select;//用户选中的项（0表示所选项对了，1表示A选项错，2表示B选项错，3表示C选项错，4表示D选项错）

}
