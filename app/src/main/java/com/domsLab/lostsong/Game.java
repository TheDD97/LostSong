package com.domsLab.lostsong;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.lostsong.R;
import model.Settings;
import model.Tile;

import java.util.ArrayList;

public class Game {

    private ArrayList<ArrayList<Tile>> soundMap;
    private ArrayList<ArrayList<Tile>> currentTiles;
    private ArrayList<Button> columnButton;
    private int charmingHitsCounter;
    private String songName;
    private int value = 0; // -1 reset 0 none 1 add
    private int lastRow;
    private static Game instance = null;
    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    private Game() {

    }

    public void setMatrix(ArrayList<Button> buttons) {
        Settings.getInstance().gameOver(false);
        charmingHitsCounter = 0;
        columnButton = buttons;
        value = -1;
        soundMap = new ArrayList<>();
        currentTiles = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ArrayList<Tile> tmp = new ArrayList();
            for (int j = 0; j < 6; j++)
                tmp.add(new Tile(true));
            soundMap.add(tmp);
        }
        for (final Button b : columnButton) {
            b.setBackgroundColor(Color.TRANSPARENT);
            b.setWidth(180);
            b.cancelLongPress();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = b.getResources().getResourceName(b.getId());
                    char n = name.charAt(name.length() - 1);
                    if (currentTiles.size() >= 4) {
                        Tile t = new Tile(currentTiles.get(currentTiles.size() - 1).get(Integer.parseInt(String.valueOf(n))).isVisible());
                        if (t.isVisible()) {
                            value = 1;
                            currentTiles.get(currentTiles.size()-1).get(Integer.parseInt(String.valueOf(n))).setVisible(false);
                            System.out.println(value);
                        }
                    }
                }
            });

        }
    }

    public boolean isAvailable(int row) {
        lastRow = row;
        return row < soundMap.size();
    }

    public ArrayList<Tile> getRow(int row) {
        return soundMap.get(row);
    }

    public void setCurrentMap(ArrayList<ArrayList<Tile>> tiles) {
        if (lastRow < soundMap.size())
            currentTiles = tiles;
        else {
            boolean ck = false;
            for (int i = 0; i < tiles.size(); i++) {
                for (int j = 0; j < tiles.get(i).size(); j++) {
                    if (tiles.get(i).get(j).isVisible()) {
                        ck = true;
                    }
                }
            }
            if (!ck)
                Settings.getInstance().gameOver(true);
        }
    }

    public synchronized int upgradeCharminCount() {
        return value;

    }
    public void restoreValue(){
        value=0;
    }
    public void lostTile() {
        value = -1;
    }


    public ArrayList<ArrayList<Tile>> getCheckedTiles() {

        return currentTiles;
    }
}
