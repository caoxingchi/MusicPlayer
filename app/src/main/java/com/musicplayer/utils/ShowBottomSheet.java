package com.musicplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.musicplayer.R;
import com.musicplayer.pojo.Song;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;


import java.util.List;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/10 20:19
 * @version: 1.0
 */
public class ShowBottomSheet {
    private Activity activity;
    private Context context;
    private int currentPosition;
    private static final String TAG = "ShowBottomSheet";

    public ShowBottomSheet(Context context) {
        this.context = context;
        this.activity=(Activity)context;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void showShareBottomSheet() {
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WEIBO = 1;
        final int TAG_SHARE_WECHAT_MOMENT =2;
        final int TAG_SHARE_QQ = 3;
        final int TAG_SHARE_LOCAL = 4;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
        builder.addItem(R.mipmap.icon_share_wechat, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_share_weibo, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_share_moment, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_share_save, "保存到本地", TAG_SHARE_LOCAL, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.icon_share_qq, "分享到QQ", TAG_SHARE_QQ, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setAddCancelBtn(true)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView) {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_SHARE_WECHAT_FRIEND:
                                ShowAlertDialog.showOKDialog(context,"已分享到微信");
                                break;

                            case TAG_SHARE_WEIBO:
                                ShowAlertDialog.showOKDialog(context,"已分享到微博");
                                break;

                            case TAG_SHARE_QQ:
                                ShowAlertDialog.showOKDialog(context,"已分享到QQ");
                                break;
                            case TAG_SHARE_WECHAT_MOMENT:
                                ShowAlertDialog.showOKDialog(context,"已分享到朋友圈");
                                break;
                            case TAG_SHARE_LOCAL:
                                ShowAlertDialog.showOKDialog(context,"已下载到本地");
                                break;
                        }
                    }
                }).build().show();
    }

    /**
     *@author -->> itcolors <<----
     *@time  14:52
     *@description 展示bottom_sheet_dialog
     */
    public void showMusicListBottomSheet(boolean addCancelBtn,
                                      CharSequence title,
                                      final List<Song> songList,
                                      boolean allowDragDismiss,
                                      final boolean withMark) {
        currentPosition=0;
        final QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(context);
        builder.setGravityCenter(false)
                .setTitle(title)
                .setAddCancelBtn(addCancelBtn)
                .setAllowDrag(allowDragDismiss)
                .setNeedRightMark(withMark)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {

                        ShowToast.showShortToast(context,"播放"+songList.get(position).getSong_name());
                        setCurrentPosition(position);
                        builder.setCheckedIndex(currentPosition);
                        Log.i(TAG, "onClick: currentPosition"+currentPosition);
                        //dialog.dismiss();
                    }
                });
        for (Song song:songList) {
            builder.addItem(song.getSong_name()+"----"+song.getSinger());
        }

        Log.i(TAG, "showBottomSheetDialog: currentPosition"+currentPosition);
       /* if(withMark){
            builder.setCheckedIndex(currentPosition);
        }*/
        builder.setSkinManager(QMUISkinManager.defaultInstance(context));
        builder.build().show();

    }
}
