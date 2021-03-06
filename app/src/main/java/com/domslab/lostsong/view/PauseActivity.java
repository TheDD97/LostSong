package com.domslab.lostsong.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.domslab.lostsong.database.DbManager;
import com.domslab.lostsong.model.Game;
import com.domslab.lostsong.model.Settings;
import com.example.lostsong.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PauseActivity extends AppCompatActivity {
    private DbManager db;

    private void getDatabaseManager() {
        if (db == null)
            db = DbManager.getDatabase(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        FloatingActionButton resume = findViewById(R.id.resume);
        FloatingActionButton home = findViewById(R.id.home);
        FloatingActionButton retry = findViewById(R.id.retry);
        FloatingActionButton songList = findViewById(R.id.golist);
        songList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SongList.class);
                finishAffinity();
                startActivity(intent);
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Home.class);
                finishAffinity();
                startActivity(intent);
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE).edit();
                editor.putInt("Time", 0);
                editor.apply();
                SharedPreferences preferences = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE);
                getDatabaseManager();
                Game.getInstance().setSong(db.songModel().loadSong(String.valueOf(preferences.getInt("position", -1))));
                Settings.getInstance().gameOver(false);
                Intent intent = new Intent(v.getContext(), GameScreen.class);
                finishAffinity();
                startActivity(intent);
            }
        });
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
}