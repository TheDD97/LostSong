package com.domsLab.lostsong;

import android.content.Context;
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
    private static int refresh = 100;
    private SurfaceHolder surfaceHolder = null;
    private Paint paint = null;
    private float tileX = 380, tileY = 0;
    private ArrayList<ArrayList<Tile>> tiles;
    private Thread gameThread;
    private Game g;
    private int timeOut = 283 * refresh;
    private int currentRow = 0;
    private ConstraintLayout constraintLayout;
    private TextView name;

    public TilesSurfaceView(Context context) {
        super(context);
        setFocusable(true);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.GRAY);
        tiles = new ArrayList<>();
    }

    public void setGame(Game g) {
        this.g = g;
        name.setText(g.getName());
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameThread = new Thread(this);
        gameThread.start();
        paint.setColor(Color.BLACK);
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void drawTiles() {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.deemo_wallpaper_19);
        for (int k = 0; k < Settings.getInstance().getTileMovement() / Settings.fps / (Settings.getInstance().getDensity()) ; k++) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(bmp, 0, 0, null);
            for (int i = 0; i < tiles.size(); i++)
                for (int j = 0; j < tiles.get(i).size(); j++) {
                    float increase = tiles.get(i).get(j).getSpeedAndIncrease();
                    if (tiles.get(i).get(j).isVisible())
                        paint.setColor(Color.BLACK);
                    else paint.setColor(Color.RED);
                    canvas.drawRect(tileX + (260 * j), (Settings.getInstance().getVerticalSpacing() * i) + increase, tileX + 240 + (260 * j), Settings.getInstance().getTileHeight() + (i * Settings.getInstance().getVerticalSpacing()) + increase, paint);
                }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public synchronized void run() {
        int time = 0;
        boolean finished = false;
        Settings.getInstance().setSurfaceHeight(constraintLayout.getMeasuredHeight());
        while (!finished) {
            try {
                Thread.sleep(refresh);
                if (time == 5 && !tiles.isEmpty()) {
                    time = 4;
                    for(int i =0;i<tiles.get(tiles.size()-1).size();i++)
                        if (tiles.get(tiles.size()-1).get(i).isVisible())
                            g.lostTile();
                    tiles.remove(tiles.size() - 1);
                }
                if (tiles.size() < 5 || tiles.isEmpty()) {
                    if (g.isAvailable(currentRow)) {
                        tiles.add(0, g.getRow(currentRow));
                        currentRow++;
                    } else
                        tiles.add(0, new ArrayList<Tile>(Arrays.asList(new Tile(false), new Tile(false), new Tile(false), new Tile(false), new Tile(false), new Tile(false))));
                    g.setCurrentMap(tiles);
                    for (int i = 0; i < tiles.size(); i++)
                        for (int j = 0; j < tiles.get(i).size(); j++)
                            tiles.get(i).get(j).resetIncrease();
                }
                drawTiles();
                g.upgradeCharminCount();
                time++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setLayout(ConstraintLayout layout) {
        constraintLayout = layout;
    }

    public void setSongView(TextView songName) {
        this.name = songName;
    }


}




