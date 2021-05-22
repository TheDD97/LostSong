package com.domslab.lostsong.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.domslab.lostsong.model.SoundMapGenerator;
import com.example.lostsong.R;
import com.domslab.lostsong.database.DbManager;
import com.domslab.lostsong.model.Settings;
import com.domslab.lostsong.model.Song;

public class Home extends AppCompatActivity {
    private ImageButton imageButton;
    private TextView homeText;
    private Animation homeAnimation;
    private LinearLayout layout;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false); //also tried with true
            System.out.println("SONO QUI");
        } else {
            System.out.println("SONO QUI2");
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        layout = findViewById(R.id.homeLayout);
        Settings.getInstance().setDensity(getResources().getDisplayMetrics().density);

       /* db = getDatabaseManager();
        SoundMapGenerator mapGenerator = new SoundMapGenerator((2.52f*60000));
        mapGenerator.generateSoundMap();
        Song s = new Song("3", mapGenerator.getMap());
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

    @Override
    protected void onResume() {
        super.onResume();
        db = getDatabaseManager();
        if (db.songModel().loadAllSong().isEmpty()) {
            Song s = new Song("0", "101000100001100001010010000101000101100000100000100000001010001010001010100001000001010100001010000001100000000101001010101000100000001010010100001010000001000001010010101000101000010100100001100001000001010100100000001010000101000101000001000001000001010010101000010010000001000001100000000101010100010010010100000101000101010010010010010100010100100000100000010010010100100001000101010010010010010100001010000001001010101000000001101000100001010100010100001010100000000001101000010010010100010100100000100001010010001010101000010100001010000001101000100000010100100001001010101000000001100000100001010010010100100001000001100000010010010100010100010010101000010010001010000101010100100000101000000101001010000101100000001010101000100001010100101000010010001010010100100000010010010010001010101000100000100001000101100001010100000001000101001010001010000001100001000001001010100001010100010010010010100001100000010100100001010100001010010100100001100001010100010010000101000001000101000001101000000101100001001010010010101000001010100000010100000101101000000101101000001010100001100001101000010100101000100001001010010010001010000001010010000101010010101000001010010010100000010010010010101000000001010100100000000001100001010010100001000101100000101000101000010100010100010010101000100001000101101000100000001010010100100000001010010010000001010100010010100000100001010010000001100000000101000001100001100001");
            System.out.println("ok0");
            db.songModel().insertSong(s);
            s = new Song("1", "001010100001000101010010000000010100101000010100000101101000000000100000100001001010010010100000001010101000010010000101001010001010010010000101010100100001100001000000100000000101000101000101100000001010001010000000101000010010001010010100001010100001101000010100010100101000001010100000001010000000010010000001000000000000000000001010000101100001000001101000000101000001100000101000000101100001010100001010101000100000000000010010000001000000000101001010100001100000101000000000000001010010100001100001100001000000010100000001010100100001100000000101100001101000101000010010100001000001000000100000001010100000000001000101100001001010000000100001100001010010000101100001010100100000001010001010010100000101010100000000010010000101001010000101010100000101100000010010000000100001010100010100100000010100000000000000101000000000010100000101000101001010100001101000010100000000010100100001000001010010010100010010100001101000010100010100");
            db.songModel().insertSong(s);
            System.out.println("ok1");
            s = new Song("2", "100001000001000001000001101000000101100001000101100000000101001010000101001010010100001010101000100001100001101000000001100000101000010100101000100000101000010010100000010010100001001010010010000101100001010010010100101000101000101000010010010010000101001010000001010010000101010010000001101000001010000001010010000101100000100001100001100001000101001010001010000101010010010010101000100001101000101000010100101000010010100000000101000101100000000101010010001010010010010100010100100001101000010100101000101000101000100000100001001010101000000001001010000101010100100000000101101000001010100001100001000101001010100000100001101000100001010010010100101000010100100000010010000001100001000101010100100000001010101000001010101000000001100000001010010100001010010100100001101000001010001010100001101000100001000001010100010100000101000101000101100001000001010100100000101000101000100000000001010010101000100000010100010010010100000101101000010010100000100001100000100001100000000001100000010100100000010100010010100001000001100001100001000001001010010010000001000101101000100001100001100000000101010100000001000001100001100001001010000101001010000001000101010010010010100001100000001010100001101000010100101000010010100000100000001010010010001010000101000001100001001010000101100001100000010100001010000001000101010010010010100000101000010100000101000001010010000101000101000101000101010010010010100001001010010100000001100000100001000001010010101000000001000101001010010100000101101000001010001010000001100001010100100000000101000001100001000001100001001010010100010100101000000101000101001010001010100000101000100001001010100000001010000101100000010100000001100001010100100000000101001010010010000001010100000101100000100001000101010100100001010010001010100000100000001010001010101000010100010100101000000101100000010010010010001010101000010100100001101000010100000001000001010010010100000001010100000001010100010010000101100000010010100001000001100000000001100001101000100000010100101000100001101000000001100000100000100001010010101000010010001010000001000001010010000001000101100001000101010010010010010010");
            db.songModel().insertSong(s);
            System.out.println("ok2");
            s = new Song("3", "010010100001100001000001010010101000010010010100010010001010000101000001000101100000001010100000000001001010010100101000010100101000001010101000010100100001100001000001101000010010100000101000000001100001010100010010010010010010101000000101000001100001100000000001000101000001000101001010101000001010100001100001001010010100010100010100001010010100101000100000010100010100000101010010100001101000010100010100100001000001001010000001000001000101100001100001100001001010010010101000100001100001010010000001001010000001000001010100010010101000010100101000100000101000010100100000100000100000100001100000100001000101010010000101000101001010010010100000001010001010100001001010000001010010000101010100010010010010010010000001100001010010100001001010010100000001100000101000000101010010000001000001000101000001100000001010000001000101010100100000001010000101010010000001100000100000100001010010000101010100100001010010000101000001000101010100100001001010000001000101100001010100001010010010010100000001000001010100101000010100000001010100010100010010001010000001000101000001010100100001100001000101100001001010001010000101010010010100001010010100010100000001010100001010100000001010000001010010000101101000000001001010101000010100000001101000000101010010000101010010010100100001000001010100001010000101100000100001100000101000010100001010000101100001100001101000100000000101001010000101001010100001101000010010010010101000000101000101010010010010000001001010000001100001010100100001010010101000010010010010101000010100100000001010100000010010010100001010010100000101000001010100100001000001010100000001000101100000001010010100010010100001000101000101000101001010010010100001001010001010000101001010010100000101100001010100101000001010010010010010010100010010010100000101101000010010000001001010001010101000100000100001000001001010000001010010101000000001100001100000000101000101010010001010010100100000101000100000010100000001001010100001001010100001000101101000000001000001010100100000010100010100000101101000001010010010100001010010001010010010001010010100");
            db.songModel().insertSong(s);
            System.out.println("ok3");
            s = new Song("4", "010100010100000101000101000101101000000101000101010010100000000101101000000001000101010100100001010100010010000001000001010100010100001010100001101000000101000001001010101000100000000101000101001010010100000101101000001010100000100001000001010010010010010010010100000101000101000101010010010010101000001010010100100000001010010010101000010010001010010010000101010010000001101000010010101000010010010010010010100000010100101000101000000101000101001010000101001010001010000101000001101000101000000001101000100000010100010010001010101000000001010010000101010100000101010100010100100000000101001010010100000001010100010010010010000101100001000101001010100000010010000101001010001010101000000101101000001010000101010100010100101000001010010100000001010100100001100001010010101000101000010100010010000101000001101000000101100000000001000001001010100000101000001010101000100001100000100000100001100000000001100000010100101000100001010010100000100000000001000001101000100000101000000101100000100001000101001010010010101000000001101000100001000101001010010010010100000000");
            db.songModel().insertSong(s);
            System.out.println("ok4");
        }

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
