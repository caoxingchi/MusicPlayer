package com.musicplayer.view;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.bar.TitleBar;
import com.jpeng.jpspringmenu.SpringMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.musicplayer.AudioService;
import com.musicplayer.MainActivity;
import com.musicplayer.R;
import com.musicplayer.adapter.MusicAdapter;
import com.musicplayer.listener.MyTitleBarListener;
import com.musicplayer.listener.PlayingListener;
import com.musicplayer.pojo.Song;
import com.musicplayer.utils.MusicDB;
import com.musicplayer.utils.ShowAlertDialog;
import com.musicplayer.utils.ShowBottomSheet;
import com.musicplayer.utils.ShowToast;
import com.musicplayer.utils.SlideNavigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 14:39
 * @version: 1.0
 */
public class ContentFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;

    //侧边导航栏
    private  SpringMenu slideMenu;
    //底部
    @ViewInject(R.id.play_bottom_ll)
    private LinearLayout playBottomLl;
    @ViewInject(R.id.play_btn)
    private ImageView playBtn;
    @ViewInject(R.id.menu_bottom_ll)
    private LinearLayout menuBottomLl;
    @ViewInject(R.id.bottom_play_container)
    private LinearLayout bottomPlayContainer;
    //底部歌名
    @ViewInject(R.id.song_name_bottom)
    private TextView songNameBottom;
    //底部歌手名
    @ViewInject(R.id.singer_bottom)
    private TextView singerBottom;

    @ViewInject(R.id.music_recyclerView)
    private RecyclerView musicRecyclerView;
    private static final String TAG = "ContentFragment";
    //顶部的播放列表
    private List<Song> playSongList;
    //音乐列表
    private List<Song> mySongList;
    //当前的播放位置 在列表中
    public int mCurrentPosition;



   /* private MediaPlayer mediaPlayer;//音频播放器
    private String musicData = null;
    // 记录当前播放歌曲的位置

    private static final int INTERNAL_TIME = 1000;// 音乐进度间隔时间*/

    private static final int REQUEST_CODE_PERMISSION = 1;

    private MusicDB musicDB;

    //当前播放的音乐
    private Song currentPlayingSong;

    private AudioService.MyBinder myBinder;
    //我的播放服务连接
    private MyConn myConn;


    public AudioService.MyBinder getMyBinder() {
        return myBinder;
    }
    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

    public void setmCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    //设置当前的正在播放音乐
    public Song getCurrentPlayingSong() {
        return currentPlayingSong;
    }

    public void setCurrentPlayingSong(Song currentPlayingSong) {
        this.currentPlayingSong = currentPlayingSong;
    }

    //便于获取底部歌名
    public TextView getSingerBottom() {
        return singerBottom;
    }

    //便于获取底部歌手
    public TextView getSongNameBottom() {
        return songNameBottom;
    }

    public MusicDB getMusicDB() {
        return musicDB;
    }

    public void setMusicDB(MusicDB musicDB) {
        this.musicDB = musicDB;
    }

    public SpringMenu getSlideMenu() {
        return slideMenu;
    }

    public void setSlideMenu(SpringMenu slideMenu) {
        this.slideMenu = slideMenu;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        ViewUtils.inject(this,view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        requestPermissionsIfAboveM();
        slideMenu = new SpringMenu(mActivity, R.layout.fragment_slide_menu);
        new SlideNavigation(mActivity, slideMenu).initSlideMenu();
        titleBar.setTitle("我的音乐");
        titleBar.setOnTitleBarListener(new MyTitleBarListener(mActivity, slideMenu));
        //底部播放器的监听事件
        playBottomLl.setOnClickListener(this);
        menuBottomLl.setOnClickListener(this);
        playSongList = new ArrayList<>();
        mySongList=new ArrayList<>();
        musicDB=new MusicDB(mActivity,((MainActivity)mActivity).getDbUtils());
        initMySongList();
        showPlayListSongs();

        if(mySongList==null){
            ShowAlertDialog.showWarningDialog(mActivity,"当前没有音乐哦!去扫描一下吧");
        }else{
            bottomPlayContainer.setOnClickListener(new PlayingListener(mActivity, playSongList));

            MusicAdapter musicAdapter=new MusicAdapter(mActivity,mySongList);
            LinearLayoutManager llm=new LinearLayoutManager(mActivity);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            musicRecyclerView.setLayoutManager(llm);
            musicRecyclerView.setAdapter(musicAdapter);
            musicRecyclerView.addItemDecoration(
                    new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

            setCurrentPlayingSong(playSongList.get(0));
            currentPlayingSong.setCurrentPosition(mCurrentPosition);
            songNameBottom.setText(currentPlayingSong.getSinger());
            singerBottom.setText(currentPlayingSong.getSinger());
            if(myBinder!=null){
                currentPlayingSong.setCurrentPlayProgress(myBinder.getCurrentPlayProgress());
            }


        }

        //MainActivity mainUI= (MainActivity) mActivity;

    }

    /**
     * @author -->> itcolors <<----
     * @time 15:23
     * @description 底部导航栏的点击按钮事件监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_bottom_ll: {
                if(playSongList!=null){
                    new ShowBottomSheet(mActivity).
                            showMusicListBottomSheet(false, "播放列表",
                                    playSongList, true, true);
                }else{
                    ShowAlertDialog.showWarningDialog(mActivity,"当前没有音乐哦！");
                    /*Intent intent=new Intent(mActivity, ScanMusicActivity.class);
                    startActivity(intent);*/
                }
                break;
            }
            case R.id.play_bottom_ll: {
                if(playSongList!=null){
                    if (isPlaying()) {
                        playBtn.setImageResource(R.mipmap.pause_music);
                        //假如当前的播放音乐为空时就从第一首开始播放
                        //currentPlayingSong.setPlaying(false);
                        if(myBinder!=null){
                            myBinder.stopPlay();
                        }
                        //ShowToast.showShortToast(mActivity,"停止播放");
                        Log.i(TAG, "onClick: 此时设置为R.mipmap.pause_music 停止播放");
                        break;
                    } else {
                        //currentPlayingSong.setPlaying(true);
                        //ShowToast.showShortToast(mActivity,"开始播放");
                        setCurrentIsPlaying();
                        break;
                    }
                }else{
                    ShowToast.showShortToast(mActivity,"当前没有音乐哦");
                }

            }

        }
    }

    public void setCurrentIsPlaying(){
        playBtn.setImageResource(R.mipmap.playing_music);
        if(currentPlayingSong==null){
            currentPlayingSong=playSongList.get(0);
        }
        myBinder.startPlay(currentPlayingSong.getPath());
        Log.i(TAG, "onClick: 此时设置为R.mipmap.playing_music 正在播放");
    }


    /**
     *@author -->> itcolors <<----
     *@time  22:10
     *@description 初始化底部播放列表
     */
    public void showPlayListSongs() {
        playSongList=mySongList;

    }

    /**
     * @author -->> itcolors <<----
     * @time 23:19
     * @description 判断是否正在播放 需修复--->已修复
     * R.mipmap.playing_music
     */
    public boolean isPlaying() {
        Drawable.ConstantState drawableCs = this.getResources().getDrawable(R.mipmap.playing_music).getConstantState();
        if(myBinder!=null){
            return myBinder.isPlaying();
        }else if(playBtn.getDrawable().getConstantState().equals(drawableCs)){
            return true;
        }
        return false;
    }

    /**
     *@author -->> itcolors <<----
     *@time  22:11
     *@description 初始化音乐列表
     */
    public void initMySongList(){
        mySongList=musicDB.findAllMusic();
    }


    private Map<String, String> permissionHintMap = new HashMap<>();
    private void requestPermissionsIfAboveM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Map<String, String> requiredPermissions = new HashMap<>();
            requiredPermissions.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储");
            for (String permission : requiredPermissions.keySet()) {
                if (mActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionHintMap.put(permission, requiredPermissions.get(permission));
                }
            }
            if (!permissionHintMap.isEmpty()) {
                requestPermissions(permissionHintMap.keySet().toArray(new String[0]), REQUEST_CODE_PERMISSION);
            }
        }

        //创建连接处
        myConn = new MyConn();
        Intent intent = new Intent(mActivity, AudioService.class);
        mActivity.bindService(intent, myConn, BIND_AUTO_CREATE);

    }

    /**
     *@author -->> itcolors <<----
     *@time  19:18
     *@description 创建播放连接
     */
    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder=(AudioService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBinder=null;
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        //解除绑定
        mActivity.unbindService(myConn);
        Log.i(TAG, "onDestroy: 解除绑定了");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            mySongList=musicDB.findAllMusic();
        }else if(requestCode==2){
            if(isPlaying()){
                playBtn.setImageResource(R.mipmap.playing_music);
            }else {
                playBtn.setImageResource(R.mipmap.pause_music);
            }
        }
    }

}
