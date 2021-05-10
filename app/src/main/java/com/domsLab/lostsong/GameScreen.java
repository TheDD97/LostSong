package com.domsLab.lostsong;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.example.lostsong.R;
import model.Settings;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen extends AppCompatActivity {
    private ImageView tabBar;
    private Button button0, button1, button2, button3, button4, button5;
    private ConstraintLayout layout;
    private TilesSurfaceView tilesSurfaceView;
    private TextView songName, charmingCount;
    private Game g;
    private ImageButton pauseButton;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("sono in pausa");
                Intent intent = new Intent(v.getContext(), PauseActivity.class);
                startActivity(intent);
            }
        });
        charmingCount = findViewById(R.id.charmingCount);
        charmingCount.setText(Integer.toString(0));
        songName = findViewById(R.id.songName);
        tabBar = findViewById(R.id.tapBar);
        tilesSurfaceView = new TilesSurfaceView(getApplicationContext());
        layout = findViewById(R.id.gameLayout);
        layout.addView(tilesSurfaceView, 0);
        ConstraintSet set = new ConstraintSet();
        set.connect(tabBar.getId(), ConstraintSet.TOP, tilesSurfaceView.getId(), ConstraintSet.BOTTOM);
        set.applyTo(layout);
        tilesSurfaceView.setLayout(layout);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        tilesSurfaceView.setSongView(songName);
        g = new Game(new ArrayList<Button>(Arrays.asList(button0, button1, button2, button3, button4, button5)));
        tilesSurfaceView.setGame(g);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Thread t = new Thread() {
            @Override
            public synchronized void run() {
                super.run();
                while (!Settings.getInstance().isGameOver()) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (g.upgradeCharminCount()) {
                                int c = Integer.parseInt((String) charmingCount.getText());
                                c++;
                                charmingCount.setText(Integer.toString(c));
                            } else charmingCount.setText(Integer.toString(0));


                        }
                    });
                }
            }
        };
        player = MediaPlayer.create(getApplicationContext(),R.raw.megalovania);
        player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
        System.out.println("scree pause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(player!=null && !player.isPlaying())
            player.start();
        System.out.println("Screen resume");

    }
}
