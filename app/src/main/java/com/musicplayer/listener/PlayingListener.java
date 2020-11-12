package com.musicplayer.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.musicplayer.MainActivity;
import com.musicplayer.PlayMusicActivity;
import com.musicplayer.pojo.Song;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 14:11
 * @version: 1.0
 */
public class PlayingListener implements View.OnClickListener {
    private Context context;
    private List<Song> songList;

    public PlayingListener() {
        super();
    }

    public PlayingListener(Context context,List<Song> songList) {
        this.context = context;
        this.songList=songList;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity=(MainActivity)((Activity)context);
        Song currentPlayingSong = mainActivity.getContentFragment().getCurrentPlayingSong();
        Intent intent=new Intent(context, PlayMusicActivity.class);
        intent.putExtra("songList",(Serializable) songList);
        intent.putExtra("currentPlayingSong",(Serializable)currentPlayingSong);
        //intent.putExtra("myBinder",(Serializable) mainActivity.getContentFragment().getMyBinder());
        context.startActivity(intent);
    }
}
