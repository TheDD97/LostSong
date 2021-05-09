package com.domsLab.lostsong;

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
    private boolean value;
    private int lastRow;

    public Game(ArrayList<Button> buttons) {
        Settings.getInstance().gameOver(false);
        value = false;
        songName = "Fatti i cazzi tuoi ca campi cent'anni";
        charmingHitsCounter = 0;
        columnButton = buttons;
        soundMap = new ArrayList<>();
        currentTiles = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            ArrayList<Tile> tmp = new ArrayList();
            for (int j = 0; j < 6; j++)
                if (i % 4== 0)
                    tmp.add(new Tile(true));
                else
                    tmp.add(new Tile(false));
            soundMap.add(tmp);
        }
        for (final Button b : columnButton) {
            //  b.setBackgroundColor(Color.TRANSPARENT);
            b.setWidth(180);
            b.cancelLongPress();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = b.getResources().getResourceName(b.getId());
                    char n = name.charAt(name.length() - 1);
                    System.out.println("NAME " + n);

                    if (currentTiles.size() == 5) {
                        Tile t = new Tile(currentTiles.get(Settings.rows - 2).get(Integer.parseInt(String.valueOf(n))).isVisible());
                        if (t.isVisible()) {
                            value = true;
                        } else value = false;
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
            for (int i = 0; i < soundMap.size(); i++) {
                for (int j = 0; j < soundMap.get(i).size(); j++) {
                    if (soundMap.get(i).get(j).isVisible()) {
                        ck = true;
                    }
                }
            }
            if (!ck)
                Settings.getInstance().gameOver(true);
        }
    }

    public boolean upgradeCharminCount() {
       return value;

    }
    public void lostTile() {
        value = false;
    }

    public String getName() {
        return songName;
    }
}
