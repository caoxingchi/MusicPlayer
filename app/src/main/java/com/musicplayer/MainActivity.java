package com.musicplayer;

import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.jpeng.jpspringmenu.SpringMenu;
import com.lidroid.xutils.DbUtils;
import com.musicplayer.view.ContentFragment;
import com.musicplayer.view.LeftMenuFragment;

/**
 * @author xingchi -->> itcolors <<----
 * @version 1.0
 * @date 2020/6/10 19:43
 * @description 主界面，进行页面的交互
 */
public class MainActivity extends AppCompatActivity{

    private SpringMenu slideMenu;
    private static final String TAG_CONTENT="TAG_CONTENT";
    private static final String TAG_LEFT_MENU="TAG_LEFT_MENU";

    private static final String TAG = "MainActivity";
    private DbUtils dbUtils;

    public DbUtils getDbUtils() {
        return dbUtils;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //沉浸式TitleBar
        //QMUIStatusBarHelper.translucent(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
    }

    public void initData() {
        initFragment();
        dbUtils=DbUtils.create(getApplicationContext(),"music_db");
    }



    /**
     * init fragment
     */
    private void initFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        /**
         *参数一：当前布局id
         * 参数二：要替换的fragment
         * 创建  TAG方便以后找到Fragment
         */
        transaction.replace(R.id.fl_content,new ContentFragment(),TAG_CONTENT);//使用fragment 替换现有布局，参数
        transaction.replace(R.id.slide_menu_fr,new LeftMenuFragment(),TAG_LEFT_MENU);

        transaction.commit();//提交事务
    }


    /**
     * get leftMenuFragment object
     * @return
     */
    public LeftMenuFragment getLeftMenuFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment)
                fragmentManager.findFragmentByTag(TAG_LEFT_MENU);
        return fragment;
    }
    public ContentFragment getContentFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        ContentFragment fragment = (ContentFragment)
                fragmentManager.findFragmentByTag(TAG_CONTENT);
        return fragment;
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(slideMenu==null){
            Log.i(TAG, "dispatchTouchEvent: slideMenu为空");
            slideMenu=getContentFragment().getSlideMenu();
        }
        return slideMenu.dispatchTouchEvent(ev);
    }
}
