package com.mvp.lt.flyset.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.ui.adapter.FixPagerAdapter;
import com.mvp.lt.flyset.ui.fragment.AirplaneFragment;
import com.mvp.lt.flyset.ui.fragment.CameraSetFragment;
import com.mvp.lt.flyset.ui.fragment.GeneralSetFragment;
import com.mvp.lt.flyset.ui.fragment.QuickKeyFragment;
import com.mvp.lt.flyset.ui.fragment.VoiceHintFragment;
import com.mvp.lt.flyset.view.NoHorizontalScrollPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2017/12/8/008
 */

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.set_title)
    TextView mSetTitle;
    @BindView(R.id.set_tab)
    TabLayout mSetTab;
    @BindView(R.id.set_viewpager)
    NoHorizontalScrollPager mSetViewpager;
    @BindView(R.id.set_back_iv)
    ImageView mSetBackIv;
    private List<Pair<String, Fragment>> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initFragment();
        initViewPager();
        setupTabLayout();
        initView();
    }

    private void initView() {
        try {
            String versionCode = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
            mSetTitle.setText("设置(" + versionCode + ")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mSetBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initFragment() {
        mItems = new ArrayList<>();
        mItems.add(new Pair<String, Fragment>("通用设置", new GeneralSetFragment()));
        mItems.add(new Pair<String, Fragment>("相机设置", new CameraSetFragment()));
        mItems.add(new Pair<String, Fragment>("飞机", new AirplaneFragment()));
        mItems.add(new Pair<String, Fragment>("语音提示", new VoiceHintFragment()));
        mItems.add(new Pair<String, Fragment>("快捷按键", new QuickKeyFragment()));

    }

    private void initViewPager() {
        mSetViewpager.setAdapter(new FixPagerAdapter(getSupportFragmentManager(), mItems));
        //mSetViewpager.setOffscreenPageLimit(3);
    }

    private void setupTabLayout() {
        mSetTab.setTabMode(TabLayout.MODE_FIXED);
        mSetTab.setupWithViewPager(mSetViewpager);
    }
}
