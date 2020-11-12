package com.musicplayer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.musicplayer.pojo.Song;
import com.musicplayer.utils.ScanMusicUtil;
import com.musicplayer.utils.ShowBottomSheet;
import com.musicplayer.utils.ShowToast;

import java.util.List;

public class PlayMusicActivity extends AppCompatActivity implements Init, View.OnClickListener {

    //顶部返回按钮
    private ImageView playBack;
    //顶部歌名
    private TextView playSongName;
    //顶部歌手
    private TextView playSinger;
    //顶部分享操作
    private ImageView playingShareMusic;
    //中间的唱片
    private ImageView playSongCd;
    //播放进度条
    private SeekBar playSeekbar;
    //已播放的时间
    private TextView songPlayCur;
    //歌的总时间
    private TextView songLength;
    //上一首
    private ImageView playingForward;
    //播放暂停键
    private ImageView playingBtn;
    //下一首
    private ImageView playingNext;

    private List<Song> songList;

    //当前的播放进度
    private long currentPlayCur;

    private Song currentSong;

    //当前的位置
    private int mCurrentPosition;
    
    private MyConn myConn;

    private AudioService.MyBinder myBinder;

    private Thread myThread;



    private static final String TAG = "PlayMusicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initData();
    }

    @Override
    public void initView() {
        playBack = (ImageView) findViewById(R.id.play_back);
        playSongName = (TextView) findViewById(R.id.play_song_name);
        playSinger = (TextView) findViewById(R.id.play_singer);
        playingShareMusic = (ImageView) findViewById(R.id.playing_share_music);
        playSongCd = (ImageView) findViewById(R.id.play_song_cd);
        playSeekbar = (SeekBar) findViewById(R.id.play_seekbar);
        songPlayCur = (TextView) findViewById(R.id.song_play_cur);
        songLength = (TextView) findViewById(R.id.song_length);
        playingForward = (ImageView) findViewById(R.id.playing_forward);
        playingBtn = (ImageView) findViewById(R.id.playing_btn);
        playingNext = (ImageView) findViewById(R.id.playing_next);
    }

    //建立一个Handler对象，用于主线程和子线程的通信
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取子线程传递过来的消息，取出进度更新音乐
            //msg.what用于判断从那个线程传递过来的消息
            if(msg.what==100){
                int currentPosition=(Integer) msg.obj;
                int realProgress=(int)((float)(currentPosition*1.0/currentSong.getDuration()*1.0)*100000);
                playSeekbar.setProgress(realProgress);
                songPlayCur.setText(ScanMusicUtil.formatTime(realProgress));
            }

        }
    };

    //实时更新播放进度
    private void updateProgress(){
        myThread=new Thread(){
            @Override
            public void run() {
                super.run();
                //判断线程是否终止
                while(!interrupted()){
                    //调用服务的方法获取进度
                    int currentPosition=myBinder.getCurrentPlayProgress();
                    //封装消息
                    Message message=new Message();
                    message.obj=currentPosition;
                    message.what=100;
                    handler.sendMessage(message);
                }
            }
        };
        myThread.start();
    }

    @Override
    public void initData() {

        myConn=new MyConn();
        bindService(new Intent(PlayMusicActivity.this,AudioService.class)
                ,myConn,BIND_AUTO_CREATE);
        //返回键的监听
        playBack.setOnClickListener(this);
        //分享按钮的监听
        playingShareMusic.setOnClickListener(this);
        //唱片的点击事件的监听
        playSongCd.setOnClickListener(this);
        //上一首的点击监听
        playingForward.setOnClickListener(this);
        //播按钮放的点击
        playingBtn.setOnClickListener(this);
        //下一首的监听
        playingNext.setOnClickListener(this);

        //初始化最大
        //playSeekbar.setProgress(100);
        playSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //当前进度为：已播放的进度100*(当前获得的进度/总进度)
                    //playSeekbar.setProgress((int)currentPlayCur)
                playSeekbar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //获取传过来的list
        Intent intent=getIntent();
        songList=(List<Song>) intent.getSerializableExtra("songList");

        playingBtn.setImageResource(R.drawable.playing_pause);
        currentSong=(Song)intent.getSerializableExtra("currentPlayingSong");
        //初始化音乐界面
        if(currentSong!=null){
            playSongName.setText(currentSong.getSong_name());
            playSinger.setText(currentSong.getSinger());
            songLength.setText(ScanMusicUtil.formatTime(currentSong.getDuration()));
            currentPlayCur = currentSong.getCurrentPlayProgress();
            int realProgress=(int)((float)(currentPlayCur*1.0/currentSong.getDuration()*1.0)*100000);
            playSeekbar.setProgress(realProgress);
        }
        //myBinder=(AudioService.MyBinder) intent.getSerializableExtra("myBinder");
        if(myBinder!=null){
            if(myBinder.isPlaying()){
                playingBtn.setImageResource(R.drawable.playing_play);
            }else {
                playingBtn.setImageResource(R.drawable.playing_pause);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_back:{
                finish();
                break;
            }
            case R.id.playing_share_music:{
                new ShowBottomSheet(this).showShareBottomSheet();
                break;
            }
            case R.id.play_song_cd:{
                new ShowBottomSheet(this).showMusicListBottomSheet(false,"播放列表"
                ,songList,true,false);
                break;
            }
            case R.id.playing_forward:{
                forwardMusic();
                ShowToast.showShortToast(this,"上一首");
                break;
            }
            case R.id.playing_btn:{
                Log.i(TAG, "onClick: "+isPlaying());
                if (isPlaying()) {
                    myBinder.stopPlay();
                    playingBtn.setImageResource(R.drawable.playing_pause);
                    //ShowToast.showShortToast(this,"停止播放");
                    //Log.i(TAG, "onClick: 此时设置为R.mipmap.pause_music 停止播放");
                    break;
                } else {
                    if(myBinder!=null&&currentSong!=null){
                        myBinder.startPlay(currentSong.getPath());
                        updateProgress();
                        //ShowToast.showShortToast(this,"开始播放");
                        playingBtn.setImageResource(R.drawable.playing_play);
                        //Log.i(TAG, "onClick: 此时设置为R.mipmap.playing_music 正在播放");
                        break;
                    }
                }

            }
            case R.id.playing_next:{
                nextMusic();
                ShowToast.showShortToast(this,"下一首");
                break;
            }
        }
    }
    /**
     * @author -->> itcolors <<----
     * @time 23:19
     * @description 判断是否正在播放 需修复--->已修复
     */
  /*  public boolean isPlaying() {
        Drawable.ConstantState drawableCs = this.getResources().getDrawable(R.drawable.playing_play).getConstantState();
        if (playingBtn.getDrawable().getConstantState().equals(drawableCs)) {
            return true;
        }
        return false;
    }*/

    /**
     *@author -->> itcolors <<----
     *@time  19:18
     *@description 创建播放连接
     */
    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder=(AudioService.MyBinder)service;
            Log.i(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBinder=null;
        }


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        //解除绑定
        unbindService(myConn);
        Log.i(TAG, "onDestroy: 解除绑定了");

    }

    public boolean isPlaying(){
        Drawable.ConstantState drawableCs = this.getResources().getDrawable(R.drawable.playing_play).getConstantState();
        if(myBinder!=null){
            return myBinder.isPlaying();
        }else if(playingBtn.getDrawable().getConstantState().equals(drawableCs)){
                return true;
        }
        return false;
    }

    /**
     *@author -->> itcolors <<----
     *@time  1:12
     *@description 上一首
     */
    private void forwardMusic(){
        //总数量为
        int allMusic=songList.size();
        mCurrentPosition--;
        if(mCurrentPosition<0){
            mCurrentPosition=allMusic-1;
        }
        currentSong=songList.get(mCurrentPosition);
        myBinder.startPlay(currentSong.getPath());
    }

    /**
     *@author -->> itcolors <<----
     *@time  1:12
     *@description 下一首
     */
    private void nextMusic(){
        int allMusic=songList.size();
        mCurrentPosition++;
        if(mCurrentPosition>allMusic){
            mCurrentPosition=0;
        }

        currentSong=songList.get(mCurrentPosition);
        myBinder.startPlay(currentSong.getPath());
    }
}
