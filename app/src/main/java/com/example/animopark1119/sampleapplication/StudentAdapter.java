package com.example.animopark1119.sampleapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class StudentAdapter extends BaseAdapter {

    ArrayList<Student> slist;
    Context c;

    public StudentAdapter(Context c, ArrayList<Student> slist){
      this.c=c;
      this.slist=slist;
    }
    @Override
    public int getCount() {
        return slist.size();
    }

    @Override
    public Object getItem(int position) {
        return slist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf=LayoutInflater.from(c);
        View view=inf.inflate(R.layout.indiview_item,null);
        ImageView img=view.findViewById(R.id.iview_indview);
        TextView title=view.findViewById(R.id.tvTitle);
        TextView desc=view.findViewById(R.id.tvDesc);
        TextView timeStamp=view.findViewById(R.id.tvTimeStamp);
        Student pos=slist.get(position);


         img.setImageURI(Uri.parse(pos.img));
        title.setText(pos.name);
        desc.setText(pos.descr);
        timeStamp.setText(pos.time);

        return view;
    }
}
