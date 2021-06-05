package com.domslab.lostsong.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.gridlayout.widget.GridLayout;
import com.example.lostsong.R;
import com.domslab.lostsong.model.Game;
import com.domslab.lostsong.model.Settings;

import java.util.ArrayList;
import java.util.Arrays;


public class GameScreen extends AppCompatActivity {
    private final String COUNTER_EXTRA = "save Counter";
    public final static String GAME_SCORE = "game score";
    private ImageView tabBar;
    private Button button0, button1, button2, button3, button4, button5;
    private ConstraintLayout layout;
    private TilesSurfaceView tilesSurfaceView;
    private TextView songName, charmingCount, tilesHitted;
    private Game g;
    private ImageButton pauseButton;
    private MediaPlayer player;
    private ScreenThread t;
    private SharedPreferences sharedPreferences;
    private GridLayout gridLayout;
    private boolean firstRun = true;
    private Animation updateCounter,tilesHittedAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Settings.getInstance().gameOver(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        pauseButton = findViewById(R.id.pauseButton);
        updateCounter= AnimationUtils.loadAnimation(this, R.anim.counter_animation);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PauseActivity.class);
                startActivity(intent);
            }
        });
        charmingCount = findViewById(R.id.charmingCount);
        tilesHitted = findViewById(R.id.tiles_hitted);
        if (savedInstanceState != null) {
            charmingCount.setText(Integer.toString(savedInstanceState.getInt(COUNTER_EXTRA, 0)));
            tilesHitted.setText(Integer.toString(savedInstanceState.getInt(GAME_SCORE, 0)));
        } else
            charmingCount.setText(Integer.toString(0));
        songName = findViewById(R.id.songName);
        sharedPreferences = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE);
        songName.setText(sharedPreferences.getString("SongName", "NOOOO"));
        int pos = sharedPreferences.getInt("position", 1);
        tabBar = findViewById(R.id.tapBar);
        gridLayout = findViewById(R.id.tapSection);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        g = Game.getInstance();
        g.setMatrix(new ArrayList<Button>(Arrays.asList(button0, button1, button2, button3, button4, button5)));
        tilesSurfaceView = new TilesSurfaceView(getApplicationContext());
        layout = findViewById(R.id.gameLayout);
        layout.addView(tilesSurfaceView, 0);
        ConstraintSet set = new ConstraintSet();
        set.connect(tabBar.getId(), ConstraintSet.TOP, tilesSurfaceView.getId(), ConstraintSet.BOTTOM);
        set.applyTo(layout);
        tilesSurfaceView.setLayout(layout);
        tilesSurfaceView.setGame(g);
        t = new ScreenThread();
        int id = this.getResources().getIdentifier("track" + pos, "raw", this.getPackageName());
        player = MediaPlayer.create(getApplicationContext(), id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
        Settings.getInstance().setPause(true);
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Settings.getInstance().setPause(false);
        if (player != null && !player.isPlaying() && Game.getInstance().ready)
            player.start();
        if (t.getState() == Thread.State.TERMINATED)
            t = new ScreenThread();
        if (g.ready)
            t.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER_EXTRA, Integer.parseInt(charmingCount.getText().toString()));
        outState.putInt(GAME_SCORE, Integer.parseInt(tilesHitted.getText().toString()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enable sticky immersive mode.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private class ScreenThread extends Thread {
        @Override
        public void run() {

            super.run();
            while (!Settings.getInstance().pause()) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (firstRun) {
                            g.setButtonWidth((int) Settings.tileWidth);
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) gridLayout.getLayoutParams();
                            params.setMargins((int) Settings.getInstance().getPaddingLeft(), 0, (int) Settings.getInstance().getPaddingLeft(), 0);
                            gridLayout.requestLayout();
                            firstRun = false;
                        }
                        if (Settings.getInstance().isGameOver()) {
                            SharedPreferences.Editor editor = getSharedPreferences(GAME_SCORE, MODE_PRIVATE).edit();
                            SharedPreferences pref = getSharedPreferences(GAME_SCORE, MODE_PRIVATE);
                            editor.putInt("current_score_" + sharedPreferences.getString("SongName", ""), Integer.parseInt(tilesHitted.getText().toString()));
                            int highscore = pref.getInt("current_score_" + sharedPreferences.getString("SongName", ""), 0);
                            if (highscore < Integer.parseInt(tilesHitted.getText().toString()))
                                editor.putInt("high_score_" + sharedPreferences.getString("SongName", ""), Integer.parseInt(tilesHitted.getText().toString()));
                            editor.apply();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), ScoreScreen.class);
                            startActivity(intent);
                        }
                        if (Game.getInstance().upgradeCharminCount() == 1) {
                            int c = Integer.parseInt((String) charmingCount.getText());
                            c++;
                            int th = Integer.parseInt(tilesHitted.getText().toString());
                            tilesHitted.setText(Integer.toString(++th));
                            tilesHitted.setAnimation(updateCounter);
                            charmingCount.setText(Integer.toString(c));
                            charmingCount.setAnimation(updateCounter);
                            Game.getInstance().restoreValue();


                        } else if (Game.getInstance().upgradeCharminCount() == -1)
                            charmingCount.setText(Integer.toString(0));
                        g.resetButtonColor();
                            }
                });
            }
        }

    }
    @Override
    public void onBackPressed() { }
}
