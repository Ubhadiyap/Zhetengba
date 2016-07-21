package com.boyuanitsm.zhetengba.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.utils.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * Created by xiaoke on 2016/6/23.
 */
public class ActivityMessDao  extends TemplateDAO<ActivityMess,String> {
    public ActivityMessDao() {
        super(ShUtils.getDbhelper());
    }
    private static ActivityMessDao dao;
    private static ActivityMessDao getDao(){
        if(dao==null){
            dao=new ActivityMessDao();
        }
        return dao;
    }
    /**
     * 插入用户
     *
     */
    public static void saveCircleMess(ActivityMess circleInfo){
        getDao().insert(circleInfo);
    }
    /**
     * 获取用户
     *
     * @return
     */
    public static List<ActivityMess> getCircleUser(){
        SQLiteDatabase db=getDao().getWritableDatabase();
        List<ActivityMess> list=getDao().find();
        if (list!=null&&list.size()>0){
            return list;
        }else {
            return null;
        }

    }
    public static ActivityMess getInfo(String id){
        SQLiteDatabase db=getDao().getWritableDatabase();

       return getDao().get(id);
    }

    /**
     * 删除所有
     */
    public static void delAll(){
        getDao().deleteAll();
    }

//    /**
//     * 删除某一项
//     * @param circleId
//     */
//    public static void delCir(String circleId){
//        getDao().delete(circleId);
//    }
    /**
     * 删除其中一条消息
     */
    public static void deleteMes(String createTime){
        SQLiteDatabase db=getDao().getWritableDatabase();
        db.delete(getDao().getTableName(), "create_time=?", new String[]{createTime});
    }
    /**
     * 更新用户
     *
     * @param circleInfo
     */
    public static void updateCircleUser(ActivityMess circleInfo) {
        SQLiteDatabase db=getDao().getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("user_icon",circleInfo.getUserIcon());
        values.put("pet_name",circleInfo.getPetName());
        values.put("create_time",circleInfo.getCreateTime());
        values.put("messtype",circleInfo.getMesstype());
        values.put("message",circleInfo.getMessage());
        values.put("type",circleInfo.getType());
        values.put("activity_theme",circleInfo.getActivityTheme());
        values.put("activity_id",circleInfo.getActivityId());
        values.put("schedule_id",circleInfo.getScheduleId());
        values.put("is_agree",circleInfo.getIsAgree());
        db.update(getDao().getTableName(), values, "user_id=?", new String[]{circleInfo.getUserId()});
    }
}
