package com.boyuanitsm.zhetengba.db;

import java.util.ArrayList;
import java.util.List;

import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LabelInfoDao {
    private DBOpenHelp dbOpenHelper;

    public LabelInfoDao(Context context) {

        this.dbOpenHelper = new DBOpenHelp(context);
    }

    /***
     * 添加记录
     *
     * @param labelBannerInfo
     */
    public void save(LabelBannerInfo labelBannerInfo) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        /*db.execSQL("Insert into person(name,phone,amount) Values(?,?,?)",
		new Object[]{person.getName(),person.getPhone(),person.getAmount()})*/
        ;
        ContentValues values = new ContentValues();
        values.put("id", labelBannerInfo.getId());
        values.put("dictName", labelBannerInfo.getDictName());
        values.put("icon", labelBannerInfo.getIcon());
        values.put("dictType", labelBannerInfo.getDictType());
        values.put("dictDescribe", labelBannerInfo.getDictDescribe());
        values.put("dictCode", labelBannerInfo.getDictCode());
        db.insert("label", null, values);//NULL值字段，如果用戶傳遞values爲空集合，
        //db.close();//当应用中只有一个地方使用数据库时，可以不关闭数据库。
    }

    /***
     * 删除记录
     *
     * @param
     */
    public void delete(Integer id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from label where id=?", new Object[]{id});
        db.delete("label", "id=?", new String[]{id.toString()});
    }

    /***
     * 更新记录
     *
     * @param labelBannerInfo
     */
    public void update(LabelBannerInfo labelBannerInfo) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("updata  label set dictName=?,icon=?,dictType=?,dictDescribe=?,dictCode=? where id=?", new Object[]{labelBannerInfo.getDictName(), labelBannerInfo.getIcon(), labelBannerInfo.getDictType(), labelBannerInfo.getDictDescribe(), labelBannerInfo.getDictCode(), labelBannerInfo.getId()});
        ContentValues values = new ContentValues();
        values.put("dictName", labelBannerInfo.getDictName());
        values.put("icon", labelBannerInfo.getIcon());
        values.put("dictType", labelBannerInfo.getDictType());
        values.put("dictDescribe", labelBannerInfo.getDictDescribe());
        values.put("dictCode", labelBannerInfo.getDictCode());
        db.update("label", values, "id=?", new String[]{labelBannerInfo.getId().toString()});
    }

    /***
     * 查询记录
     *
     * @param
     */
    public LabelBannerInfo find(String id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
	/*db.query("person", null, "personid=?",new String[]{id.toString()} , null, null, null);*/
        Cursor cursor = db.rawQuery("select * from person where id=?", new String[]{id.toString()});

        if (cursor.moveToFirst()) {
            String labelid = cursor.getString(cursor.getColumnIndex("id"));
            String dictName = cursor.getString(cursor.getColumnIndex("dictName"));
            String icon = cursor.getString(cursor.getColumnIndex("icon"));
            String dictType = cursor.getString(cursor.getColumnIndex("dictType"));
            String dictDescribe = cursor.getString(cursor.getColumnIndex("dictDescribe"));
            String dictCode = cursor.getString(cursor.getColumnIndex("dictCode"));
            return new LabelBannerInfo(labelid, dictName, icon, dictType, dictDescribe, dictCode);
        }
        return null;

    }
/***
 * 分页记录
 * @param labelBannerInfo
 */
public List<LabelBannerInfo> getScrollData(int offset, int maxResult){
	List<LabelBannerInfo> persons=new ArrayList<LabelBannerInfo>();
	SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
	/*Cursor cursor=db.query("person", null, null, null, null, null, "person asc",offset+","+maxResult);*/
	Cursor cursor = db.rawQuery("select * from person order by personid asc limit ?,?",
			new String[]{String.valueOf(offset),String.valueOf(maxResult)});
	while (cursor.moveToNext()) {
		String id= cursor.getString(cursor.getColumnIndex("id"));
		  String dictName=cursor.getString(cursor.getColumnIndex("dictName"));
		  String icon=cursor.getString(cursor.getColumnIndex("icon"));
		  String dictType=cursor.getString(cursor.getColumnIndex("dictType"));
        String dictDescribe=cursor.getString(cursor.getColumnIndex("dictDescribe"));
        String dictCode=cursor.getString(cursor.getColumnIndex("dictCode"));
		  persons.add(new LabelBannerInfo(id, dictName, icon, dictType, dictDescribe, dictCode));

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
        Cursor cursor = db.rawQuery("select id as _id,dictName, icon, dictType, dictDescribe, dictCode from label order by id asc limit ?,?",
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
