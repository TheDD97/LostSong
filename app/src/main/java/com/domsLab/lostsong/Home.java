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
import model.DBManager;
import model.Settings;
import model.Song;
import model.Tile;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private ImageButton imageButton;
    private DBManager db;
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
        db = new DBManager(this);
        ArrayList<ArrayList<Tile>>soundMap=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Tile> tmp = new ArrayList();
            for (int j = 0; j < 6; j++)
                if(i%2==0)
                    tmp.add(new Tile(true));
                else tmp.add(new Tile(false));
            soundMap.add(tmp);
        }
        db.addSong(new Song("Prova",soundMap));
        ArrayList<ArrayList<Tile>> obtainedMap = db.getSong("Prova");
        StringBuilder stringBuilder=new StringBuilder();
        for (ArrayList<Tile> tiles : obtainedMap) {
            for (Tile tile : tiles) {
                stringBuilder.append(tile.toString());
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }

    private void launchListActivity() {
        Intent intent = new Intent(this, SongList.class);
        startActivity(intent);
    }
}
