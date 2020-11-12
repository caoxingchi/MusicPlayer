package com.musicplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;


import com.musicplayer.pojo.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/13 15:19
 * @version: 1.0
 */
public class ScanMusicUtil {


    /**
         * 扫描系统里面的音频文件，返回一个list集合
         */
        public static List<Song> getMusicData(Context context,MusicDB musicDB) {
            List<Song> list = new ArrayList<Song>();
            // 媒体库查询语句（写一个工具类MusicUtils）
            Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, MediaStore.Audio.AudioColumns.IS_MUSIC);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Song song = new Song();
                    song.setSong_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))) ;//歌曲名称
                    song.setSinger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));//歌手
                    song.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));;//专辑名
                    song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));//歌曲路径
                    song.setDuration (cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));//歌曲时长
                    song.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));;//歌曲大小

                    if (song.getSize() > 1000 * 800) {
                        // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                        if (song.getSong_name().contains("-")) {
                            String[] str = song.getSong_name().split("-");
                            song.setSinger(str[0]);
                            song.setSong_name( str[1]);
                        }
                        //避免第二次扫描的时候又重新将相同的数据加载进去了
                        if(!musicDB.findByKey(song)){
                            musicDB.saveMusic(song);
                            list.add(song);
                        }

                    }
                }
                // 释放资源
                cursor.close();
            }
            return list;
        }
        //专辑图片
        private static String imgUrl(Context context){
            String album_art= null;
            String[] mediaColumns1 = new String[] {MediaStore.Audio.Albums.ALBUM_ART, MediaStore.Audio.Albums.ALBUM};

            Cursor cursor1 = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, mediaColumns1, null, null,
                    null);

            if (cursor1 != null) {
                cursor1.moveToFirst();
                do {
                    album_art =  cursor1.getString(0);
                    if (album_art != null) {
                        Log.d("ALBUM_ART", album_art);
                    }

                    String album =  cursor1.getString(1);
                    if (album != null) {
                        Log.d("ALBUM_ART", album);
                    }

                } while (cursor1.moveToNext());

                cursor1.close();
            }
            return album_art;
        }

        /**
         * 定义一个方法用来格式化获取到的时间
         */
        public static String formatTime(long time) {
            if (time / 1000 % 60 < 10) {
                return time / 1000 / 60 + ":0" + time / 1000 % 60;

            } else {
                return time / 1000 / 60 + ":" + time / 1000 % 60;
            }

        }

    }
