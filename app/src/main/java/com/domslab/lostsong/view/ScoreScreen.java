package com.domslab.lostsong.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.domslab.lostsong.model.Settings;
import com.example.lostsong.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScoreScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_FULLSCREEN);
        TextView score = findViewById(R.id.score);
        TextView highScore = findViewById(R.id.bestscore);
        TextView name = findViewById(R.id.songLabel);
        SharedPreferences preferences = getSharedPreferences(GameScreen.GAME_SCORE,MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE);
        score.setText(Integer.toString(preferences.getInt("current_score_"+sharedPreferences.getString("SongName", ""),0)));
        highScore.setText(Integer.toString(preferences.getInt("high_score_"+sharedPreferences.getString("SongName", ""),0)));
        name.setText(sharedPreferences.getString("SongName",null));
        int pos = sharedPreferences.getInt("position", 0);
        int id = this.getResources().getIdentifier("bkg" + pos, "drawable", this.getPackageName());
        LinearLayout layout = findViewById(R.id.main_score_layout);
        layout.setBackground(getDrawable(id));
        FloatingActionButton restart=findViewById(R.id.restart_on_score);
        FloatingActionButton goList = findViewById(R.id.songlist_on_score);
        restart.setOnClickListener(new View.OnClickListener() {
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
        goList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SongList.class);
                startActivity(intent);
            }
        });
    }
}
