package com.boyuanitsm.zhetengba.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.boyuanitsm.zhetengba.bean.NewCircleMess;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.utils.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * Created by xiaoke on 2016/9/19.
 */
public class CircleNewMessDao extends TemplateDAO<NewCircleMess,String> {
    public CircleNewMessDao() {
        super(ShUtils.getDbhelper());
    }
    private static CircleNewMessDao dao;

    private static CircleNewMessDao getDao() {
        if (dao == null) {
            dao = new CircleNewMessDao();
        }
        return dao;
    }
    /**
     * 插入用户
     *
     */
    public static void saveUser(NewCircleMess userInfo) {
        getDao().insert(userInfo);
    }

    /**
     * 获取用户
     *
     * @return
     */
    public static NewCircleMess getUser() {
        List<NewCircleMess> list = getDao().find();
        if (list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
    }
    /**
     * 删除用户
     *
     */
    public static void deleteUser() {
        getDao().deleteAll();
    }
    /**
     * 更新用户
     *
     * @param userInfo
     */
    public static void updateMess(NewCircleMess userInfo) {
        SQLiteDatabase db=getDao().getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("is_main",userInfo.isMain());
        values.put("is_circle",userInfo.isCircle());
        values.put("is_mess",userInfo.isMess());
        db.update(getDao().getTableName(), values, "id=?", new String[]{userInfo.getId()});
    }
}
