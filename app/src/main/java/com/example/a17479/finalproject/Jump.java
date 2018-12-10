package com.example.a17479.finalproject;


import android.content.Intent;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import java.util.Random;


public class Jump extends AppCompatActivity {
    public boolean jumpState = false;
    public boolean startRun = false;
    public static int type;
    private int score = 0;
    public int x, y;
    public boolean alive = true;
    public int[] dino = {R.drawable.dino1, R.drawable.dino2};
    public int[] turtle = {R.drawable.turtle1, R.drawable.turtle2};
    public int[] rabbit = {R.drawable.rabbit1, R.drawable.rabbit2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaming_mode);
        final Handler handler = new Handler();//程序停止开始控制
        final TextView tap = findViewById(R.id.tapScreen);//提示点击开始
        final TextView score_num = findViewById(R.id.score_num);//成绩
        final ImageView background = findViewById(R.id.GroundImage);//背景1
        final ImageView background_fill = findViewById(R.id.groundImage);//背景2
        final ImageView playRole = findViewById(R.id.PlayRole);//角色
        final ImageView barrier1 = findViewById(R.id.Barrier1);
        final ImageView barrier2 = findViewById(R.id.Barrier2);

//设定动物图片
        if (type == 1) {
            playRole.setImageResource(R.drawable.dino1);
        }
        if (type == 2) {
            playRole.setImageResource((R.drawable.turtle1));
        }
        if (type == 3) {
            playRole.setImageResource((R.drawable.rabbit1));
        }
        background.setImageResource(R.drawable.desert2);
        background_fill.setImageResource(R.drawable.desert2);

//返回主界面
        final Button back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jump.this, Start.class);
                setContentView(R.layout.activity_jump);
                startActivity(intent);
                Start.lastScore = score;
                if (score > Start.highScore) {
                    Start.highScore = score;
                }
            }
        });

//时间计数器
        final Runnable scoreKeeper = new Runnable() {
            @Override
            public void run() {
                score_num.setText(String.valueOf(score));
                handler.postDelayed(this, 800);
                score++;
            }
        };

//背景滚动
        final Thread run = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animation = new TranslateAnimation(0,-1780,0,0);
                animation.setDuration(4000);
                animation.setFillAfter(true);
                animation.setRepeatCount(-1);
                animation.setRepeatMode(Animation.RESTART);
                animation.setInterpolator(new LinearInterpolator());
                background.setAnimation(animation);

                Animation animation_fill = new TranslateAnimation(0,-1780,0,0);
                animation_fill.setDuration(4000);
                animation_fill.setFillAfter(true);
                animation_fill.setRepeatCount(-1);
                animation_fill.setRepeatMode(Animation.RESTART);
                animation_fill.setInterpolator(new LinearInterpolator());
                background_fill.setAnimation(animation);
            }
        });
//角色运动
        final Thread move = new Thread(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 1) {
                            playRole.setImageResource(R.drawable.dino2);
                            handler.postDelayed(this, 300);
                            playRole.setImageResource(R.drawable.dino1);
                        } else if (type == 2) {
                            playRole.setImageResource(R.drawable.turtle2);
                            handler.postDelayed(this, 300);
                            playRole.setImageResource(R.drawable.turtle1);
                        } else if (type == 3) {
                            playRole.setImageResource(R.drawable.rabbit2);
                            handler.postDelayed(this, 300);
                            playRole.setImageResource(R.drawable.rabbit2);
                        }
                    }
                }, 2000);
            }
        });
//跳跃
        final Thread jump = new Thread(new Runnable() {
            @Override
            public void run() {
                Animation jump = new TranslateAnimation(0,0,-360,0);
                jump.setDuration(800);
                playRole.setAnimation(jump);
                jumpState = false;
                //run2();
            }
            public void run2() {
                Animation jump = new TranslateAnimation(0,0,0,-360);
                jump.setDuration(800);
                playRole.setAnimation(jump);
            }
        });

//障碍物
        final Thread barrier = new Thread(new Runnable() {
            @Override
            public void run() {
                int ranSpeed = new Random(100).nextInt(200);
                Animation jump = new TranslateAnimation(0,-1780 - ranSpeed,0,0);
                jump.setDuration(3000);
                jump.setRepeatCount(-1);
                jump.setRepeatMode(Animation.RESTART);
                jump.setInterpolator(new LinearInterpolator());
                barrier1.setAnimation(jump);
            }
        });

//跳跃按钮
        final ImageButton jumpButton = findViewById(R.id.JumpButton);
        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!startRun) {
                    tap.setVisibility(View.INVISIBLE);
                    run.run();
                    move.run();
                    scoreKeeper.run();
                    barrier.run();
                    startRun = true;
                }
                if (!jumpState) {
                    try {
                        jumpState = true;
                        jump.run();
                        jump.join();
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }
}
