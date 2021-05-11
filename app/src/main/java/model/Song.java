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
        for (int i = tiles.size()-1; i>=0;i--) {

            for (int j =0;j<tiles.get(i).size();j++) {
               stringBuilder.append(tiles.get(i).get(j).toString());
            }

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
