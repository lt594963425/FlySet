package com.mvp.lt.flyset.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.ui.adapter.FixPagerAdapter;
import com.mvp.lt.flyset.ui.fragment.AirplaneFragment;
import com.mvp.lt.flyset.ui.fragment.CameraSetFragment;
import com.mvp.lt.flyset.ui.fragment.GeneralSetFragment;
import com.mvp.lt.flyset.ui.fragment.QuickKeyFragment;
import com.mvp.lt.flyset.ui.fragment.SystemSetFragment;
import com.mvp.lt.flyset.ui.fragment.VoiceHintFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 竖直菜单导航
 *
 * @author ${LiuTao}
 * @date 2017/12/8/008
 */

public class Setting2Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.v_set_title)
    TextView mVSetTitle;
    @BindView(R.id.v_set_back_iv)
    ImageView mVSetBackIv;
    @BindView(R.id.server_set_rb)
    RadioButton mServerSetRb;
    @BindView(R.id.general_set_rb)
    RadioButton mGeneralSetRb;
    @BindView(R.id.camera_set_rb)
    RadioButton mCameraSetRb;
    @BindView(R.id.airplane_set_rb)
    RadioButton mAirplaneSetRb;
    @BindView(R.id.voice_set_rb)
    RadioButton mVoiceSetRb;
    @BindView(R.id.quick_set_rb)
    RadioButton mQuickSetRb;
    @BindView(R.id.vertical_viewpager)
    ViewPager mVerticalViewpager;
    @BindView(R.id.v_menu_rg)
    RadioGroup mVMenuRg;

    private List<Pair<String, Fragment>> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_menu);
        ButterKnife.bind(this);
        initFragment();
        initViewPager();
        initView();
    }

    private void initView() {
        mVMenuRg.setOnCheckedChangeListener(this);
        try {
            String versionCode = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
            mVSetTitle.setText("设置(" + versionCode + ")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mVSetBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initFragment() {
        mItems = new ArrayList<>();
        mItems.add(new Pair<String, Fragment>("系统设置", new SystemSetFragment()));
        mItems.add(new Pair<String, Fragment>("通用设置", new GeneralSetFragment()));
        mItems.add(new Pair<String, Fragment>("相机设置", new CameraSetFragment()));
        mItems.add(new Pair<String, Fragment>("飞机", new AirplaneFragment()));
        mItems.add(new Pair<String, Fragment>("语音提示", new VoiceHintFragment()));
        mItems.add(new Pair<String, Fragment>("快捷按键", new QuickKeyFragment()));

    }

    private void initViewPager() {
        mVerticalViewpager.setAdapter(new FixPagerAdapter(getSupportFragmentManager(), mItems));
        mVerticalViewpager.setOffscreenPageLimit(3);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.server_set_rb:
                mVerticalViewpager.setCurrentItem(0, false);
                break;
            case R.id.general_set_rb:
                mVerticalViewpager.setCurrentItem(1, false);
                break;
            case R.id.camera_set_rb:
                mVerticalViewpager.setCurrentItem(2, false);
                break;
            case R.id.airplane_set_rb:
                mVerticalViewpager.setCurrentItem(3, false);
                break;
            case R.id.voice_set_rb:
                mVerticalViewpager.setCurrentItem(4, false);
                break;
            case R.id.quick_set_rb:
                mVerticalViewpager.setCurrentItem(5, false);
                break;
            default:
                break;
        }
    }
}
