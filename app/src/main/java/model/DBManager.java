package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LostSongDB";
    private static final String TABLE_NAME = "song";
    private static final String PK = "name";
    private static final String TILES = "tiles";
    private static final String[] COLUMNS = {PK, TILES};


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( " + PK + " TEXT PRIMARY KEY, " + TILES + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public ArrayList<ArrayList<Tile>> getSong(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, " name = ?", new String[]{name}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        System.out.println(cursor.getCount());
        String map = cursor.getString(1);

        ArrayList<ArrayList<Tile>> soundTiles = new ArrayList<>();
        for (int i = 0; i < map.length(); i += 6) {
            ArrayList<Tile> tmp = new ArrayList<>();
            for (int j = i + 0; j < i + 6; j++) {
                if (Integer.parseInt(String.valueOf(map.charAt(j))) == 0)
                    tmp.add(new Tile(false));
                else tmp.add(new Tile(true));
            }
            soundTiles.add(0,tmp);
        }
        return soundTiles;

    }
    public void addSong(Song song){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, " name = ?", new String[]{song.getName()}, null, null, null, null);
        if (cursor != null) {
            System.out.println("Demente c'è già");
            return;
        }
        System.out.println("og no");
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PK,song.getName());
        values.put(TILES,song.toString());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
}
