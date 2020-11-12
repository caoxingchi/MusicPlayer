package com.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class AudioService extends Service {

    private MediaPlayer mediaPlayer;
    private static final String TAG = "AudioService";

    //private boolean isPlaying=false;
    //用来判断是否播放的同一首歌
    private String currentPlayPath;

    public AudioService() {
    }

    public class MyBinder extends Binder {

        public AudioService getService(){
            return AudioService.this;
        }
        //获取当前的进度
        public int getCurrentPlayProgress() {
            return getCurrentProgress();
        }

        public boolean isPlaying(){
            if(mediaPlayer!=null){
                return mediaPlayer.isPlaying();
            }
            return false;
        }


        /**
         *@author -->> itcolors <<----
         *@time  12:54
         *@description 切换音乐的场景：
         * 1、当mediaPlayer为空时：
         *
         */
        public void startPlay(String path){

            if(mediaPlayer==null){
                try {
                    currentPlayPath=path;
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                            //isPlaying=true;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //当当前仍在播放，再次点击获取当前的播放位置，继续播放
            }else if(mediaPlayer!=null&&mediaPlayer.isPlaying()&&isSameSong(path)){
                /*//mediaPlayer.reset();
                //int currentProgressPosition=getCurrentProgress();
                //Log.i(TAG, "startPlay: "+currentProgressPosition);
                //mediaPlayer.seekTo(currentProgressPosition);
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                //当切换音乐的时候重置，并且换路径播放
            }else if(mediaPlayer!=null&&mediaPlayer.isPlaying()&&!isSameSong(path)){
                //mediaPlayer.stop();
                mediaPlayer.reset();
                try {
                    currentPlayPath=path;
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                            //isPlaying=true;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if(mediaPlayer!=null&&!(mediaPlayer.isPlaying())&&isSameSong(path)){
                int currentProgressPosition=getCurrentProgress();
                //Log.i(TAG, "startPlay: "+currentProgressPosition);
                mediaPlayer.seekTo(currentProgressPosition);
                try {
                    //mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(mediaPlayer!=null&&!(mediaPlayer.isPlaying())&&!isSameSong(path)){
                mediaPlayer.reset();
                try {
                    currentPlayPath=path;
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                            //isPlaying=true;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void stopPlay(){
            if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                //isPlaying=false;
                //mediaPlayer.seekTo(0);
                //mediaPlayer.stop();
                Log.i(TAG, "stopPlay: stop"+getCurrentProgress());
            }else if(mediaPlayer!=null && (!mediaPlayer.isPlaying())){
                mediaPlayer.start();
                //isPlaying=true;
                Log.i(TAG, "stopPlay: start"+getCurrentProgress());
            }
        }

    }
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }

    //获取当前进度
    public int getCurrentProgress(){
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
          //  Log.i(TAG, "getCurrentProgress: "+mediaPlayer.getCurrentPosition());
            return  mediaPlayer.getCurrentPosition();
        }else if(mediaPlayer!=null && (!mediaPlayer.isPlaying())){
          //  Log.i(TAG, "getCurrentProgress: "+mediaPlayer.getCurrentPosition());
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    //判断是否为同一首歌
    private boolean isSameSong(String PlayPath){
        if(currentPlayPath.equals(PlayPath))
            return true;
        else {
            return false;
        }
    }
}