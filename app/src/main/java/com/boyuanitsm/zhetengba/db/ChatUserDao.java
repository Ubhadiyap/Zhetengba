package com.boyuanitsm.zhetengba.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.boyuanitsm.zhetengba.bean.ChatUserBean;
import com.boyuanitsm.zhetengba.utils.CharacterParserUtils;
import com.boyuanitsm.zhetengba.utils.ShUtils;
import com.hyphenate.easeui.domain.EaseUser;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * Created by wangbin on 16/6/24.
 */
public class ChatUserDao extends TemplateDAO<ChatUserBean, String> {

    public ChatUserDao() {
        super(ShUtils.getDbhelper());
    }

    private static ChatUserDao dao;

    private static ChatUserDao getDao() {
        if (dao == null) {
            dao = new ChatUserDao();
        }
        return dao;
    }


    /**
     * 插入用户
     */
    public static void saveUser(ChatUserBean userInfo) {
        if (findUserById(userInfo.getUserId()) == null)
            getDao().insert(userInfo);
        else
            updateUser(userInfo);
    }

    /**
     * 插入List用户
     *
     * @param users
     */
    public static void saveUserList(List<ChatUserBean> users) {
        for (int i = 0; i < users.size(); i++) {
            if (findUserById(users.get(i).getUserId()) == null)
                getDao().insert(users.get(i));
            else
                updateUser(users.get(i));
        }

    }

    /**
     * 更新用户
     *
     * @param userInfo
     */
    public static void updateUser(ChatUserBean userInfo) {
        SQLiteDatabase db = getDao().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nick", userInfo.getNick());
        values.put("icon", userInfo.getIcon());
        db.update(getDao().getTableName(), values, "user_id=?", new String[]{userInfo.getUserId()});
    }

    public static void updateUsers(List<EaseUser> users){
        SQLiteDatabase db = getDao().getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0;i<users.size();i++){
            values.put("nick", users.get(i).getNick());
            values.put("icon",  users.get(i).getAvatar());
            db.update(getDao().getTableName(), values, "user_id=?", new String[]{users.get(i).getUsername()});
        }
    }


    /**
     * 获取用户
     *
     * @param userId
     * @return
     */
    public static EaseUser findUserById(String userId) {
        String selection = "user_id=?";
        String selectionArgs[] = {userId};
        List<ChatUserBean> list = getDao().find(null, selection, selectionArgs, null, null, null, null);
        if (list.size() > 0) {
            ChatUserBean chatUserBean = list.get(0);
            EaseUser easeUser = new EaseUser(chatUserBean.getUserId());
            easeUser.setAvatar(chatUserBean.getIcon());
            easeUser.setNick(chatUserBean.getNick());
            if(!TextUtils.isEmpty(chatUserBean.getNick()))
            easeUser.setInitialLetter(CharacterParserUtils.getInstance().getSelling(chatUserBean.getNick()).substring(0, 1));
            return easeUser;
        } else {
            return null;
        }
    }


    public static void deleteUser() {
        getDao().deleteAll();
    }


}
