package com.domsLab.lostsong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import model.Song;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "Dbhelper";
    private static final String TABLE_NAME = "Song";
    private static final String COL1 = "NAME";
    private static final String COL2 = "TILES";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (NAME TEXT PRIMARY KEY, TILES TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean addData(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, song.getName());
        values.put(COL2, song.toString());
        System.out.println(song.getName() + "\n" + song.toString());
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) return false;
        else return true;
    }

    public String getData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME +" where name = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});
        cursor.moveToFirst();
        return cursor.getString(1);
    }
}
