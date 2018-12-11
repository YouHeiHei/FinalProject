//package edu.illinois.cs.cs125.lab11;
package com.example.a17479.finalproject;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

public class Start extends AppCompatActivity {
    public static int highScore;
    public static int lastScore;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        final TextView last = findViewById(R.id.lastScore_Number);
        last.setText(String.valueOf(lastScore));
        final TextView high = findViewById(R.id.HighScore_Number);
        high.setText(String.valueOf(highScore));

        final Button start_1 = findViewById(R.id.start_button1);
        final Button start_2 = findViewById(R.id.start_button2);
        final Button start_3 = findViewById(R.id.start_button3);
        start_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Jump.class);
                setContentView(R.layout.gaming_mode);
                startActivity(intent);
                ImageView role = findViewById(R.id.PlayRole);
                role.setImageResource(R.drawable.dino1);
                Jump.type = 1;
            }
        });
        start_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Jump.class);
                setContentView(R.layout.gaming_mode);
                startActivity(intent);
                ImageView role = findViewById(R.id.PlayRole);
                role.setImageResource(R.drawable.turtle1);
                Jump.type = 2;
            }
        });
        start_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Jump.class);
                setContentView(R.layout.gaming_mode);
                startActivity(intent);
                ImageView role = findViewById(R.id.PlayRole);
                role.setImageResource(R.drawable.rabbit1);
                Jump.type = 3;
            }
        });
    }
}
