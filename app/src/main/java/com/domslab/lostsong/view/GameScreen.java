package com.domslab.lostsong.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.example.lostsong.R;
import com.domslab.model.Game;
import com.domslab.model.Settings;

import java.util.ArrayList;
import java.util.Arrays;


public class GameScreen extends AppCompatActivity {
    private String COUNTER_EXTRA = "save Counter";
    public static String GAME_SCORE = "game score";
    private ImageView tabBar;
    private Button button0, button1, button2, button3, button4, button5;
    private ConstraintLayout layout;
    private TilesSurfaceView tilesSurfaceView;
    private TextView songName, charmingCount;
    private Game g;
    private ImageButton pauseButton;
    private MediaPlayer player;
    private ScreenThread t;
    private SharedPreferences sharedPreferences;
    private int tilesHitted = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PauseActivity.class);
                startActivity(intent);
            }
        });
        charmingCount = findViewById(R.id.charmingCount);
        if (savedInstanceState != null)
            charmingCount.setText(Integer.toString(savedInstanceState.getInt(COUNTER_EXTRA, 0)));
        else
            charmingCount.setText(Integer.toString(0));
        songName = findViewById(R.id.songName);
        sharedPreferences = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE);
        songName.setText(sharedPreferences.getString("SongName", "NOOOO"));
        int pos = sharedPreferences.getInt("position", 1);
        tabBar = findViewById(R.id.tapBar);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        g = Game.getInstance();
        System.out.println(g.ready);

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER_EXTRA, Integer.parseInt(charmingCount.getText().toString()));
    }

    private class ScreenThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!Settings.getInstance().pause()) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Settings.getInstance().isGameOver()) {
                            finish();
                            SharedPreferences.Editor editor = getSharedPreferences(GAME_SCORE, MODE_PRIVATE).edit();
                            SharedPreferences pref = getSharedPreferences(GAME_SCORE,MODE_PRIVATE);
                            editor.putInt("current_score_"+sharedPreferences.getString("SongName", ""), tilesHitted);
                            int highscore = pref.getInt("current_score_"+sharedPreferences.getString("SongName", ""),0);
                            if(highscore<tilesHitted)
                                editor.putInt("high_score_"+sharedPreferences.getString("SongName", ""), tilesHitted);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), ScoreScreen.class);
                            startActivity(intent);
                        }
                        if (Game.getInstance().upgradeCharminCount() == 1) {
                            int c = Integer.parseInt((String) charmingCount.getText());
                            c++;
                            tilesHitted++;
                            charmingCount.setText(Integer.toString(c));
                            Game.getInstance().restoreValue();
                        } else if (Game.getInstance().upgradeCharminCount() == -1)
                            charmingCount.setText(Integer.toString(0));
                    }
                });
            }
        }

    }

}
