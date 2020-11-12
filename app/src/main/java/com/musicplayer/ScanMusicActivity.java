package com.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.hjq.bar.TitleBar;
import com.kongqw.radarscanviewlibrary.RadarScanView;
import com.lidroid.xutils.DbUtils;
import com.musicplayer.adapter.MusicAdapter;
import com.musicplayer.listener.MyTitleBarListener;
import com.musicplayer.pojo.Song;
import com.musicplayer.utils.MusicDB;
import com.musicplayer.utils.ScanMusicUtil;
import com.qmuiteam.qmui.alpha.QMUIAlphaButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *@author -->> itcolors <<----
 *@time  0:33
 *@description 扫描文件界面
 */
public class ScanMusicActivity extends AppCompatActivity implements Init, View.OnClickListener {

    private TitleBar titleBar;
    private RadarScanView scanFile;
    private MusicDB musicDB;
    private static final String TAG = "ScanMusicActivity";

    //扫描线程
    private Thread scanThread;
    /*定时器  用于定时检测扫描线程的状态*/
    private Timer scanTimer;

    /*检测扫描线程的任务*/
    private TimerTask scanTask;

    //扫描完后的音乐列表
    private List<Song> musicData;

    private RecyclerView scanRecyclerView;
    private LinearLayout scanLl;
    private QMUIAlphaButton startScanBtn;

    private MusicAdapter musicAdapter;

    private DbUtils dbUtils;

    public DbUtils getDbUtils() {
        return dbUtils;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_music);
        initView();
        initData();
    }

    @Override
    public void initView() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);


        //扫描界面 当扫描完毕后将不再显示，以便来显示recyclerview
        scanLl = (LinearLayout) findViewById(R.id.scan_ll);
        scanFile = (RadarScanView) findViewById(R.id.scan_file);

        startScanBtn = (QMUIAlphaButton) findViewById(R.id.start_scan_btn);
        //扫描完毕后将会显示出来
        scanRecyclerView = (RecyclerView) findViewById(R.id.scan_recycler_view);


    }

    @Override
    public void initData() {
        musicDB=new MusicDB(getApplicationContext(),dbUtils);
        titleBar.setLeftIcon(R.mipmap.bar_icon_back_white);
        titleBar.setTitle("扫描本地音乐");
        configRadarScanView();
        titleBar.setOnTitleBarListener(new MyTitleBarListener(this));
        musicDB=new MusicDB(getApplicationContext(),dbUtils);

        //扫描按钮的监听事件
        startScanBtn.setOnClickListener(this);
        //startScan();
        //Log.i(TAG, "initData: "+musicData.toString());

    }

    /**
     * 初始化雷达扫描器
     */
    private void configRadarScanView(){
        //一圈时间
        scanFile.setRadarScanTime(2000);
        //
        //雷达扫描颜色
        scanFile.setRadarScanColor(getResources().getColor(R.color.colorScan));
        //背景圈数量
        scanFile.setRadarBackgroundLinesNumber(4);
        scanFile.setRadarBackgroundLinesWidth(1.7f);
        scanFile.setRadarBackgroundLinesColor(Color.GRAY);
        //背景
        scanFile.setRadarBackgroundColor(getResources().getColor(R.color.colorScanBackground));
        //扫描透明度
        scanFile.setRadarScanAlpha(0xAA);
    }

    /**
     *@author -->> itcolors <<----
     *@time  16:23
     *@description 开始扫描本地音乐
     */
    public void startScan(){
        scanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                musicData = ScanMusicUtil.getMusicData(getApplicationContext(), musicDB);
            }
        });
        /*判断扫描是否完成 其实就是个定时任务 时间可以自己设置  每2s获取一下扫描线程的状态  如果线程状态为结束就说明扫描完成*/
        scanTimer = new Timer();
        scanTask = new TimerTask() {
            @Override
            public void run() {
                Log.i("线程状态",scanThread.getState().toString());

                if (scanThread.getState() == Thread.State.TERMINATED) {
                    /*说明扫描线程结束 扫描完成  更新ui*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("线程结束","扫描完成");
                            //scanTv.setText("扫描完成，共扫描出" + scanedFiles.size() + "个txt文件");
                            Log.i(TAG, "run: "+musicData.size()+"首歌");
                            Log.i(TAG, "run: "+musicData.toString());
                            List<Song> allMusic = musicDB.findAllMusic();
                            //Log.i(TAG, "run: "+allMusic);
                            scanFile.stopScan();
                            scanLl.setVisibility(View.GONE);
                            scanRecyclerView.setVisibility(View.VISIBLE);
                            initRecyclerView();
                            cancelTask();

                        }
                    });
                }
            }
        };
        scanTimer.schedule(scanTask, 0,1000);

        /*开始扫描*/
        scanThread.start();
    }
    private void cancelTask() {

        Log.i("cancelTask","结束任务");
        if (scanTask!=null){
            scanTask.cancel();
        }

        if (scanTimer!=null){
            scanTimer.purge();
            scanTimer.cancel();;
        }
    }
    /**
     *@author -->> itcolors <<----
     *@time  16:21
     *@description 初始化列表
     */
    public void initRecyclerView(){
        musicAdapter=new MusicAdapter(this,musicData);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        scanRecyclerView.setLayoutManager(llm);
        scanRecyclerView.setAdapter(musicAdapter);
        scanRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onClick(View v) {
        startScan();;
    }
}
