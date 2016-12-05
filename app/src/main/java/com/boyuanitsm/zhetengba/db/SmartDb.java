package com.boyuanitsm.zhetengba.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.leaf.library.db.SmartDBHelper;
import com.leaf.library.db.util.TableHelper;

/**
 * Created by xiaoke on 2016/9/19.
 */
public class SmartDb extends SmartDBHelper {

    public SmartDb(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, Class<?>... modelClasses) {
        super(context, databaseName, factory, databaseVersion, modelClasses);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion<5){
            TableHelper.createTablesByClasses(db, ConstantValue.NEWSMODELS);
        }else  if (oldVersion<10){
            TableHelper.createTablesByClasses(db, ConstantValue.NEWSMODELS2);
            db.execSQL("alter table user_table add city varchar(100)");
            db.execSQL("alter table circle_talbe add message varchar(100)");
            db.execSQL("alter table circle_talbe add img_talk varchar(100)");
            db.execSQL("alter table circle_talbe add circle_talk_id varchar(100)");
        }else if (oldVersion==10){
            TableHelper.createTablesByClasses(db, ConstantValue.NEWSMODELS2);
//            db.execSQL("alter table user_table add city varchar(100)");
            db.execSQL("alter table circle_talbe add message varchar(100)");
            db.execSQL("alter table circle_talbe add img_talk varchar(100)");
            db.execSQL("alter table circle_talbe add circle_talk_id varchar(100)");
        }
    }
}
