package com.musicplayer.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.jpeng.jpspringmenu.SpringMenu;
import com.musicplayer.MainActivity;
import com.musicplayer.ScanMusicActivity;
import com.musicplayer.utils.ShowAlertDialog;
import com.musicplayer.utils.ShowPopup;
import com.musicplayer.utils.ShowToast;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 22:53
 * @version: 1.0
 */
public class ClickSlideMenuItemListener{

    //主题
    private final int THEME_ITEM=0;
    //定时停止播放
    private final int SET_TIME_SLEEP_ITEM=1;
    //切换夜间模式
    private final int SWITCH_NIGHT_ITEM=2;
    //private 扫描本地音乐
    private final int SCAN_MUSIC_ITEM=3;
    //关于我们
    private final int ABOUT_US_ITEM=4;

    private int position;
    private Context context;

    //侧边导航栏
    private SpringMenu slideMenu;
    private Intent intent;

    public ClickSlideMenuItemListener(int position, Context context) {
        this.position = position;
        this.context = context;
    }


    public void init(){
        MainActivity mainUI=(MainActivity)((Activity)context);
        //获取侧边导航栏
        slideMenu = mainUI.getContentFragment().getSlideMenu();
    }
    /**
     *@author -->> itcolors <<----
     *@time  23:01
     *@description 点击左侧导航栏的item,传入SpringMenu进行关闭操作
     */
    public void onClickListener(View v) {
        init();
        switch (position){
            case THEME_ITEM:{
                ShowAlertDialog.showWarningDialog(context,"主题 ----> 待开发哦!!!");
                break;
            }
            case SET_TIME_SLEEP_ITEM:{
                ShowToast.showShortToast(context,"定时停止");
                break;
            }
            case SWITCH_NIGHT_ITEM:{
                ShowToast.showShortToast(context,"夜间模式");
                break;
            }
            case SCAN_MUSIC_ITEM:{
                slideMenu.closeMenu();
                ToScanMusic();
                //ShowToast.showShortToast(context,"扫描本地");
                break;
            }
            case ABOUT_US_ITEM:{
                new ShowPopup(context).showAboutUS(v);
                //ShowToast.showShortToast(context,"关于我们");
                break;
            }

        }
    }

    //跳转到扫描界面
    private void ToScanMusic(){
        slideMenu.closeMenu();
        intent=new Intent(context, ScanMusicActivity.class);
        context.startActivity(intent);
    }
}
