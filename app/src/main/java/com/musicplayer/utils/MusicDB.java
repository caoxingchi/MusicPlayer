package com.musicplayer.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.musicplayer.pojo.Song;

import java.util.List;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/13 8:51
 * @version: 1.0
 */
public class MusicDB {
    private DbUtils dbUtils;
    private Context context;
    private static final String TAG = "MusicDB";

    public MusicDB() {
        super();
    }

    public MusicDB(Context context,DbUtils dbUtils) {
        this.context = context;
        this.dbUtils=dbUtils;
    }

    /**
     *@author -->> itcolors <<----
     *@time  8:55
     *@description 保存到数据库中
     */
    public void saveMusic(Song song){
        try {
            dbUtils.save(song);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     *@author -->> itcolors <<----
     *@time  9:02
     *@description 查询所有的music
     */
    public List<Song> findAllMusic(){
        List<Song> songList=null;
        try {
            songList=dbUtils.findAll(Song.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return songList;
    }

    /**
     *@author -->> itcolors <<----
     *@time  9:03
     *@description 根据关键词进行查询
     */
    public boolean findByKey(Song scan_song){
        boolean isExist=false;
        try {
            Song song=dbUtils.findFirst(Selector.from(Song.class).where("song_name","=",scan_song.getSong_name()));
            if(song!=null){
                isExist=true;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    /**
     *@author -->> itcolors <<----
     *@time  9:04
     *@description 根据关键词进行删除操作有bug，删除仍然未成功
     */
    public void deleteByKey(long id){
        try {
            dbUtils.delete(Song.class, WhereBuilder.b("id","=",id));
            Log.i(TAG, "deleteByKey: 删除成功！");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     *@author -->> itcolors <<----
     *@time  9:07
     *@description 更新数据
     */
    public void updateByKey(){

    }
}
