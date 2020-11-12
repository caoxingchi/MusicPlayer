package com.musicplayer.utils;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.musicplayer.R;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 23:43
 * @version: 1.0
 */
public class ShowPopup {

    private Context context;
    private QMUIPopup mNormalPopup;

    public ShowPopup(Context context) {
        this.context = context;
    }

    /**
     *@author -->> itcolors <<----
     *@time  23:44
     *@description 展示关于我们
     */
    public void showAboutUS(View v){
        TextView textView = new TextView(context);
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(context, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(context, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(R.string.about_us);
        textView.setTextColor(
                QMUIResHelper.getAttrColor(context, R.attr.app_skin_common_title_text_color));
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        builder.textColor(R.attr.app_skin_common_title_text_color);
        QMUISkinHelper.setSkinValue(textView, builder);
        builder.release();
        mNormalPopup = QMUIPopups.popup(context, QMUIDisplayHelper.dp2px(context, 250))
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                .view(textView)
                .edgeProtection(QMUIDisplayHelper.dp2px(context, 20))
                .dimAmount(0.6f)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .skinManager(QMUISkinManager.defaultInstance(context))
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //Toast.makeText(context, "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .show(v);

    }
}
