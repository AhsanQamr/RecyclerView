package com.ahsan.recyclerview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class Add extends AppCompatActivity {

    Button button;
    EditText name,number,address;
    ImageView imageView;
    Uri imageUri;
    //Bitmap bitmap;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        button = findViewById(R.id.add_button);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.num);
        address = (EditText) findViewById(R.id.addr);
        imageView = (ImageView) findViewById(R.id.image);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(Add.this);
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(Contract.MyContacts._NAME,name.getText().toString());
                contentValues.put(Contract.MyContacts._PHNO,number.getText().toString());
                contentValues.put(Contract.MyContacts._ADDRESS,address.getText().toString());
                contentValues.put(Contract.MyContacts._IMG, String.valueOf(Uri.parse(imageUri.toString())));
                database.insert(Contract.MyContacts.TABLE_NAME,null,contentValues);
                dbHelper.close();
                finish();
            }

        });
    }

    private void openGallery(){
//        Intent intent = new Intent(Intent.ACTION_PICK);//, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        //intent.setClass(Add.this,MainActivity.class);
//        intent.setType("image/*");
//        intent.putExtra("Image",imageUri);
//        setResult(Activity.RESULT_OK,intent);
//        startActivityForResult(intent,PICK_IMAGE);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select image"),PICK_IMAGE);

        //finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == PICK_IMAGE){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}

//                   imageView.buildDrawingCache();
//                    bitmap = imageView.getDrawingCache();
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream.toByteArray();
//
//
//
//                    Intent returnIntent =  new Intent();
//                    returnIntent.putExtra("name", name.getText().toString());
//                    returnIntent.putExtra("number",number.getText().toString());
//                    returnIntent.putExtra("address",address.getText().toString());
//                    returnIntent.putExtra("image",byteArray);
//                    setResult(Activity.RESULT_OK,returnIntent);
//                    finish();