package com.domslab.lostsong.view;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lostsong.R;

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
    }
}
