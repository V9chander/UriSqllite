package com.example.animopark1119.sampleapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    Uri uri;
    ImageView img;
    File file;
   DbHelper db;
   String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         img = findViewById(R.id.iview);
         Button save=(Button) findViewById(R.id.bInsert);
        Button show=(Button) findViewById(R.id.bShow);
        final EditText title=findViewById(R.id.etTitle);
        final EditText desc=findViewById(R.id.etDesc);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new
                        AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Select Any one");

                alertDialogBuilder.setPositiveButton("Files", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_PICK);
                        i.setType("image/*");
                        startActivityForResult(Intent.
                                createChooser(i,"Select Picture"), 0);
                    }
                });
                alertDialogBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 1);
                    }
                });

                alertDialogBuilder.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues cv= new ContentValues();
                DbHelper db=new DbHelper(MainActivity.this);

                Student s=new Student(getRealPathFromURI(uri),title.getText().toString(),desc.getText().toString());
                Long result=db.insertData(s);
                if (result!=-1){
                    Toast.makeText(MainActivity.this,"Inserted",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK) {
                    uri = data.getData();
                    img.setImageURI(uri);


                }
                break;
            case 1:
                if(resultCode == RESULT_OK){

                    Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                    /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);*/
                    img.setImageBitmap(bitmap);
                   uri=getImageUri(bitmap);
                }
                break;
        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public Uri getImageUri(Bitmap src) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 50/100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), src, "title", null);
        return Uri.parse(path);
    }
    }

