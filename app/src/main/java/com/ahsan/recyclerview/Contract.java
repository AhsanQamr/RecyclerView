package com.ahsan.recyclerview;

import android.provider.BaseColumns;

public class Contract {

    private Contract(){}

    public static class MyContacts implements BaseColumns{
        public static String TABLE_NAME = "Contacts";
        public static String _NAME = "name";
        public static String _PHNO = "number";
        public static String _ADDRESS = "address";
        public static String _IMG = "image";

    }

}
