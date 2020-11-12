package com.musicplayer.utils;

import android.app.Application;

import com.lidroid.xutils.DbUtils;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/13 0:30
 * @version: 1.0
 */
public class MyApplication extends Application {
    private DbUtils dbUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        //内存不足的使用将会删除全部的数据,不推荐
        //dbUtils=DbUtils.create(this,"music_db");
    }
}
