package com.example.a17479.finalproject;


import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class Jump extends AppCompatActivity {
    public boolean jumpState = false;
    public int jumpSpeed = -100;
    public int acc = 25;
    public int currentH = 310;
    public boolean startRun = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaming_mode);
        final TextView textView = findViewById(R.id.tapScreen);
        final ImageView background = findViewById(R.id.GroundImage);
        final ImageView background_fill = findViewById(R.id.groundImage);
        final ImageView playRole = findViewById(R.id.PlayRole);
        background.setImageResource(R.drawable.desert2);
        background_fill.setImageResource(R.drawable.desert2);


        final Thread myThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animation = new TranslateAnimation(0,-1600,0,0);
                animation.setDuration(4000);
                animation.setFillAfter(true);
                animation.setRepeatCount(-1);
                animation.setRepeatMode(Animation.RESTART);
                animation.setInterpolator(new LinearInterpolator());
                background.setAnimation(animation);

                Animation animation_fill = new TranslateAnimation(0,-1600,0,0);
                animation_fill.setDuration(4000);
                animation_fill.setFillAfter(true);
                animation_fill.setRepeatCount(-1);
                animation_fill.setRepeatMode(Animation.RESTART);
                animation_fill.setInterpolator(new LinearInterpolator());
                background_fill.setAnimation(animation);
            }
        });
        final ImageButton jump = findViewById(R.id.JumpButton);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!startRun) {
                    textView.setVisibility(View.INVISIBLE);
                    myThread.run();
                    startRun = true;
                }
                jumpState = true;
                Animation jump = new TranslateAnimation(0,0,-360,0);
                jump.setDuration(500);
                playRole.setAnimation(jump);
//                Animation jump2 = new TranslateAnimation(0,0,0,360);
//                jump.setDuration(500);
//                playRole.setAnimation(jump2);
            }
        });

    }
}
