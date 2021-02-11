package org.fourqwebs.a4qwebapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MyDataBase extends SQLiteOpenHelper {

    String cl0="id";
    String cl1="state";
    String cl2="url";
    String cl3="port";
    public Context context;
    public MyDataBase(Context context ) {
        super(context, "user.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT,url VARCHAR(50) ,port VARCHAR(50),state VARCHAR(10))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table  if exists user");
    }
    public  void insert_data(String state,String url,String port)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(cl1, String.valueOf(state));
        contentValues.put(cl2, String.valueOf(url));
        contentValues.put(cl3, String.valueOf(port));
        sqLiteDatabase.insert("user",null,contentValues);
    }
    public Cursor getallData()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select *  from user" ,null);
        return  cursor;
    }
    public void deletedata()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from user",null);
        if(cursor.getCount()>0)
        {
            sqLiteDatabase.execSQL("Delete  from user");
        }
    }
}
