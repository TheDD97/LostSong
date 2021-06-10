package com.domslab.lostsong.model;


public class Settings {

    public static int rows = 5;
    private boolean gameOver;
    public float tileMovement;
    public static float tileHeight;
    public static float tileWidth;
    public static float verticalSpacing;
    public float surfaceHeight = 0, surfaceWidth = 0;
    private float density;
    private float paddingLeft;
    private static Settings instance = null;
    public static int tileSpeed = 5;
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
        if (this.surfaceWidth == 0) {
            this.surfaceHeight = surfaceHeight;
            tileHeight = 10 * density;
            verticalSpacing = surfaceHeight / 5; //5 + il numero di righe mostrate per volta
            tileMovement = verticalSpacing;
        }
    }

    public float getTileMovement() {
        return tileMovement;
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

    public void setSurfaceWidth(float measuredWidth) {
        if (surfaceWidth == 0) {
            surfaceWidth = measuredWidth;
            paddingLeft = measuredWidth / 8;
            tileWidth = paddingLeft - 10;
        }
    }

    public float getPaddingLeft() {
        return paddingLeft;
    }

    public float getSurfaceWidth() {
        return surfaceWidth;
    }

}
