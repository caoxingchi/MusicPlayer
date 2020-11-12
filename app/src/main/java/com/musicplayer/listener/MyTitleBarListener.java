package com.musicplayer.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.jpeng.jpspringmenu.SpringMenu;
import com.musicplayer.ScanMusicActivity;


/**
 * @author xingchi
 * @version 1.0
 * @date 2020/6/4 13:56
 */
public class MyTitleBarListener implements OnTitleBarListener {
    private Context context;
    private Activity activity;
    private TitleBar titleBar;
    private SpringMenu menu;

    public MyTitleBarListener(Context context,SpringMenu menu) {
        this.context = context;
        this.activity=(Activity) context;
        this.menu=menu;
    }

    public MyTitleBarListener(Context context) {
        this.context = context;
        this.activity=(Activity) context;
    }

    @Override
    public void onLeftClick(View v) {
        if(context.getClass().equals(ScanMusicActivity.class)){
            activity.finish();
        }else {
            menu.openMenu();
        }
    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

    }
}
