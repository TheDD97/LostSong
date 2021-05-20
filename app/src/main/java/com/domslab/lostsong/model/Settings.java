package com.domslab.lostsong.model;


public class Settings {

    public static int rows = 5;
    private boolean gameOver;
    public float tileMovement;
    public float tileHeight;
    public static float verticalSpacing;
    public float surfaceHeight;
    private float density;
    private Song selectedSong;

    private static Settings instance = null;

    public static int fps = 5;
    private boolean pause;

    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    private Settings() {
    }

    public void gameOver(boolean b) {
        gameOver = b;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setDensity(float density) {
        this.density = density;


    }

    public void setSurfaceHeight(float surfaceHeight) {
        this.surfaceHeight = surfaceHeight;
        tileHeight = 10 * density;
        verticalSpacing = surfaceHeight / 5; //5 + il numero di righe mostrate per volta
        System.out.println(verticalSpacing);
        tileMovement = verticalSpacing;
    }

    public float getTileMovement() {
        return tileMovement;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public float getVerticalSpacing() {
        return verticalSpacing;
    }

    public float getDensity() {
        return density;
    }

    public float getSurfaceHeight() {
        return surfaceHeight;
    }


    public boolean pause() {
        return pause;
    }

    public void setPause(Boolean p) {
        pause = p;
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong;
    }
}
