package com.musicplayer;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.runner.AndroidJUnit4;

import com.musicplayer.pojo.Song;
import com.musicplayer.utils.MusicDB;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";
    private MusicDB db;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.musicplayer", appContext.getPackageName());
    }
    
    @Test
    public void createDb(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Song song=new Song("你好","我爱你");
        MusicDB db=new MusicDB(appContext);
        db.saveMusic(song);
        Log.i(TAG, "createDb: 写入成功！！！");
        
        
    }
    
    public void getDB(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        List<Song> allMusic = db.findAllMusic();
        Log.i(TAG, "getDB: "+allMusic);
        
    }
}
