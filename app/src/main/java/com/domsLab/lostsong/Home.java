package com.domsLab.lostsong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lostsong.R;
import model.Settings;

public class Home extends AppCompatActivity {
    private ImageButton imageButton;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageButton = findViewById(R.id.goSongList);
        layout = findViewById(R.id.homeLayout);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchListActivity();
            }
        });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm = new DisplayMetrics();
        System.out.println(getResources().getDisplayMetrics().density);
        Settings.getInstance().setDensity(getResources().getDisplayMetrics().density);
        SharedPreferences.Editor editor = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE).edit();
        editor.putInt("Time", 0);
        editor.apply();
    }

    private void launchListActivity() {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}
