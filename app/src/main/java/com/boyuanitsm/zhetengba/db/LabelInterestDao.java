package com.boyuanitsm.zhetengba.db;

import java.util.ArrayList;
import java.util.List;

import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LabelInterestDao {
    private DBOpenHelp dbOpenHelper;

    public LabelInterestDao(Context context) {

        this.dbOpenHelper = new DBOpenHelp(context);
    }

    /***
     * 添加记录
     *
     * @param labelBannerInfo
     */
    public void save(UserInterestInfo labelBannerInfo) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        /*db.execSQL("Insert into person(name,phone,amount) Values(?,?,?)",
		new Object[]{person.getName(),person.getPhone(),person.getAmount()})*/
        ;
        ContentValues values = new ContentValues();
        values.put("interestId", labelBannerInfo.getInterestId());
        values.put("dictName", labelBannerInfo.getDictName());
        db.insert("label", null, values);//NULL值字段，如果用戶傳遞values爲空集合，
        //db.close();//当应用中只有一个地方使用数据库时，可以不关闭数据库。
    }

    /***
     * 删除记录
     *
     * @param
     */
    public void delete(String id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from label where id=?", new Object[]{id});
        db.delete("label", "id=?", new String[]{id});
    }

    /***
     * 更新记录
     *
     * @param labelBannerInfo
     */
    public void update(UserInterestInfo labelBannerInfo) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("updata  label set interestId=?,dictName=? where id=?", new Object[]{labelBannerInfo.getInterestId(),labelBannerInfo.getDictName(), labelBannerInfo.getId()});
        ContentValues values = new ContentValues();
        values.put("interestId",labelBannerInfo.getInterestId());
        values.put("dictName", labelBannerInfo.getDictName());
        db.update("label", values, "id=?", new String[]{labelBannerInfo.getId()});
    }

    /***
     * 查询记录
     *
     * @param
     */
    public UserInterestInfo find(String id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
	/*db.query("person", null, "personid=?",new String[]{id.toString()} , null, null, null);*/
        Cursor cursor = db.rawQuery("select * from label where id=?", new String[]{id});

        if (cursor.moveToFirst()) {
            String labelid = cursor.getString(cursor.getColumnIndex("id"));
            String interestId=cursor.getString(cursor.getColumnIndex("interestId"));
            String dictName = cursor.getString(cursor.getColumnIndex("dictName"));
//            String icon = cursor.getString(cursor.getColumnIndex("icon"));
//            String dictType = cursor.getString(cursor.getColumnIndex("dictType"));
//            String dictDescribe = cursor.getString(cursor.getColumnIndex("dictDescribe"));
//            String dictCode = cursor.getString(cursor.getColumnIndex("dictCode"));
            return new UserInterestInfo(labelid,interestId, dictName);
        }
        return null;

    }
/***
 * 分页记录
 * @param labelBannerInfo
 */
public List<UserInterestInfo> getScrollData(int offset, int maxResult){
	List<UserInterestInfo> persons=new ArrayList<UserInterestInfo>();
	SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
	/*Cursor cursor=db.query("person", null, null, null, null, null, "person asc",offset+","+maxResult);*/
	Cursor cursor = db.rawQuery("select * from label order by id asc limit ?,?",
			new String[]{String.valueOf(offset),String.valueOf(maxResult)});
	while (cursor.moveToNext()) {
		String id= cursor.getString(cursor.getColumnIndex("id"));
        String interestId=cursor.getString(cursor.getColumnIndex("interestId"));
		  String dictName=cursor.getString(cursor.getColumnIndex("dictName"));
//		  String icon=cursor.getString(cursor.getColumnIndex("icon"));
//		  String dictType=cursor.getString(cursor.getColumnIndex("dictType"));
//        String dictDescribe=cursor.getString(cursor.getColumnIndex("dictDescribe"));
//        String dictCode=cursor.getString(cursor.getColumnIndex("dictCode"));
		  persons.add(new UserInterestInfo(id, interestId,dictName));

	}
	cursor.close();
	return persons;
}

    /***
     * 分页记录查询
     *
     * @param
     */
    public Cursor getCursorData(int offset, int maxResult) {
        List<LabelBannerInfo> persons = new ArrayList<LabelBannerInfo>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
	/*Cursor cursor=db.query("person", null, null, null, null, null, "person asc",offset+","+maxResult);*/
        Cursor cursor = db.rawQuery("select id as _id,interestId,dictName from label order by id asc limit ?,?",
                new String[]{String.valueOf(offset), String.valueOf(maxResult)});

        return cursor;
    }

    /***
     * 获取记录
     *
     * @param
     */
    public long getCount() {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("dictName", new String[]{"count(*)"}, null, null, null, null, null);

	/*Cursor cursor = db.rawQuery("select (*) from person ",null);*/
        long resault = cursor.getLong(0);
        cursor.close();
        return resault;
    }
}
