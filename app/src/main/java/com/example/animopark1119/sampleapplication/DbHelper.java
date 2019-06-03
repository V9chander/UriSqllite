package com.example.animopark1119.sampleapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "students";
    public  final String COL_ID = "stuid";
    public  final String COL_IMG = "image";
    public  final String COL_NAME = "name";
    public final String COL_DESC= "descr";
    public final String COL_TIME= "times";
    //String createTable = "CREATE TABLE " + TABLE_NAME +" (stuid INTEGER PRIMARY KEY AUTOINCREMENT, image TEXT, name TEXT, descr TEXT)";

    private static final int DATABASE_VERSION = 2;



    /* String createTable= "create table if not exists "
             + TABLE_NAME
             + " (_id integer primary key autoincrement,"
             + COL_IMAGE +"text,"
             + COL_NAME +"text, "
             + COL_DESC + "text);"; */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME +" (id INTEGER PRIMARY KEY AUTOINCREMENT,image TEXT,name TEXT,descr TEXT,times DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " +TABLE_NAME);
    }
    Long insertData(Student sObj){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(COL_IMG,sObj.img);
        cv.put(COL_NAME,sObj.name);
        cv.put(COL_DESC,sObj.descr);


        Long result=db.insert(TABLE_NAME,null,cv);
        return result;
    }
Cursor readData() {

    String selectQuery = "SELECT  * FROM " + TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    return cursor;
}



}
