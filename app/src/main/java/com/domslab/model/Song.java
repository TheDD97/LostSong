package com.domslab.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Song {
    @PrimaryKey
    @NonNull
    private String name;
    @NonNull
    private String tiles;
    @Ignore
    private ArrayList<ArrayList<Tile>> soundMap;


    public Song(@NonNull String name, @NonNull String tiles) {
        this.name = name;
        this.tiles = tiles;
        soundMap = new ArrayList<>();
        System.out.println(tiles);
        for (int i = tiles.length() - 1; i >= 0; i -= 6) {
            ArrayList<Tile> tmp = new ArrayList();
            for (int j = i; j > i - 6; j--)
                if (Integer.parseInt(String.valueOf(tiles.charAt(j))) == 1)
                    tmp.add(new Tile(true));
                else tmp.add(new Tile(false));
            soundMap.add(0, tmp);
        }
    }

    @NonNull
    public String getTiles() {
        return tiles;
    }

    public void setTiles(@NonNull String tiles) {
        this.tiles = tiles;
    }

    public ArrayList<ArrayList<Tile>> getSoundMap() {
        return soundMap;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles.toString();
    }

    public void addRow(ArrayList<Tile> row) {
        soundMap.add(0, row);
    }

    @Override
    public String toString() {
        return tiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
