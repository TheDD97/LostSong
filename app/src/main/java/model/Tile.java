package model;

public class Tile {
    private boolean visible;
    private int speed;


    public Tile(boolean visible) {
        // this.setImageResource(imgRes);
        speed = 0;
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getSpeedAndIncrease() {
        if (speed>=Settings.getInstance().getTileMovement())
            speed=0;
        return speed+=Settings.fps*Settings.getInstance().getDensity();
    }


    public void resetIncrease() {
        speed=0;
    }

    @Override
    public String toString() {
        if (visible)
            return "1";
        else return "0";
    }
}
