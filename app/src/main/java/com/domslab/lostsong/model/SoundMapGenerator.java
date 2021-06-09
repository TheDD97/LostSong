package com.domslab.lostsong.model;

import com.domslab.lostsong.view.TilesSurfaceView;

import java.util.Random;

public class SoundMapGenerator {
    private String[] possibleRows = {"000001", "000101", "001010", "010100", "101000", "100000", "100001", "010010"};
    private float duration;
    private String sound;

    public SoundMapGenerator(float duration) {
        this.duration = duration;
    }

    public void generateSoundMap() {
        float rest = duration % TilesSurfaceView.refresh;
        int numRow = (int) (duration / (TilesSurfaceView.refresh + rest));
        Random r = new Random();
        StringBuilder soundMap = new StringBuilder();
        for (int i = 0; i < numRow; i++) {
            soundMap.append(possibleRows[r.nextInt(possibleRows.length)]);
        }
        this.sound = soundMap.toString();
    }

    public String getMap() {
        return sound;
    }
}
