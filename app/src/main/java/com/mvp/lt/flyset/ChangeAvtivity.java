package com.mvp.lt.flyset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/3/24/024
 */

public class ChangeAvtivity  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        initVideoAanMap();
    }

    public FrameLayout mFrameLayoutBigView;
    public FrameLayout mFrameLayoutLittleView;
    public View mVideoViewFlater;
    public View mMapViewFlater;
    //地图和视频切换的标志
    public boolean isVideoAndMapChangeBig = true;
    private void initVideoAanMap() {
        //初始化视频和地图UI
        mFrameLayoutBigView = (FrameLayout) findViewById(R.id.frame_layout_bigView);
        mFrameLayoutLittleView = (FrameLayout) findViewById(R.id.frame_layout_littleView);

        //视频
        mVideoViewFlater = LayoutInflater.from(this).inflate(R.layout.video_view, null,false);
        //地图

        mMapViewFlater = LayoutInflater.from(this).inflate(R.layout.map_view, null,false);



        mFrameLayoutBigView.addView(mMapViewFlater);
        mFrameLayoutLittleView.addView(mVideoViewFlater);

        mFrameLayoutLittleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVideoAndMapChangeBig) {
                    setBigVideo();
                } else {
                    setBigMap();
                }
            }
        });
    }

    public void setBigMap() {
        mFrameLayoutBigView.removeAllViews();
        mFrameLayoutLittleView.removeAllViews();
        isVideoAndMapChangeBig = true;
        mFrameLayoutBigView.addView(mMapViewFlater);
        mFrameLayoutLittleView.addView(mVideoViewFlater);

    }

    public void setBigVideo() {
        mFrameLayoutBigView.removeAllViews();
        mFrameLayoutLittleView.removeAllViews();
        isVideoAndMapChangeBig = false;
        mFrameLayoutBigView.addView(mVideoViewFlater);
        mFrameLayoutLittleView.addView(mMapViewFlater);

    }
}
