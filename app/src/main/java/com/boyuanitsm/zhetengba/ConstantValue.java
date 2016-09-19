package com.boyuanitsm.zhetengba;


import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.bean.ChatUserBean;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.bean.NewCircleMess;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;

/**
 * 全局常量
 * Created by wangbin on 16/1/12.
 */
public class ConstantValue {
    /**
     * 正式上线后，关闭log，改为false
     */
    public  static final boolean IS_SHOW_DEBUG = true;

    /*编码格式*/
    public static final String ENCODING="UTF-8";
    /**
     * 首页简约/档期广播
     */

    /**
    /**
     * 数据库版本
     */
    public final static int VERSION =4;
   public final static Class<?>[] MODELS = {UserInfo.class, ChatUserBean.class,UserInterestInfo.class,CircleInfo.class, ActivityMess.class,NewCircleMess.class};
    public final static String DB_NAME = "user.db";
}
