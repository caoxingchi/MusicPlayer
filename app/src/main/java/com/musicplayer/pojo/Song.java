package com.musicplayer.pojo;

import com.lidroid.xutils.db.annotation.Column;

import java.io.Serializable;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/8 11:45
 * @version: 1.0
 */
public class Song implements Serializable {
    /**
     * 编号
     */
    @Column(column = "id")
    private long id;
    /**
     * 歌手
     */
    @Column(column = "singer")
    private String singer;
    /**
     * 歌曲名
     */
    @Column(column = "song_name")
    private String song_name;
    /**
     * 专辑
     */
    @Column(column = "album")
    private String album;
    /**
     * 专辑图片
     */
    @Column(column = "album_art")
    private String album_art;
    /**
     * 歌曲地址
     */
    @Column(column = "path")
    private String path;
    /**
     * 歌曲长度
     */
    @Column(column = "duration")
    private long duration;
    /**
     * 歌曲大小
     */
    @Column(column = "size")
    private long size;

    //是否正在播放
    private boolean isPlaying;
    //当前的播放进度
    private long currentPlayProgress;

    private int currentPosition;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public long getCurrentPlayProgress() {
        return currentPlayProgress;
    }

    public void setCurrentPlayProgress(long currentPlayProgress) {
        this.currentPlayProgress = currentPlayProgress;
    }

    public Song() {
        super();
    }

    public Song(String singer, String song_name) {
        this.singer = singer;
        this.song_name = song_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", singer='" + singer + '\'' +
                ", song_name='" + song_name + '\'' +
                ", album='" + album + '\'' +
                ", album_art='" + album_art + '\'' +
                ", path='" + path + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                '}';
    }
}
