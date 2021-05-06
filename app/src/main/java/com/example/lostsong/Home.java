package com.example.lostsong;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private ImageButton imageButton;
     private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageButton = findViewById(R.id.goSongList);
        layout=findViewById(R.id.homeLayout);
        System.out.println(layout.getWidth());
        System.out.println(layout.getWidth());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchListActivity();
            }
        });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    private void launchListActivity() {
        Intent intent = new Intent(this,GameScreen.class);
        startActivity(intent);
    }
}
