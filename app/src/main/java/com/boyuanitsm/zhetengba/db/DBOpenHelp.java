package com.boyuanitsm.zhetengba.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelp extends SQLiteOpenHelper {
/**
 * 指定数据库名称
 * 游标
 * 版本号
 * @param context
 */
	public DBOpenHelp(Context context) {
		super(context, "label.db", null, 1);//指定数据库名称、游标、版本<包>database文件夹下
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//这个方法是数据库第一次创建时调用
		db.execSQL("CREATE TABLE label(id integer primary key autoincrement,interestId varchar(20),dictName varchar(20))");//执行SQL语句创建表
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//这个方法当数据库版本号发生改变时调用
		db.execSQL("ALTER TABLE label ADD dictName VARCHAR(12) NULL");//给表里添加一个phone的字段
	}

}
