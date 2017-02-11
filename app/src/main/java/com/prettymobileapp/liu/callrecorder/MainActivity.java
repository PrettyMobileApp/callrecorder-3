package com.prettymobileapp.liu.callrecorder;
// call receiver will record call, activity b is the main screen, activity c is the load page, activity d is for SMS stats

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "checkError";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.prettymobileapp.liu.callrecorder.R.layout.activity_main);

        final ImageView imageView=(ImageView) findViewById(R.id.imageView);

                    AnimationDrawable animation = (AnimationDrawable) imageView.getDrawable();
                    animation.stop();
                    animation.selectDrawable(0);
                    animation.start();

        int secondsDelayed=2*1000;
        new Handler().postDelayed(new Runnable(){public void run(){startActivity(new Intent(MainActivity.this,HomePageActivity.class));finish();}},secondsDelayed);
    }
}
