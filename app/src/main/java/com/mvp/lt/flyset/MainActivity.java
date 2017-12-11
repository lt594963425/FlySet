package com.mvp.lt.flyset;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mvp.lt.flyset.ui.activity.Setting2Activity;
import com.mvp.lt.flyset.ui.activity.SettingActivity;

/**
 * @author LiuTao
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickHorizontal(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }


    public void clickVertical(View view) {
        startActivity(new Intent(this, Setting2Activity.class));
    }
}
