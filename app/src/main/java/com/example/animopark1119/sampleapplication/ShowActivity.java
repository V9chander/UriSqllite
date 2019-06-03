package com.example.animopark1119.sampleapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    ArrayList<Student> slist=new ArrayList<>();
    DbHelper db=new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
         getAllData();
        ListView list =  findViewById(R.id.lview);
        StudentAdapter sAdapter = new StudentAdapter(ShowActivity.this,slist);
        list.setAdapter(sAdapter);

    }
    void getAllData(){
        Cursor cursor=db.readData();
        if (cursor.moveToFirst()) {
            do {
                Student s=new Student();
                DbHelper data=new DbHelper(this);
                if ( !cursor.isNull( cursor.getColumnIndex(data.COL_ID) ) )
                s.id=cursor.getInt(cursor.getColumnIndex(data.COL_ID));
                s.img=cursor.getString(cursor.getColumnIndex(data.COL_IMG));
                s.name=cursor.getString(cursor.getColumnIndex(data.COL_NAME));
                s.descr=cursor.getString(cursor.getColumnIndex(data.COL_DESC));
                if ( !cursor.isNull( cursor.getColumnIndex(data.COL_TIME) ) )
                 s.time=cursor.getString(cursor.getColumnIndex(data.COL_TIME));
                slist.add(s);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
