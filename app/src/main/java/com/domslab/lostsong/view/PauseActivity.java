package com.domslab.lostsong.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.domslab.lostsong.model.Settings;
import com.example.lostsong.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PauseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        FloatingActionButton resume = findViewById(R.id.resume);
        FloatingActionButton home = findViewById(R.id.home);
        FloatingActionButton retry = findViewById(R.id.retry);
        FloatingActionButton songList = findViewById(R.id.golist);
        songList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SongList.class);
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
                startActivity(intent);
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE).edit();
                editor.putInt("Time", 0);
                editor.apply();
                Settings.getInstance().gameOver(false);
                Intent intent = new Intent(v.getContext(), GameScreen.class);
                startActivity(intent);
            }
        });
    }
}