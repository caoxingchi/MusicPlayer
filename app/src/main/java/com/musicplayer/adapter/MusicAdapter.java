package com.musicplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.lidroid.xutils.DbUtils;
import com.musicplayer.MainActivity;
import com.musicplayer.R;
import com.musicplayer.ScanMusicActivity;
import com.musicplayer.pojo.Song;
import com.musicplayer.utils.MusicDB;
import com.musicplayer.utils.ScanMusicUtil;
import com.musicplayer.utils.ShowAlertDialog;
import com.musicplayer.utils.ShowBottomSheet;
import com.musicplayer.utils.ShowToast;
import com.musicplayer.view.ContentFragment;


import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MusicAdapter extends RecyclerSwipeAdapter<MusicAdapter.ItemViewHolder> {

    private LayoutInflater ll;
    private Activity activity;
    private List<Song> songList;
    private MusicDB musicDB;
    private MainActivity mActivity;
    private Context context;
    private ContentFragment contentFragment;
    private static final String TAG = "MusicAdapter";
    private DbUtils dbUtils;

    public MusicAdapter(Activity activity, List<Song> songList) {
        this.activity = activity;
        this.songList = songList;
        this.context=(Context) activity;
        this.ll =LayoutInflater.from(activity);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=ll.inflate(R.layout.one_item_song,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, int i) {
        //初始化音乐列表
        ((ItemViewHolder) (viewHolder)).updateSongs();
        if(isScanActivity()){
            dbUtils=((ScanMusicActivity)activity).getDbUtils();
        }else {
            dbUtils=((MainActivity)activity).getDbUtils();
        }
        musicDB=new MusicDB(context,dbUtils);
        //此地方只为播放列表服务，不为其他列表服务
        if (!isScanActivity()) {
            //获取当前的界面
            mActivity = (MainActivity) activity;
            contentFragment = mActivity.getContentFragment();
            //获取数据操作
            //musicDB = contentFragment.getMusicDB();
            final int position = i;
        /**
         *@author -->> itcolors <<----
         *@time 13:18
         *@description 播放列表点击监听
         */

        viewHolder.playLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放事件开始


               // 首先要解决的问题就是要将其他的显示出来的音乐图标关闭
                //将播放图标展示出来
                //有bug，未修复，暂时不显示播放图标
                //viewHolder.playingImg.setVisibility(View.VISIBLE);
                String song_name = songList.get(position).getSong_name();
                String singer = songList.get(position).getSinger();
                Log.i(TAG, "onClick: " + song_name + "    -----     " + singer);
                contentFragment.getSongNameBottom().setText(song_name);
                contentFragment.getSingerBottom().setText(singer);
                //
                contentFragment.setmCurrentPosition(position);
                contentFragment.setCurrentPlayingSong(songList.get(position));
                contentFragment.setCurrentIsPlaying();
                //viewHolder.shareLl.setVisibility(View.VISIBLE);
                //播放开始

                //
                /*SweetAlertDialog warning =new SweetAlertDialog(context);
                warning.setContentText("提示！");
                warning.setTitle("warning");
                warning.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ShowToast.showShortToast(context,"success!!!");
                        sweetAlertDialog.cancel();
                        viewHolder.playingImg.setVisibility(View.VISIBLE);
                    }
                });
                warning.setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ShowToast.showLongToast(context,"failed!!!");
                        sweetAlertDialog.cancel();
                        viewHolder.playingImg.setVisibility(View.GONE);
                    }
                });
                warning.show();*/
            }
        });


        /**
         *@author -->> itcolors <<----
         *@time 13:19
         *@description 收藏监听
         */
        viewHolder.collectSongLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlertDialog.showOKDialog(context, "收藏成功!");
                viewHolder.swipeLayout.close(true);
            }
        });
        /**
         *@author -->> itcolors <<----
         *@time 13:21
         *@description 删除监听
         */
        viewHolder.deleteSongLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog deleteAlertDialog = new SweetAlertDialog(context,
                        SweetAlertDialog.WARNING_TYPE);
                deleteAlertDialog.setTitle("删 除");
                deleteAlertDialog.setContentText("确 定 删 除？");
                deleteAlertDialog.setCancelButton("取消", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        viewHolder.swipeLayout.close(true);
                        deleteAlertDialog.cancel();
                    }
                });
                deleteAlertDialog.setConfirmButton("删除", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        songList.remove(position);
                        deleteAlertDialog.cancel();
                        musicDB.deleteByKey(songList.get(position).getId());
                        notifyDataSetChanged();
                        ShowAlertDialog.showOKDialog(context, "删除成功!");

                    }
                });
                deleteAlertDialog.show();
            }
        });

        viewHolder.shareLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowBottomSheet(context).showShareBottomSheet();
            }
        });
    }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private SwipeLayout swipeLayout;
        private LinearLayout bottomWrapper;
        private ImageView collectSong;
        private ImageView deleteSong;
        private ImageView playingImg;
        private TextView songName;
        private TextView singerName;
        private LinearLayout playLl;
        private LinearLayout collectSongLl;
        private LinearLayout deleteSongLl;
        private LinearLayout shareLl;
        private TextView songDuration;



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
            bottomWrapper = (LinearLayout) itemView.findViewById(R.id.bottom_wrapper);
            //播放要点击的部分
            playLl = (LinearLayout) itemView.findViewById(R.id.play_ll);
            collectSong = (ImageView) itemView.findViewById(R.id.collect_song);
            deleteSong = (ImageView) itemView.findViewById(R.id.delete_song);
            playingImg = (ImageView) itemView.findViewById(R.id.playing_img);
            songName = (TextView) itemView.findViewById(R.id.song_name);
            singerName = (TextView) itemView.findViewById(R.id.singer_tx);
            collectSongLl = (LinearLayout) itemView.findViewById(R.id.collect_song_ll);
            deleteSongLl = (LinearLayout) itemView.findViewById(R.id.delete_song_ll);
            shareLl = (LinearLayout) itemView.findViewById(R.id.share_ll);
            songDuration = (TextView) itemView.findViewById(R.id.song_duration);


        }

        /**
         *@author -->> itcolors <<----
         *@time  10:30
         *@description 更新音乐列表
         */
        public void updateSongs(){
            int position=this.getLayoutPosition();
            Song song=songList.get(position);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            songName.setText(song.getSong_name());
            singerName.setText("-----"+song.getSinger());
            songDuration.setText(ScanMusicUtil.formatTime(song.getDuration()));
            if(isScanActivity()){
                shareLl.setVisibility(View.GONE);
                //swipeLayout.setVisibility(View.GONE);
            }else{
                shareLl.setVisibility(View.VISIBLE);
            }
        }
    }
    /**
     *@author -->> itcolors <<----
     *@time  17:42
     *@description 判断是否是scanMusicActivity 以免在初始化数据的时候报错
     */
    private boolean isScanActivity(){
        return activity.getClass().equals(ScanMusicActivity.class);
    }

}
