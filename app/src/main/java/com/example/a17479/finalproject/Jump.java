package com.example.a17479.finalproject;
//package edu.illinois.cs.cs125.lab11;


import android.app.Activity;
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
    private final String api_key = "5af609cc3c737850c2826865d096692aaa34607577a63f64b16d0fc5f0fa9d90";
    public boolean collide = false;
    public static boolean jumpState = false;
    public static boolean scoreK = false;
    public static boolean barrM = false;
    public static boolean roll = false;
    public boolean startRun = false;
    public static int speed = -60;
    public static int acc = 6;
    public static int type;
    private static int score = 0;
    TextView tap;//提示点击开始
    TextView score_num;//成绩
    static ImageView background;//背景1
    ImageView background_fill;//背景2
    static ImageView playRole;//角色
    static ImageView barrier1;
    public Activity activity = this;


    private static RequestQueue requestQueue;
    private static final String TAG = "Youheihei";

    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.unsplash.com/photos/random?client_id=" + api_key,
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
            Log.d(TAG, "startAPICall error: " + e.toString());
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
            Picasso.get().load(url).into(barrier1);
            // Example of how to pull a field off the returned JSON object
            //Log.i(TAG, response.get("hostname").toString());
        } catch (JSONException e) {
            Log.d(TAG, "apiCallDone error: " + e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaming_mode);
        requestQueue = Volley.newRequestQueue(this);
        final Handler handler = new Handler();//程序停止开始控制

        tap = findViewById(R.id.tapScreen);
        score_num = findViewById(R.id.score_num);
        background = findViewById(R.id.GroundImage);
        background_fill = findViewById(R.id.groundImage);
        playRole = findViewById(R.id.PlayRole);
        barrier1 = findViewById(R.id.Barrier1);


        try {
            startAPICall();
        } catch (Exception e) {
            Log.d(TAG, "api call error: " + e.toString());
        }

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
        final Thread scoreKeeper = new Thread(new Runnable() {
            @Override
            public void run() {
                score_num.setText(String.valueOf(score));
                handler.postDelayed(this, 800);
                if (scoreK) {
                    score++;
                }
            }
        });

//背景滚动
        final Thread rolling = new Thread(new Runnable() {
            @Override
        public void run() {
                Animation animation = new TranslateAnimation(0, -1780, 0, 0);
                animation.setDuration(4000);
                animation.setFillAfter(true);
                animation.setRepeatCount(-1);
                animation.setRepeatMode(Animation.RESTART);
                animation.setInterpolator(new LinearInterpolator());
                background.setAnimation(animation);

                Animation animation_fill = new TranslateAnimation(0, -1780, 0, 0);
                animation_fill.setDuration(4000);
                animation_fill.setFillAfter(true);
                animation_fill.setRepeatCount(-1);
                animation_fill.setRepeatMode(Animation.RESTART);
                animation_fill.setInterpolator(new LinearInterpolator());
                background_fill.setAnimation(animation);
            }
        });
//角色运动
        final Thread animation = new Thread(new Runnable() {
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

//跳跃按钮
        final jump jump = new jump(new Activity());
        final Move moveB = new Move();
        final ImageButton jumpButton = findViewById(R.id.JumpButton);
//        final Collision collision = new Collision();
        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!startRun) {
                    tap.setVisibility(View.INVISIBLE);
                    score = 0;
                    rolling.run();
                    animation.run();
                    if (!scoreK) {
                        scoreKeeper.run();
                    }
                    //barrier.run();
                    startRun = true;
                    jumpState = true;
                    scoreK = true;
                    barrM = true;
                    jump.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    moveB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                jumpState = true;
            }
        });
    }


        static class Move extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected Void doInBackground(Void... voids) {
                do {
                    if (barrM) {
                        if (barrier1.getX() > -100) {
                            barrier1.setX(barrier1.getX() - 26);
                        } else {
                            barrier1.setX(2000);
                        }
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } while (true);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        }
        static class jump extends AsyncTask<Void, Void, Void> {
            private Activity activity;

            jump(Activity activity) {
                this.activity = activity;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected Void doInBackground(Void... voids) {
                do {
                    int[] roleloc = new int[2];
                    playRole.getLocationInWindow(roleloc);
                    int[] barrloc = new int[2];
                    barrier1.getLocationInWindow(barrloc);
                    Rect role = new Rect(roleloc[0], roleloc[1], roleloc[0] + playRole.getWidth(), roleloc[1] + playRole.getHeight());
                    Rect barr = new Rect(barrloc[0], barrloc[1], barrloc[0] + barrier1.getWidth(), barrloc[1] + barrier1.getHeight());
                    if (role.intersect(barr)) {
                        break;
                    }
                    if (jumpState) {
                        if (speed <= 60) {
                            playRole.setY(playRole.getY() + speed);
                            speed += acc;
                        } else {
                            speed = -60;
                            jumpState = false;
                        }
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                speed = -60;
                jumpState = false;
                scoreK = false;
                barrM = false;
                background.clearAnimation();
                Start.lastScore = score;
                if (score > Start.highScore) {
                    Start.highScore = score;
                }
            }
        }
    }