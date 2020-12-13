package com.example.Weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db";
    public static final int DATABASE_VERSION = 88;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PROFILE(CITY TEXT ,APIKEY TEXT,PROFILENAME TEXT PRIMARY KEY ,UNIT TEXT,ISDEFAULT BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertWeatherProfile(Weather weather) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CITY", weather.getCityName());
        contentValues.put("APIKEY", weather.getAPIKey());
        contentValues.put("UNIT", weather.getUnit());
        contentValues.put("ISDEFAULT", weather.isDefaultOne());
        contentValues.put("PROFILENAME", weather.getProfileName());
        sqLiteDatabase.insert("PROFILE", null, contentValues);
    }


    public Cursor getAllProfiles() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILE", null);
    }

    public Cursor getDefaultProfile() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILE WHERE ISDEFAULT == 1", null);
    }

    public void setThisProfileDefault(String profile) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        resetDefaultProfile();
        sqLiteDatabase.execSQL("UPDATE PROFILE SET ISDEFAULT = 1 WHERE PROFILENAME = '" + profile + "'");
    }

    public Cursor checkIfThisProfileExists(String profileName) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILE WHERE PROFILENAME ='" + profileName + "'", null);
    }

    public void resetDefaultProfile() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL("UPDATE PROFILE SET ISDEFAULT = 0 WHERE ISDEFAULT =1 ");
    }

    public Cursor getAllProfilesExpectDefault() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILE WHERE ISDEFAULT = 0", null);
    }

    public void updateProfile(Weather weather, String profileName) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CITY", weather.getCityName()); //These Fields should be your String values of actual column names
        cv.put("APIKEY", weather.getAPIKey());
        cv.put("PROFILENAME", weather.getProfileName());
        cv.put("UNIT", weather.getUnit());
        cv.put("ISDEFAULT", weather.isDefaultOne());
        sqLiteDatabase.update("PROFILE", cv, "PROFILENAME = " + profileName, null);
    }


}
