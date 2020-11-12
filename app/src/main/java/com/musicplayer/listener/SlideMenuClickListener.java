package com.musicplayer.listener;

import android.content.Context;
import android.view.View;

import com.musicplayer.R;
import com.musicplayer.utils.ShowAlertDialog;
import com.musicplayer.utils.ShowToast;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 18:28
 * @version: 1.0
 */
public class SlideMenuClickListener implements View.OnClickListener {


    private Context context;

    public SlideMenuClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.slide_exit_ll:
            {
                ShowAlertDialog.showOKDialog(context,"退出成功");
                break;
            }
            case R.id.slide_setting_ll:{
                ShowToast.showShortToast(context,"设置操作");
            }
        }
    }
}
