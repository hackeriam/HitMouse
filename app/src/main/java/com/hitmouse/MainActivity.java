package com.hitmouse;

import android.app.Activity;//全屏
import android.os.Bundle;
import android.content.Intent;//跳转
import android.os.Handler;//定时

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //new -> public 定时

                //跳转
                Intent intent = new Intent(MainActivity.this, ActivityHome.class);
                // MainActivity.this(MainActivity:当前文件名称)
                //ActivityHome.class(ActivityHome:目标.java文件名称)
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
