package model;

import java.util.ArrayList;

public class Song {
    private String name;
    private ArrayList<ArrayList<Tile>> tiles;

    public Song(String name,ArrayList<ArrayList<Tile>> tiles) {
        this.name=name;
        this.tiles = tiles;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }
    public void addRow(ArrayList<Tile> row){
        tiles.add(0,row);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ArrayList<Tile> tile : tiles) {
            StringBuilder s = new StringBuilder();
            for (Tile tile1 : tile) {
                s.append(tile1.toString());
            }
            stringBuilder.insert(0,s);
        }
        return stringBuilder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
