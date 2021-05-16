package com.domslab.lostsong.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.domslab.model.SoundMapGenerator;
import com.example.lostsong.R;
import com.domslab.lostsong.database.DbManager;
import com.domslab.model.Settings;
import com.domslab.model.Song;
import com.domslab.model.Tile;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private ImageButton imageButton;
    private TextView homeText;
    // private DatabaseHelper db;
    private Animation homeAnimation;
    //private DbManager db;
    private Song song;
    private DbManager db;

    private DbManager getDatabaseManager() {
        if (db == null)
            db = DbManager.getDatabase(this);
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_FULLSCREEN);
        imageButton = findViewById(R.id.goSongList);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchListActivity();
            }
        });
        homeText = findViewById(R.id.home_text);
        homeAnimation = AnimationUtils.loadAnimation(this, R.anim.text_animation_fade);
        homeText.setAnimation(homeAnimation);
        Settings.getInstance().setDensity(getResources().getDisplayMetrics().density);
       /* db = getDatabaseManager();
        SoundMapGenerator mapGenerator = new SoundMapGenerator((2.45f*60000));
        mapGenerator.generateSoundMap();
        Song s = new Song("2", mapGenerator.getMap());
        db.songModel().insertSong(s);
        System.out.println(s.getName() + " " + s);*/


    }

    private void launchListActivity() {
        Intent intent = new Intent(this, SongList.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        DbManager.destroyInstance();
        super.onDestroy();

    }
}
