package com.domsLab.lostsong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lostsong.R;

public class PauseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        TextView resume = findViewById(R.id.resume);
        TextView home = findViewById(R.id.home);
        TextView retry = findViewById(R.id.retry);
        TextView songList = findViewById(R.id.golist);
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
                Intent intent = new Intent(v.getContext(), GameScreen.class);
                startActivity(intent);
            }
        });
    }
}