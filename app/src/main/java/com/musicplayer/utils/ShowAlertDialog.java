package com.musicplayer.utils;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/9 13:39
 * @version: 1.0
 */
public class ShowAlertDialog {
    /**
     *@author -->> itcolors <<----
     *@time  13:40
     *@description 返回响应 --> 成功
     */
    public static void showOKDialog(Context context,String text){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(text)
                .show();
    }

    /**
     *@author -->> itcolors <<----
     *@time  13:41
     *@description 返回响应 --> 失败
     */
    public static void showFailedDialog(Context context,String text){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setContentText(text)
                .show();
    }

    public static void showWarningDialog(Context context,String text){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setContentText(text)
                .show();
    }

}
