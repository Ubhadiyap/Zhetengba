package com.boyuanitsm.zhetengba.http.manager;

import android.text.TextUtils;

import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 频道管理
 * Created by wangbin on 16/5/31.
 */
public class TalkManager extends RequestManager{

    /**
     * 建立圈子
     * @param circleEntity
     * @param personIds
     */
    public void addCircle(CircleEntity circleEntity,String personIds,ResultCallback callBack){
        Map<String,String> map=new HashMap<>();
        map.put("circleName",circleEntity.getCircleName());
        if(!TextUtils.isEmpty(circleEntity.getNotice())) {
            map.put("notice", circleEntity.getNotice());
        }
        if(!TextUtils.isEmpty(personIds)){
            map.put("personIds",personIds);
        }
        doPost(IZtbUrl.CREATE_CIRCLE_URL,map,callBack);
    }


    /**
     * 我的圈子列表
     * @param page
     * @param rows
     * @param callback
     */
    public void myCircleList(int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.CIRCLE_LIST_URL,map,callback);
    }

    /**
     * 圈子详情
     * @param circleId
     * @param callback
     */
    public void myCircleDetail(String circleId ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleId)) {
            map.put("circleId", circleId);
        }
        doPost(IZtbUrl.CIRCLE_DETAIL_URL,map,callback);
    }

}
