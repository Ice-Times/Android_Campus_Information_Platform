package com.example.gs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar()!=null){

            getSupportActionBar().hide();

            getWindow().setFlags(

                    WindowManager.LayoutParams.FLAG_FULLSCREEN,

                    WindowManager.LayoutParams.FLAG_FULLSCREEN

            );

        }



        final Intent intent=new Intent(this,login_in.class);
        Timer timer=new Timer();
        int DELAY=1*1000;
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                startActivity(intent);
            }
        };
        timer.schedule(task,DELAY);//此处的Delay可以是3*1000，代表三秒

    }
}
