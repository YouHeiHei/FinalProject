package com.example.a17479.finalproject;
//package edu.illinois.cs.cs125.lab11;


import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
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

import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Random;


public class Jump extends AppCompatActivity {
    public boolean collide = false;
    public boolean jumpState = false;
    public boolean startRun = false;
    public int speed = -80;
    public int acc = 10;
    public static int type;
    private int score = 0;
    TextView tap;//提示点击开始
    TextView score_num;//成绩
    ImageView background;//背景1
    ImageView background_fill;//背景2
    ImageView playRole;//角色
    ImageView barrier1;
    ImageView barrier2;

    private static RequestQueue requestQueue;
    private static final String TAG = "Youheihei";

    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.unsplash.com/photos/random",
                    null,
                    new Response.Listener<JSONObject>() {
                        //@Override
                        public void onResponse(final JSONObject response) {
                            apiCallDone(response);
                        }
                    }, new Response.ErrorListener() {
                //@Override
                public void onErrorResponse(final VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            jsonObjectRequest.setShouldCache(false);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void apiCallDone(final JSONObject response) {
        try {
            Log.d(TAG, response.toString(2));
            //TextView showapi = findViewById(R.id.showapi);
            //showapi.setText("api get");
            JSONObject urls = response.getJSONObject("urls");
            String url = urls.getString("raw");
            Log.d(TAG, "Url is" + url);
            Picasso.get().load("url").into(barrier1);
            // Example of how to pull a field off the returned JSON object
            //Log.i(TAG, response.get("hostname").toString());
        } catch (JSONException ignored) { }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaming_mode);
        final Handler handler = new Handler();//程序停止开始控制

        tap = findViewById(R.id.tapScreen);
        score_num = findViewById(R.id.score_num);
        background = findViewById(R.id.GroundImage);
        background_fill = findViewById(R.id.groundImage);
        playRole = findViewById(R.id.PlayRole);
        barrier1 = findViewById(R.id.Barrier1);
        barrier2 = findViewById(R.id.Barrier2);
        startAPICall();

//设定动物图片
        if (type == 1) {
            playRole.setBackgroundResource(R.drawable.role_moving1);
            AnimationDrawable animation1 = (AnimationDrawable) playRole.getBackground();
            animation1.setOneShot(false);   //设置是否只播放一次，和上面xml配置效果一致
            animation1.start();             //启动动画
        } else if (type == 2) {
            playRole.setBackgroundResource(R.drawable.role_moving2);
            AnimationDrawable animation2 = (AnimationDrawable) playRole.getBackground();
            animation2.setOneShot(false);   //设置是否只播放一次，和上面xml配置效果一致
            animation2.start();             //启动动画
        } else if (type == 3) {
            playRole.setBackgroundResource(R.drawable.role_moving3);
            AnimationDrawable animation3 = (AnimationDrawable) playRole.getBackground();
            animation3.setOneShot(false);   //设置是否只播放一次，和上面xml配置效果一致
            animation3.start();             //启动动画
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
                 if (type == 1) {
                     playRole.setBackgroundResource(R.drawable.role_moving1);
                     AnimationDrawable animation1 = (AnimationDrawable) playRole.getBackground();
                     animation1.setOneShot(false);   //设置是否只播放一次，和上面xml配置效果一致
                     animation1.start();             //启动动画
                } else if (type == 2) {
                     playRole.setBackgroundResource(R.drawable.role_moving2);
                     AnimationDrawable animation2 = (AnimationDrawable) playRole.getBackground();
                     animation2.setOneShot(false);   //设置是否只播放一次，和上面xml配置效果一致
                     animation2.start();             //启动动画
                } else if (type == 3) {
                     playRole.setBackgroundResource(R.drawable.role_moving3);
                     AnimationDrawable animation3 = (AnimationDrawable) playRole.getBackground();
                     animation3.setOneShot(false);   //设置是否只播放一次，和上面xml配置效果一致
                     animation3.start();             //启动动画
                }
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
                barrier2.setAnimation(jump);
            }
        });
//跳跃
//        final Thread jump = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Animation jump = new TranslateAnimation(0,0,-360,0);
//                jump.setDuration(800);
//                playRole.setAnimation(jump);
//                jumpState = false;
//                //run2();
//            }
//            public void run2() {
//                Animation jump = new TranslateAnimation(0,0,0,-360);
//                jump.setDuration(800);
//                playRole.setAnimation(jump);
//            }
//        });
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
                    jump jump = new jump();
                    jump.execute();
                }
                jumpState = true;
//                if (!jumpState) {
//                    jumpState = true;
//                    //jump.run();
//                }
                Collision collision = new Collision();
                collision.execute();
            }
        });
    }

    class Collision extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            do {
                if (!collide) {
                    //collide = true;
                    Rect play = new Rect();
                    playRole.getHitRect(play);
                    double px = play.centerX();
                    double py = play.centerY();
                    Rect bar1 = new Rect();
                    barrier1.getHitRect(bar1);
                    double b1x = bar1.centerX();
                    double b1y = bar1.centerY();
                    Rect bar2 = new Rect();
                    barrier2.getHitRect(bar2);
                    int b2x = bar2.centerX();
                    int b2y = bar2.centerY();
                    if (Math.pow((b1x - px), 2) + Math.pow((b1y - py), 2) < 10000) {
                        collide = true;
                    } else if (Math.pow((b2x - px), 2) + Math.pow((b2y - py), 2) < 10000) {
                        collide = true;
                    }
                } else {
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            } while (true);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(Jump.this, Start.class);
            setContentView(R.layout.activity_jump);
            startActivity(intent);
            Start.lastScore = score;
            if (score > Start.highScore) {
                Start.highScore = score;
            }
        }
    }

//跳跃
//    final Thread jump = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            Animation jump = new TranslateAnimation(0,0,-360,0);
//            jump.setDuration(800);
//            playRole.setAnimation(jump);
//            jumpState = false;
//            //run2();
//        }
//        public void run2() {
//            Animation jump = new TranslateAnimation(0,0,0,-360);
//            jump.setDuration(800);
//            playRole.setAnimation(jump);
//        }
//    });

    class jump extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Void doInBackground(Void... voids) {
            do {
                if (speed <= 80) {
                    playRole.setY(playRole.getY() + speed);
                    speed += acc;
                } else {
                    speed = -80;
                    jumpState = false;
                }
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jumpState = false;
        }
    }
}
