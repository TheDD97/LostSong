package com.example.lostsong;


import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen extends AppCompatActivity {
    private GridLayout gridLayout;
    private Button button0, button1, button2, button3, button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridLayout = findViewById(R.id.tileContainer);
        int counter = 0;
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(button0, button1, button2, button3, button4, button5));
        for (final Button b : buttons) {
            b.setBackgroundColor(Color.TRANSPARENT);
            b.setWidth(180);

            b.cancelLongPress();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(b.getResources().getResourceName(b.getId()).charAt(b.getResources().getResourceName(b.getId()).length() - 1));
                }
            });
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                final ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.tile);
                imageView.setMaxWidth(200);
                imageView.setAdjustViewBounds(true);
                imageView.setId(counter);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.setMargins(45, 35, 45, 0);
                imageView.setLayoutParams(layoutParams);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(imageView.getId());
                    }
                });
                gridLayout.addView(imageView, counter);
                counter++;
            }
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
