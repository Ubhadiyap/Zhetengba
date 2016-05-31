package com.boyuanitsm.zhetengba.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.util.ShUtils;
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
    public static void saveUser(UserInfo userEntity) {
        getDao().insert(userEntity);
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
     * @param userEntity
     */
    public static void updateUser(UserInfo userEntity) {
        SQLiteDatabase db=getDao().getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",userEntity.getName());
        values.put("hometown",userEntity.getHomeTown());
        values.put("sex",userEntity.getSex());
        values.put("phone",userEntity.getPhone());
        values.put("email",userEntity.getEmail());
        values.put("companyname",userEntity.getCompanyName());
        values.put("companyaddr",userEntity.getCompanyAddr());
        values.put("companyphone",userEntity.getCompanyPhone());
        values.put("job",userEntity.getJob());
        values.put("icon",userEntity.getIcon());
        db.update(getDao().getTableName(), values, "username=?", new String[]{userEntity.getUsername()});
    }

    /**
     * 删除用户
     *
     */
    public static void deleteUser() {
        getDao().deleteAll();
    }
}


