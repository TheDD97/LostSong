package com.domslab.lostsong.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.domslab.lostsong.model.Settings;
import com.example.lostsong.R;
import com.domslab.lostsong.database.DbManager;
import com.domslab.lostsong.model.Game;
import com.domslab.lostsong.model.SongCard;
import adapters.SongAdapter;

import java.util.ArrayList;

public class SongList extends AppCompatActivity implements SongAdapter.OnSongListener {
    public final static String showTutorial = "Tutorial";
    private RecyclerView recyclerView;
    private ArrayList<SongCard> songCards;
    private SongAdapter songAdapter;
    private DbManager db;
    private Button howToPlayButton;
    private ImageButton closeAppButton;

    private DbManager getDatabaseManager() {
        if (db == null)
            db = DbManager.getDatabase(this);
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        db = getDatabaseManager();
        recyclerView = findViewById(R.id.song_list_view);
        SharedPreferences.Editor editor = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE).edit();
        editor.putInt("Time", 0);
        editor.apply();
        Integer[] songLogo = {R.drawable.dream, R.drawable.mg, R.drawable.fairy_tail, R.drawable.me3, R.drawable.vivy, R.drawable.dragon_quest, R.drawable.black_clover, R.drawable.edens_zero, R.drawable.sao_alicization};
        String[] songName = {"Dream", "Megalovania", "Fairy tail main theme", "An end once and for all", "Vivy fluorite eyes song", "Dragon quest Opening 2020", "Haruka Mirai", "Edens through the rough", "LiSA - ADAMAS"};
        songCards = new ArrayList<>();
        for (int i = 0; i < songLogo.length; i++) {
            SongCard tmp = new SongCard(songLogo[i], songName[i]);
            songCards.add(tmp);
        }
        howToPlayButton = findViewById(R.id.howToPlay);
        howToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HowToPlay.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(SongList.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        songAdapter = new SongAdapter(SongList.this, songCards, this);
        recyclerView.setAdapter(songAdapter);
        closeAppButton = findViewById(R.id.closeButton);
        closeAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SongList.this);
                builder.setMessage("Do you want to exit?");
                builder.setCancelable(true);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Process.killProcess(Process.myPid());
                        System.exit(0);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        SharedPreferences preferences = getSharedPreferences(showTutorial, MODE_PRIVATE);
        if (preferences.getString("show", "yes").equals("yes")) {
            SharedPreferences.Editor preferencesEditor = getSharedPreferences(showTutorial, MODE_PRIVATE).edit();
            preferencesEditor.putString("show", "false");
            preferencesEditor.apply();
            Intent intent = new Intent(this, SongListHelp.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSongClick(int position) {

        SharedPreferences.Editor editor = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE).edit();
        editor.putString("SongName", songCards.get(position).getSongName());
        editor.putInt("position", position);
        editor.apply();
        Settings.getInstance().gameOver(false);
        Game.getInstance().setSong(db.songModel().loadSong(Integer.toString(position)));
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}