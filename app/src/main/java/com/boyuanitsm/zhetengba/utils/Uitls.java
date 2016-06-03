package com.boyuanitsm.zhetengba.utils;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wanggang
 * 
 */
public class Uitls {

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 实体类转换成map
	 * @param obj
	 * @return
	 */
	public static Map<String,String> obj2Map(Object obj){
		Map<String,String> map=new HashMap<>();
		try {
			JSONObject object=new JSONObject(GsonUtils.bean2Json(obj));
			Iterator iterator=object.keys();
			while (iterator.hasNext()){
				String key= (String) iterator.next();
				String value=object.getString(key);
				if (!TextUtils.isEmpty(value)){
					map.put(key,value);
				}


			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

}
