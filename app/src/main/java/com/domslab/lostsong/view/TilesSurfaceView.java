package com.domslab.lostsong.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.domslab.lostsong.model.Game;
import com.domslab.lostsong.model.Settings;
import com.domslab.lostsong.model.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class TilesSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public static String settingName = "TilesSurfaceSetting";
    public static int refresh = 330;
    private SurfaceHolder surfaceHolder;
    private Paint paint, bkgPaint;
    private float tileX = 380, tileY = 0;
    private ArrayList<ArrayList<Tile>> tiles;
    private Thread gameThread;
    private int currentRow = 0;
    private ConstraintLayout constraintLayout;
    private boolean isRunning, setbkg = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Bitmap bmp;
    private int height, width;
    private Game g;

    public TilesSurfaceView(Context context) {
        super(context);
        setFocusable(true);
        isRunning = true;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.GRAY);
        bkgPaint = new Paint();
        bkgPaint.setAntiAlias(true);
        bkgPaint.setFilterBitmap(true);
        bkgPaint.setDither(true);
        tiles = new ArrayList<>();
        gameThread = new Thread(this);
        preferences = context.getSharedPreferences(settingName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        int pos = preferences.getInt("position", 1);
        int id = this.getResources().getIdentifier("bkg" + pos, "drawable", context.getPackageName());
        bmp = BitmapFactory.decodeResource(getResources(), id);


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
        paint.setColor(Color.BLACK);
        for (int k = 0; k < Settings.getInstance().getTileMovement() / Settings.tileSpeed / (Settings.getInstance().getDensity()); k++) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(bmp, 0, 0, bkgPaint);
            if (!tiles.isEmpty())
                for (int i = 0; i < tiles.size(); i++)
                    for (int j = 0; j < tiles.get(i).size(); j++) {
                        float increase = tiles.get(i).get(j).getSpeedAndIncrease();
                        if (tiles.get(i).get(j).isVisible())
                            canvas.drawRect(Settings.getInstance().getPaddingLeft() + (Settings.getInstance().getPaddingLeft() * j), (Settings.getInstance().getVerticalSpacing() * i) + increase, Settings.getInstance().getPaddingLeft() + Settings.tileWidth + (Settings.getInstance().getPaddingLeft() * j), Settings.tileHeight + (i * Settings.getInstance().getVerticalSpacing()) + increase, paint);
                    }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        boolean stop = false;
        int time = preferences.getInt("Time", 0);
        Settings.getInstance().setSurfaceHeight(constraintLayout.getMeasuredHeight());
        Settings.getInstance().setSurfaceWidth(constraintLayout.getMeasuredWidth());
        if (!setbkg) {
            bmp = Bitmap.createScaledBitmap(bmp, (int) Settings.getInstance().getSurfaceWidth(), (int) Settings.getInstance().getSurfaceHeight(), false);
            setbkg = true;
        }
        while (isRunning && !Settings.getInstance().pause()) {
            editor.putInt("Time", time);
            editor.commit();
            try {
                drawTiles();
                Thread.sleep(refresh);
                if (time >= 5 && !tiles.isEmpty() && !stop) {
                    time = 4;
                    for (int i = 0; i < tiles.get(tiles.size() - 1).size(); i++)
                        if (tiles.get(tiles.size() - 1).get(i).isVisible())
                            g.lostTile(i);
                    tiles.remove(tiles.size() - 1);
                }
                System.out.println(stop);
                if (tiles.size() < 5 || tiles.isEmpty() && !stop) {
                    if (g.isAvailable(currentRow)) {
                        tiles.add(0, g.getRow(currentRow));
                        currentRow++;
                    } else {
                        stop = true;
                        time = 0;
                    }
                }
                if (stop) {
                    if (!tiles.isEmpty())
                        tiles.remove(tiles.size() - 1);
                    if (time < 5) {
                        tiles.add(0, new ArrayList<Tile>(Arrays.asList(new Tile(false), new Tile(false), new Tile(false), new Tile(false), new Tile(false), new Tile(false))));
                    } else break;
                }
                for (int i = 0; i < tiles.size(); i++)
                    for (int j = 0; j < tiles.get(i).size(); j++)
                        tiles.get(i).get(j).resetIncrease();

                g.setCurrentMap(tiles);

                tiles = g.getCheckedTiles();
                time++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setLayout(ConstraintLayout layout) {
        constraintLayout = layout;
    }

    public void setGame(Game g) {
        this.g = g;
    }
}




