package com.mvp.lt.flyset;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mvp.lt.flyset.simple.SimpleActivity;
import com.mvp.lt.flyset.ui.activity.Setting2Activity;
import com.mvp.lt.flyset.ui.activity.SettingActivity;

import butterknife.ButterKnife;

/**
 * @author LiuTao
 */
public class MainActivity extends AppCompatActivity {

    private final int ACTION_CAMERA_REQUEST_CODE = 100;
    private final int ACTION_ALBUM_REQUEST_CODE = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void clickHorizontal(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }


    public void clickVertical(View view) {
        startActivity(new Intent(this, Setting2Activity.class));
    }


    public void clickWeiChat(View view) {
        startActivity(new Intent(this, SimpleActivity.class));
    }
}
