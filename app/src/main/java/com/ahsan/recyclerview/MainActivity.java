package com.ahsan.recyclerview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Modal> ls;
    Adapter adapter;
    Button add_button;
    ImageView imageView;
    Bitmap bitmap;
    private static final int ADD_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_button = findViewById(R.id.add);
        recyclerView = findViewById(R.id.rec_view);
        imageView = (ImageView) findViewById(R.id.image_view);

        ls = new ArrayList<>();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add.class);
               //startActivityForResult(intent,ADD_ACTIVITY_REQUEST_CODE);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new Adapter(getData(),MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @SuppressLint("Range")
    public List<Modal> getData(){
        List<Modal> list = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] columns = {Contract.MyContacts._ID,Contract.MyContacts._NAME,Contract.MyContacts._PHNO,Contract.MyContacts._ADDRESS,Contract.MyContacts._IMG};
        Cursor cursor = database.query(
                Contract.MyContacts.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                Contract.MyContacts._NAME+" ASC");
        while (cursor.moveToNext()){
            list.add(new Modal(
                    cursor.getString(cursor.getColumnIndex(Contract.MyContacts._NAME)),
                    cursor.getString(cursor.getColumnIndex(Contract.MyContacts._PHNO)),
                    cursor.getString(cursor.getColumnIndex(Contract.MyContacts._ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(Contract.MyContacts._IMG))
            ));
        }
        return list;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String name = data.getStringExtra("name");
                String number = data.getStringExtra("number");
                String address = data.getStringExtra("address");

                String image = null;
                Bundle extras = getIntent().getExtras();
                if(extras!=null && extras.containsKey("Image")){
                    Uri uri = Uri.parse(extras.getString("Image")); //extras.getString("Image");
                    image = uri.toString();
                }

                ls.add(new Modal(name,number,address,image));
                recyclerView.setAdapter(adapter);

            }
        }

    }

}