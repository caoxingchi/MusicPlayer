package com.musicplayer.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jpeng.jpspringmenu.SpringMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.musicplayer.R;
import com.musicplayer.adapter.SlideMenuAdapter;
import com.musicplayer.pojo.SlideMenu;
import com.musicplayer.utils.ShowAlertDialog;
import com.musicplayer.utils.ShowToast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 14:38
 * @version: 1.0
 */
public class LeftMenuFragment extends BaseFragment implements View.OnClickListener {

    private List<SlideMenu> slideMenuList;
    private SlideMenuAdapter slideMenuAdapter;

    //通过注解将view获得
    @ViewInject(R.id.slide_setting_ll)
    private LinearLayout slideSettingLl;
    @ViewInject(R.id.slide_exit_ll)
    private LinearLayout slideExitLl;
    @ViewInject(R.id.slide_recycler_view)
    private RecyclerView slideMenuRecycler;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_slide_menu,null);
        ViewUtils.inject(this,view);

        slideSettingLl = (LinearLayout) view.findViewById(R.id.slide_setting_ll);
        slideExitLl = (LinearLayout) view.findViewById(R.id.slide_exit_ll);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //初始化侧边列表数据
        slideMenuList=new ArrayList<>();
        addAllMenu();
        slideMenuAdapter=new SlideMenuAdapter(mActivity,slideMenuList);
        LinearLayoutManager llm=new LinearLayoutManager(mActivity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        slideMenuRecycler.setLayoutManager(llm);
        slideMenuRecycler.setAdapter(slideMenuAdapter);
        slideMenuRecycler.addItemDecoration(
                new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        //侧边底部点击监听事件
        slideSettingLl.setOnClickListener(this);
        slideExitLl.setOnClickListener(this);
    }

    /**
     *@author -->> itcolors <<----
     *@time  14:27
     *@description 添加所有的menu
     */
    private void addAllMenu(){
        slideMenuList.add(new SlideMenu(R.mipmap.icon_theme,"主题"));
        slideMenuList.add(new SlideMenu(R.mipmap.icon_clock,"定时停止播放"));
        slideMenuList.add(new SlideMenu(R.mipmap.icon_sun,"夜间模式"));
        slideMenuList.add(new SlideMenu(R.mipmap.icon_searching_music,"扫描本地音乐"));
        slideMenuList.add(new SlideMenu(R.mipmap.icon_about_us,"关于我们"));
    }

    /**
     *@author -->> itcolors <<----
     *@time  15:26
     *@description 点击事件的监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.slide_exit_ll:
            {
                final SweetAlertDialog exitAlertDialog=new SweetAlertDialog(mActivity,SweetAlertDialog.WARNING_TYPE);
                exitAlertDialog.setTitle("提示");
                exitAlertDialog.setContentText("确定退出?");
                exitAlertDialog.setCancelButton("取消", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        exitAlertDialog.cancel();
                    }
                });
                exitAlertDialog.setConfirmButton("确定", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mActivity.finish();
                    }
                });
                exitAlertDialog.show();
                break;
            }
            case R.id.slide_setting_ll:{
                ShowToast.showShortToast(mActivity,"设置操作");
            }
        }
    }

}
