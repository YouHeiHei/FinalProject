package com.example.a17479.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class Start extends AppCompatActivity {

    protected void button(Button button) {
        final Button start_1 = findViewById(R.id.start_button1);
        final Button start_2 = findViewById(R.id.start_button2);
        final Button start_3 = findViewById(R.id.start_button3);
        start_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Start.this, Jump.class));
            }
        });
        start_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Start.this, Jump.class));
            }
        });
        start_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Start.this, Jump.class));
            }
        });
    }
}
