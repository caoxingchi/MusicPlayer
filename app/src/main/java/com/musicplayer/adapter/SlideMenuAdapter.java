package com.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.musicplayer.R;
import com.musicplayer.listener.ClickSlideMenuItemListener;
import com.musicplayer.pojo.SlideMenu;
import com.musicplayer.utils.ShowToast;

import java.util.List;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 10:09
 * @version: 1.0
 */
public class SlideMenuAdapter extends RecyclerView.Adapter<SlideMenuAdapter.ItemViewHolder> {
     private LayoutInflater ll;
     private Context context;
     private List<SlideMenu> slideMenuList;

    public SlideMenuAdapter(Context context, List<SlideMenu> slideMenuList) {
        this.context = context;
        this.slideMenuList = slideMenuList;
        this.ll=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=ll.inflate(R.layout.one_item_menu,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, final int position) {
        ((ItemViewHolder)(viewHolder)).updateItem();

        /**
         *@author -->> itcolors <<----
         *@time  11:12
         *@description 点击侧边导航栏的监听事件
         */
        viewHolder.slideMenuLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ClickSlideMenuItemListener(position,context).onClickListener(v);
                //ShowToast.showShortToast(context,"点击了"+slideMenuList.get(position).getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return slideMenuList.size();
    }



    class ItemViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout slideMenuLl;
        private ImageView slideItemIco;
        private TextView showTimeSleep;
        private TextView titleMenu;



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            slideMenuLl = (LinearLayout) itemView.findViewById(R.id.slide_menu_ll);
            slideItemIco = (ImageView) itemView.findViewById(R.id.slide_item_ico);
            showTimeSleep = (TextView) itemView.findViewById(R.id.show_time_sleep);
            titleMenu = (TextView) itemView.findViewById(R.id.title_menu);

        }
        public void updateItem(){
            int position=this.getLayoutPosition();
            SlideMenu slideMenu=slideMenuList.get(position);

            slideItemIco.setImageResource(slideMenu.getResource());
            titleMenu.setText(slideMenu.getTitle());


        }
    }
}
