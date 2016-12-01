package com.boyuanitsm.zhetengba.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.bean.SquareInfo;
import com.boyuanitsm.zhetengba.utils.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * Created by xiaoke on 2016/11/29.
 */
public class SquareMessDao extends TemplateDAO<SquareInfo,String> {
    public SquareMessDao() {
        super(ShUtils.getDbhelper());
    }

    private static SquareMessDao dao;

    private static SquareMessDao getDao() {
        if (dao == null) {
            dao = new SquareMessDao();
        }
        return dao;
    }

    /**
     * 插入用户
     */
    public static void saveCircleMess(SquareInfo circleInfo) {
        getDao().insert(circleInfo);
    }

    /**
     * 获取用户
     *
     * @return
     */
    public static List<SquareInfo> getCircleUser() {
        List<SquareInfo> list = getDao().find();
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 清空数据库
     */
    public static void dellAll(){
        getDao().deleteAll();
    }
    /**
     * 删除某一项
     * @param createTime
     */
    public static void delCir(String createTime){
        SQLiteDatabase db=getDao().getWritableDatabase();
        db.delete(getDao().getTableName(), "create_time=?", new String[]{createTime});
    }

    /**
     * 更新用户
     *
     * @param circleInfo
     */
    public static void updateCircleUser(SquareInfo circleInfo) {
        SQLiteDatabase db = getDao().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_icon", circleInfo.getUserIcon());
        values.put("pet_name", circleInfo.getPetName());
        values.put("create_time", circleInfo.getCreateTime());
        values.put("messtype", circleInfo.getMesstype());
        values.put("message_state", circleInfo.getMessageState());
        values.put("circle_name", circleInfo.getCircleName());
        values.put("comment_content", circleInfo.getCommentContent());
        values.put("comment_talk", circleInfo.getCommentTalk());
        values.put("circle_id", circleInfo.getCircleId());
        values.put("is_agree",circleInfo.getIsAgree());
        db.update(getDao().getTableName(), values, "user_id=?", new String[]{circleInfo.getUserId()});
    }
}
