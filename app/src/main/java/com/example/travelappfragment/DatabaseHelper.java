package com.example.travelappfragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "travel.db";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "travel.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT)");
        MyDatabase.execSQL("create Table place(title TEXT primary key, address TEXT," +
                "time TEXT, price TEXT, describe TEXT)");
        MyDatabase.execSQL("create Table profile(id integer primary key AUTOINCREMENT," +
                " avatar blob, name text, password text," +
                "nickname text, phone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists place");
        MyDB.execSQL("drop Table if exists profile");
    }

    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean insertPlace (String title, String address, String time, String price, String describe){
        SQLiteDatabase myDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("address",address);
        contentValues.put("time",time);
        contentValues.put("price",price);
        contentValues.put("describe",describe);

        long result = myDatabase.insert("place", null, contentValues);

        if(result != -1) {
           return true;
        } else {
            return false;
        }
    }



}
