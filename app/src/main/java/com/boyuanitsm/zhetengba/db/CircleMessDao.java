package com.boyuanitsm.zhetengba.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.utils.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiaoke on 2016/6/21.
 */
public class CircleMessDao extends TemplateDAO<CircleInfo, String> {
    public CircleMessDao() {
        super(ShUtils.getCircleDbhelper());
    }

    private static CircleMessDao dao;

    private static CircleMessDao getDao() {
        if (dao == null) {
            dao = new CircleMessDao();
        }
        return dao;
    }

    /**
     * 插入用户
     */
    public static void saveCircleMess(CircleInfo circleInfo) {
        getDao().insert(circleInfo);
    }

    /**
     * 获取用户
     *
     * @return
     */
    public static List<CircleInfo> getCircleUser() {
        List<CircleInfo> list = getDao().find();
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 更新用户
     *
     * @param circleInfo
     */
    public static void updateCircleUser(CircleInfo circleInfo) {
        SQLiteDatabase db = getDao().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_icon", circleInfo.getUserIcon());
        values.put("pet_name", circleInfo.getPetName());
        values.put("create_time", circleInfo.getCreateTime());
        values.put("mess_type", circleInfo.getMesstype());
        values.put("mess_state", circleInfo.getMessageState());
        values.put("circle_ame", circleInfo.getCircleName());
        values.put("comment_content", circleInfo.getCommentContent());
        values.put("comment_talk", circleInfo.getCommentTalk());
        values.put("cicle_id", circleInfo.getCircleId());
        db.update(getDao().getTableName(), values, "userId=?", new String[]{circleInfo.getUserId()});
    }

}
