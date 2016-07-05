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
	private static SmartDBHelper labelDbHelper;
	private static DBOpenHelp labelDbOpenHelp;
	private static SmartDBHelper circleDbHelper;
	private static DBOpenHelp circleDbOpenHelper;
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
//	public static SmartDBHelper getLabelDbhelper(){
//		if(labelDbHelper==null){
//			labelDbHelper=new SmartDBHelper(getApplicationContext(),
//					ConstantValue.LABEL_DB_NAME,null,ConstantValue.LABEL_VERSION,ConstantValue.LABEL_MODELS);
//		}
//		return labelDbHelper;
//	}
//	public static DBOpenHelp getLabelDbOpenHelp(){
//		if (labelDbOpenHelp==null){
//			labelDbOpenHelp=new DBOpenHelp(getApplicationContext());
//		}
//		return labelDbOpenHelp;
//	}
//	public static SmartDBHelper getCircleDbhelper(){
//		if(circleDbHelper==null){
//			circleDbHelper=new SmartDBHelper(getApplicationContext(),
//					ConstantValue.CIRCLE_DB_NAME,null,ConstantValue.CIRCLE_VERSION,ConstantValue.CIRCLE_MODELS);
//		}
//		return circleDbHelper;
//	}
//	public static DBOpenHelp getCircleDbOpenHelper(){
//		if (circleDbOpenHelper==null){
//			circleDbOpenHelper=new DBOpenHelp(getApplicationContext());
//		}
//		return circleDbOpenHelper;
//	}
	
	public static MyApplication getApplication(){
		return MyApplication.getInstance();
	}
}
