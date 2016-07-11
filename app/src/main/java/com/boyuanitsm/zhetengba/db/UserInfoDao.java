package com.boyuanitsm.zhetengba.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.utils.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * 个人信息数据库
 * Created by bitch-1 on 2016/5/31.
 */
public class UserInfoDao extends TemplateDAO<UserInfo, String> {
    public UserInfoDao() {
        super(ShUtils.getDbhelper());
    }

    private static UserInfoDao dao;

    private static UserInfoDao getDao() {
        if (dao == null) {
            dao = new UserInfoDao();
        }
        return dao;
    }

    /**
     * 插入用户
     *
     */
    public static void saveUser(UserInfo userInfo) {
        getDao().insert(userInfo);
    }

    /**
     * 获取用户
     *
     * @return
     */
    public static UserInfo getUser() {
        List<UserInfo> list = getDao().find();
        if (list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    /**
     * 更新用户
     *
     * @param userInfo
     */
    public static void updateUser(UserInfo userInfo) {
        SQLiteDatabase db=getDao().getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("pet_name",userInfo.getPetName());
        values.put("home_town",userInfo.getHomeTown());
        values.put("sex",userInfo.getSex());
        values.put("phone",userInfo.getPhone());
        values.put("email",userInfo.getEmail());
        values.put("company_name",userInfo.getCompanyName());
        values.put("company_addr",userInfo.getCompanyAddr());
        values.put("company_phone",userInfo.getCompanyPhone());
        values.put("job",userInfo.getJob());
        values.put("icon",userInfo.getIcon());
        values.put("user_type",userInfo.getUserType());
        db.update(getDao().getTableName(), values, "username=?", new String[]{userInfo.getUsername()});
    }

    /**
     * 删除用户
     *
     */
    public static void deleteUser() {
        getDao().deleteAll();
    }
}


