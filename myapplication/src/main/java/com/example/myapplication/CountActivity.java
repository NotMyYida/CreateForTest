package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.myapplication.music.MusicAdapter;
import com.example.myapplication.music.MusicUtil;
import com.example.myapplication.music.Song;

import java.util.List;

/**
 * Created by brant on 18-10-27.
 */

public class CountActivity extends Activity {

    private ThreadLocal<Boolean> mThreadLocalBoolean = new ThreadLocal<Boolean>();
    public final static String TAG = CountActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity);

//        ListView lvSongs = findViewById(R.id.lv_songs);

        Intent intent = new Intent(this,CountService.class);
        intent.putExtra(CountService.EXTRA_NUMBER,10);
        startService(intent);

        Intent intent2 = new Intent(this,CountService.class);
        intent2.putExtra(CountService.EXTRA_NUMBER,5);
        startService(intent2);

        StringBuilder sb = new StringBuilder();
        sb.append("123456");
        sb.append("ABCDEF");
        Log.e("CountActivity","sb.toString() -> "+sb.toString());
        sb.delete(1,4);
        Log.e("CountActivity","after delete sb.toString() -> "+sb.toString());

//        Intent intent3 = new Intent(this,TestService.class);
//        startService(intent3);

        List<Song> songList = MusicUtil.getSongList(this);
        MusicAdapter adapter = new MusicAdapter(this,songList);
//        lvSongs.setAdapter(adapter);

//        mThreadLocalBoolean.set(true);
//        Log.d(TAG,"Main Thread : "+mThreadLocalBoolean.get());
//
//        new Thread("Thread#1"){
//            @Override
//            public void run() {
//                super.run();
//                Log.d(TAG,"Thread # 1 : "+mThreadLocalBoolean.get());
//                mThreadLocalBoolean.set(false);
//                Log.d(TAG,"Thread # 1 : "+mThreadLocalBoolean.get());
//            }
//        }.start();
//
//        new Thread("Thread#2"){
//            @Override
//            public void run() {
//                super.run();
//                Log.d(TAG,"Thread # 2 : "+mThreadLocalBoolean.get());
//            }
//        }.start();
//
//        Log.d(TAG,"Main Thread : "+mThreadLocalBoolean.get());
//
//        B b = new B();

    }

    class A {
        C c = new C("A");
        int i = 10;
        A(){
            System.out.println("System.out.println A.create");
            run();
        }

        void run() {
            System.out.println("System.out.println A.run:"+ i++);
        }
    }

    class B extends A{
        int i = 20;
        C c = new C("B");

        B(){
            System.out.println("System.out.println B.create");
            run();
        }

        void run() {
            System.out.println("System.out.println B.run:"+ ++i);
        }
    }

    class C{
        C(String s){
            System.out.println("System.out.println "+s+".C.create");
        }
    }
}
