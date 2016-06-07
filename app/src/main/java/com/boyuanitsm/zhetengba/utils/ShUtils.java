package com.boyuanitsm.zhetengba.utils;

import android.content.Context;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.db.DBOpenHelp;
import com.leaf.library.db.SmartDBHelper;


/**
 * 数据库操作辅助类
 * @author wangbin
 *
 */
public class ShUtils {
   
	private static SmartDBHelper dbHelper;
	private static DBOpenHelp dbOpenHelp;
	public static Context getApplicationContext(){
		return getApplication().getApplicationContext();
	}
	
	public static SmartDBHelper getDbhelper(){
		if(dbHelper==null){
			dbHelper=new SmartDBHelper(getApplicationContext(),
					ConstantValue.DB_NAME,null,ConstantValue.VERSION,ConstantValue.MODELS);
		}
		return dbHelper;
	}
	public static DBOpenHelp getDbOpenHelp(){
		if (dbOpenHelp==null){
			dbOpenHelp=new DBOpenHelp(getApplicationContext());
		}
		return dbOpenHelp;
	}
	
	public static MyApplication getApplication(){
		return MyApplication.getInstance();
	}
}
