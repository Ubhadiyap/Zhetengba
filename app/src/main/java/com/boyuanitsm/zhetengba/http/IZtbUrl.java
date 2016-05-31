package com.boyuanitsm.zhetengba.http;

/**
 * 基础URl
 * Created by wangbin on 16/5/30.
 */
public interface IZtbUrl {

    //基础地址
    public static final String BASE_URL = "http://172.16.6.253:8089/zhetengba/";

    public static final String LOGIN_URL = BASE_URL + "";

    /**=======档期 =======*/
    //banner轮播图
    public static final String BANNER_URL=BASE_URL+"schedule/login/findActivityBanner.do";
    //活动列表
    public static final String ACTIVITY_LIST=BASE_URL+"schedule/login/findActivityList.do";
    //活动详情
    public static final String ACTIVITY_DETIALS=BANNER_URL+"schedule/login/findActivityDetails.do";

}
