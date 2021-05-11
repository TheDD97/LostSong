package com.domsLab.lostsong;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.lostsong.R;
import model.Settings;
import model.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class TilesSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public static String settingName = "TilesSurfaceSetting";
    private static int refresh = 330;
    private SurfaceHolder surfaceHolder = null;
    private Paint paint = null;
    private float tileX = 380, tileY = 0;
    private ArrayList<ArrayList<Tile>> tiles;
    private Thread gameThread;
    private Game g;
    private int currentRow = 0;
    private ConstraintLayout constraintLayout;
    private boolean isRunning;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public TilesSurfaceView(Context context) {
        super(context);
        setFocusable(true);
        isRunning = true;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.GRAY);
        tiles = new ArrayList<>();
        gameThread = new Thread(this);
        preferences = context.getSharedPreferences(settingName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setGame(Game g) {
        this.g = g;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isRunning = true;
        if (gameThread.getState() == Thread.State.TERMINATED)
            gameThread = new Thread(this);
        gameThread.start();
        paint.setColor(Color.BLACK);
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (gameThread.getState() == Thread.State.TERMINATED) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void drawTiles() {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.deemo_wallpaper_19);

        for (int k = 0; k < Settings.getInstance().getTileMovement() / Settings.fps / (Settings.getInstance().getDensity()); k++) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(bmp, 0, 0, null);
            for (int i = 0; i < tiles.size(); i++)
                for (int j = 0; j < tiles.get(i).size(); j++) {
                    float increase = tiles.get(i).get(j).getSpeedAndIncrease();
                    if (tiles.get(i).get(j).isVisible()) {
                        canvas.drawRect(tileX + (260 * j), Settings.verticalSpacing + (Settings.getInstance().getVerticalSpacing() * i) + increase, tileX + 240 + (260 * j), Settings.verticalSpacing + Settings.getInstance().getTileHeight() + (i * Settings.getInstance().getVerticalSpacing()) + increase, paint);
                    }
                }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {

        int time = preferences.getInt("Time", 0);
        Settings.getInstance().setSurfaceHeight(constraintLayout.getMeasuredHeight());
        while (isRunning && !Settings.getInstance().pause()) {
            editor.putInt("Time", time);
            editor.apply();
            try {
                Thread.sleep(refresh);
                if (time >= 5 && !tiles.isEmpty()) {
                    time = 4;
                    /*for (int i = 0; i < tiles.get(tiles.size() - 1).size(); i++)
                        if (tiles.get(tiles.size() - 1).get(i).isVisible())
                            g.lostTile();*/
                    tiles.remove(tiles.size() - 1);
                }
                if (tiles.size() < 5 || tiles.isEmpty()) {
                    if (g.isAvailable(currentRow)) {
                        tiles.add(0, g.getRow(currentRow));
                        currentRow++;
                    } else
                        tiles.add(0, new ArrayList<Tile>(Arrays.asList(new Tile(false), new Tile(false), new Tile(false), new Tile(false), new Tile(false), new Tile(false))));

                    for (int i = 0; i < tiles.size(); i++)
                        for (int j = 0; j < tiles.get(i).size(); j++)
                            tiles.get(i).get(j).resetIncrease();

                }
                g.setCurrentMap(tiles);
                tiles = g.getCheckedTiles();
                drawTiles();
                time++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setLayout(ConstraintLayout layout) {
        constraintLayout = layout;
    }


}




