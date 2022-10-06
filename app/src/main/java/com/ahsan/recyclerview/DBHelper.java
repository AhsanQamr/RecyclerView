package com.ahsan.recyclerview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public static String DBNAME = "Contact.db";
    public static int Version = 2;
    // query
    public static String CREATE_CONTACTS_TABLE=" CREATE TABLE "+Contract.MyContacts.TABLE_NAME +
            "( "+Contract.MyContacts._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+ Contract.MyContacts._NAME+" TEXT, "+
            Contract.MyContacts._PHNO+" TEXT NOT NULL, "+
            Contract.MyContacts._ADDRESS +" TEXT, "+
            Contract.MyContacts._IMG +" TEXT NOT NULL "+
        ");";

    public static String DROP_CONTACTS_TABLE = "DROP TABLE IF EXISTS "+ Contract.MyContacts.TABLE_NAME;




    public DBHelper(@Nullable Context context) {
        super(context,DBNAME,null,Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_CONTACTS_TABLE);
        onCreate(sqLiteDatabase);
    }
}
