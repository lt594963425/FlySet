package com.mvp.lt.flyset;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mvp.lt.flyset.blue.DeviceControlActivity;
import com.mvp.lt.flyset.seekbar.HorizontalSeekActivity;
import com.mvp.lt.flyset.fragment.SimpleActivity;
import com.mvp.lt.flyset.task.TaskActivity;
import com.mvp.lt.flyset.ui.activity.Setting2Activity;
import com.mvp.lt.flyset.ui.activity.SettingActivity;

import butterknife.ButterKnife;

/**
 * @author LiuTao
 */
public class MainActivity extends AppCompatActivity {
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

    public void clickTab(View view) {
        startActivity(new Intent(this, TaskActivity.class));
    }

    public void clicklaser(View view) {
        startActivity(new Intent(this, DeviceControlActivity.class));
    }

    public void clickSeek(View view) {
        startActivity(new Intent(this, HorizontalSeekActivity.class));
    }

    public void clickDJI(View view) {

    }

    public void clickChange(View view) {
        startActivity(new Intent(this, ChangeAvtivity.class));
    }
}
