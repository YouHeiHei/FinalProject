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
import java.net.URL;

public class Start extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        final Button start_1 = findViewById(R.id.start_button1);
        final Button start_2 = findViewById(R.id.start_button2);
        final Button start_3 = findViewById(R.id.start_button3);
        start_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Jump.class);
                setContentView(R.layout.gaming_mode);
                startActivity(intent);
                ImageView role = (ImageView) findViewById(R.id.PlayRole);
                role.setImageResource(R.drawable.dino1);
            }
        });
        start_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Jump.class);
                setContentView(R.layout.gaming_mode);
                startActivity(intent);
                ImageView role = (ImageView) findViewById(R.id.PlayRole);
                role.setImageResource(R.drawable.turtle1);
            }
        });
        start_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Jump.class);
                setContentView(R.layout.gaming_mode);
                startActivity(intent);
                ImageView role = (ImageView) findViewById(R.id.PlayRole);
                role.setImageResource(R.drawable.rabbit1);
            }
        });
    }
}
