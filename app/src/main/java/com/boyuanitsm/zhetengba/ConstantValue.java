package com.boyuanitsm.zhetengba;


import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.bean.ChatUserBean;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
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
    /*测试图片地址*/
    public static final String IMAGEURL="http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1402/12/c1/31189058_1392186616852.jpg";
    public static final String IMAGEURL2="http://pic41.nipic.com/20140509/4746986_145156378323_2.jpg";
    public static final String IMAGEURL3="http://img05.tooopen.com/images/20140604/sy_62331342149.jpg";
    public static final String IMAGEURL4="http://pic36.nipic.com/20131217/6704106_233034463381_2.jpg";
    public static final String IMAGEURL5="http://pic55.nipic.com/file/20141208/19462408_171130083000_2.jpg";

    /**
     * 首页简约/档期广播
     */

    /**
    /**
     * 数据库版本
     */
    public final static int VERSION =4;
   public final static Class<?>[] MODELS = {UserInfo.class, ChatUserBean.class,UserInterestInfo.class,CircleInfo.class, ActivityMess.class};
    public final static String DB_NAME = "user.db";
//
//    /**
//     * 个人兴趣标签版本
//     */
//    public final static int LABEL_VERSION =2;
//    public final static Class<?>[] LABEL_MODELS = {};
//    public final static String LABEL_DB_NAME = "userInterestInfo.db";
//    /**
//     * 个人兴趣标签版本
//     */
//    public final static int CIRCLE_VERSION =3;
//    public final static Class<?>[] CIRCLE_MODELS = {};
//    public final static String CIRCLE_DB_NAME = "circleinfo.db";
}
