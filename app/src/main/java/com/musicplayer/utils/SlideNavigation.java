package com.musicplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.rebound.SpringConfig;
import com.jpeng.jpspringmenu.MenuListener;
import com.jpeng.jpspringmenu.SpringMenu;
import com.musicplayer.R;
import com.musicplayer.pojo.SlideMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingchi
 * @version 1.0
 * @date 2020/6/3 9:00
 * 侧边工具栏
 */
public class SlideNavigation {
    private Context context;
    private SpringMenu springMenu;
    private int qcTension;
    private int qcFriction;

    public int getQcTension() {
        return qcTension;
    }

    public void setQcTension(int qcTension) {
        this.qcTension = qcTension;
    }

    public int getQcFriction() {
        return qcFriction;
    }

    public void setQcFriction(int qcFriction) {
        this.qcFriction = qcFriction;
    }

    public SlideNavigation() {
        super();
    }

    public SlideNavigation(Context context, SpringMenu springMenu) {
        this.context = context;
        this.springMenu = springMenu;
    }

    public void initSlideMenu(){

        setQcTension(0);
        setQcFriction(0);
        springMenu.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(qcTension,qcFriction));
        // 内容页变暗的效果
        springMenu.setFadeEnable(false);
        //面板在左边
        springMenu.setDirection(SpringMenu.DIRECTION_LEFT);
        // 允许菜单开始拖动的距离
        springMenu.setDragOffset(0.4f);

       /* //动画面板
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        *//**
         * Scale
         *//*
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        AnimationSet set=new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);

        springMenu.setAnimation(set);*/

    }


}
