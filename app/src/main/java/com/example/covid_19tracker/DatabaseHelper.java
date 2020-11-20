package com.example.covid_19tracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "COVID-19.db";
    public static final String TABLE_NAME= "Patients_table";
    public static final String COL_1= "ID";
    public static final String COL_2= "NAME";
    public static final String COL_3= "PHONE_NUMBER";
    public static final String COL_4= "MAC_ADDRESS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "NAME TEXT,"
                + "PHONE_NUMBER INTEGER,"
                + "MAC_ADDRESS TEXT) ");

        db.execSQL("INSERT INTO " + TABLE_NAME+" VALUES (1,'Hussein',71263100,'00:57:0c:58:4d:ea')");
        db.execSQL("INSERT INTO " + TABLE_NAME+" VALUES (2,'Samir',70486178,'00:44:0d:47:s5:77')");
        db.execSQL("INSERT INTO " + TABLE_NAME+" VALUES (3,'Hasan',70451236,'0c:77:0e:sd:44:33')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    onCreate(db);
    }

    public Cursor getAllData(){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
    }
}
