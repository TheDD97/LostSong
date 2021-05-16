package com.domslab.model;

import com.domslab.lostsong.view.TilesSurfaceView;

import java.util.Random;

public class SoundMapGenerator {
    private String[] possibleRows= {"000001","000101","001010","010100","101000","100000","100001","010010"};
    private float duration;
    private String sound;
    public SoundMapGenerator(float duration) {
        this.duration = duration;
    }
    public void generateSoundMap(){
        float rest = duration%TilesSurfaceView.refresh;
       // System.out.println(rest);
        int numRow = (int) (duration/(TilesSurfaceView.refresh+rest));
        Random r = new Random();
        System.out.println(numRow);
        StringBuilder soundMap= new StringBuilder();
        for (int i = 0; i < possibleRows.length; i++) {
            System.out.println(possibleRows[i]);
        }
        for (int i=0;i<numRow+45;i++){
            soundMap.append(possibleRows[r.nextInt(possibleRows.length)]);
        }
        System.out.println("SOUND MAP "+sound);
        this.sound=soundMap.toString();
    }
    public String getMap(){
        return sound;
    }
}
