package com.domslab.lostsong.model;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import com.example.lostsong.R;

import java.util.ArrayList;

public class Game {

    private ArrayList<ArrayList<Tile>> soundMap;
    private ArrayList<ArrayList<Tile>> currentTiles;
    private ArrayList<Button> columnButton;
    private int value = 0; // -1 reset 0 none 1 add
    private int lastRow;
    private static Game instance = null;
    public boolean ready = false;

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    private Game() {
    }

    public void setMatrix(ArrayList<Button> buttons) {
        columnButton = buttons;
        value = -1;
        currentTiles = new ArrayList<>();
        for (final Button b : columnButton) {
            b.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
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
                            currentTiles.get(currentTiles.size() - 1).get(Integer.parseInt(String.valueOf(n))).setVisible(false);
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
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

    public int upgradeCharminCount() {
        return value;

    }

    public void restoreValue() {
        value = 0;
    }

    public void lostTile(int i) {
        columnButton.get(i).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        value = -1;
    }

    public ArrayList<ArrayList<Tile>> getCheckedTiles() {
        return currentTiles;
    }

    public void setSong(Song s) {
        soundMap = s.getSoundMap();
        ready = true;
    }

    public void setButtonWidth(int width) {
        for (Button b : columnButton)
            b.setWidth(width);
    }

    public void resetButtonColor() {
        for (Button b:columnButton)
            b.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
    }
}
