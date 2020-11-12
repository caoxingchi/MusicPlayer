package com.musicplayer.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/13 17:33
 * @version: 1.0
 */
public class ListScanMusicAdapter extends RecyclerSwipeAdapter<ListScanMusicAdapter.ScanItemViewHolder> {

    @Override
    public ScanItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ScanItemViewHolder scanItemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    class ScanItemViewHolder extends RecyclerView.ViewHolder{

        public ScanItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
