package com.domsLab.lostsong;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lostsong.R;
import model.SongCard;
import adapters.SongAdapter;

import java.util.ArrayList;

public class SongList extends AppCompatActivity implements SongAdapter.OnSongListener {
    //initialize variable
    RecyclerView recyclerView;
    ArrayList<SongCard> songCards;
    SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        recyclerView = findViewById(R.id.song_list_view);
        SharedPreferences.Editor editor = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE).edit();
        editor.putInt("Time", 0);
        editor.apply();
        Integer[] songLogo = {R.drawable.dream, R.drawable.mg, R.drawable.fairy_tail, R.drawable.me3};
        String[] songName = {"Dream", "Megalovania", "Fairy tail main theme", "An end once and for all"};
        songCards = new ArrayList<>();
        for (int i = 0; i < songLogo.length; i++) {
            SongCard tmp = new SongCard(songLogo[i], songName[i]);
            songCards.add(tmp);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(SongList.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        songAdapter = new SongAdapter(SongList.this, songCards, this);
        recyclerView.setAdapter(songAdapter);
    }

    @Override
    public void onSongClick(int position) {

        SharedPreferences.Editor editor = getSharedPreferences(TilesSurfaceView.settingName, MODE_PRIVATE).edit();
        editor.putString("SongName", songCards.get(position).getSongName());
        editor.putInt("position",position);
        editor.apply();
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}