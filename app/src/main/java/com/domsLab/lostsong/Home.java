package com.domsLab.lostsong;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lostsong.R;

import model.DBManager;
import model.Settings;
import model.Song;
import model.Tile;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private ImageButton imageButton;
    private DatabaseHelper db;

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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm = new DisplayMetrics();
        Settings.getInstance().setDensity(getResources().getDisplayMetrics().density);
        ArrayList<ArrayList<Tile>> soundMap = new ArrayList<>();
        db = new DatabaseHelper(this);
        for (int i = 0; i < 10; i++) {
            ArrayList<Tile> tmp = new ArrayList();
            for (int j = 0; j < 6; j++)
                if (i % 2 == 0)
                    tmp.add(new Tile(true));
                else tmp.add(new Tile(false));
            soundMap.add(tmp);
        }
        Song s = new Song("3KKK", soundMap);
        Boolean b = db.addData(s);
        System.out.println(s.toString());


    }

    private void launchListActivity() {
        Intent intent = new Intent(this, SongList.class);
        startActivity(intent);
    }
}
