package com.domslab.lostsong.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.domslab.model.Song;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SongDao {
    @Insert(onConflict = REPLACE)
    void insertSong(Song song);
    @Query("Select * from Song")
    List<Song> loadAllSong();
    @Query("Select * from song where name = :n")
    Song loadSong(String n);
    @Query("DELETE FROM Song")
    void deleteAll();

}
