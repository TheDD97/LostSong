package com.domslab.lostsong.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.domslab.lostsong.database.DAO.SongDao;
import com.domslab.model.Song;

@Database(entities = {Song.class}, version = 1, exportSchema = true)
public abstract class DbManager extends RoomDatabase {
    private static DbManager instance;

    public abstract SongDao songModel();

    public static DbManager getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DbManager.class, "song_db").allowMainThreadQueries().build();
        }
        return instance;
    }

    private static void checkSong() {
    }

    public static void destroyInstance() {
        instance = null;
    }
}
