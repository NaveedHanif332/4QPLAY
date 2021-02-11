package org.fourqwebs.a4qwebapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import org.fourqwebs.androidtv.R;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {
   private Intent it;
    VideoView videoView;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        videoView=findViewById(R.id.introvideo);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.introvideo;
        videoView.setVideoURI(Uri.parse(path));
        videoView.setZOrderOnTop(false);
        videoView.start();

        videoView.setBackgroundColor(R.color.colorAccent); // Your color.
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.setBackgroundColor(Color.TRANSPARENT);
            }
        });
     MyDataBase dataBase=new MyDataBase(SplashScreen.this);
        dataBase.getWritableDatabase();
        dataBase.getReadableDatabase();

        Cursor data=dataBase.getallData();
        data.moveToFirst();
        if(data.getCount()>0)
        {
            it = new Intent(SplashScreen.this,MainActivity.class);
            String url=data.getString(1);
            String port=data.getString(2);
            it.putExtra("url",url);
            it.putExtra("port",port);
        }
        else {
            // no data in database
            it = new Intent(SplashScreen.this,MyConnect.class);
        }

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(it);
                    SplashScreen.this.finish();
                }
            }
        });
        thread.start();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // ignore orientation/keyboard change
        super.onConfigurationChanged(newConfig);
    }
}