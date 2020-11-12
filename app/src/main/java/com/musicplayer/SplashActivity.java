package com.musicplayer;

import android.content.Intent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * @author xingchi -->> itcolors <<----
 * @version 1.0
 * @date 2020/6/10 17:33
 * @description 闪屏页面 APP进入入口
 */
public class SplashActivity extends AppCompatActivity implements Init {

    private LinearLayout splashLl;
    private ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initData();
    }

    /**
     * @author -->> itcolors <<----
     * @time 17:50
     * @description 初始化
     */
    @Override
    public void initView() {
        splashLl = (LinearLayout) findViewById(R.id.splash_ll);
        imgSplash = (ImageView) findViewById(R.id.img_splash);
    }

    @Override
    public void initData() {
        /**
         * Scale
         */
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        /**
         * Alpha
         */
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        /**
         * gather
         */
        AnimationSet set = new AnimationSet(false);//accelerate speed;
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);

        splashLl.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
