package com.musicplayer.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/9 10:43
 * @version: 1.0
 */
public class ShowToast {
    /**
     *@author -->> itcolors <<----
     *@time  10:46
     *@description 短时间显示Toast
     */
    public static void showShortToast(Context context,CharSequence text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    /**
     *@author -->> itcolors <<----
     *@time  10:46
     *@description  长时间显示Toast
     */
    public static void showLongToast(Context context,CharSequence text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
