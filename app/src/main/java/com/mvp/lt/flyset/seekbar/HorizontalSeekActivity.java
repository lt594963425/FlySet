package com.mvp.lt.flyset.seekbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mvp.lt.flyset.BaseActivity;
import com.mvp.lt.flyset.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/1/27/027
 */

public class HorizontalSeekActivity extends BaseActivity {
    @BindView(R.id.image_reduce)
    ImageView mImageReduce;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    @BindView(R.id.image_add)
    ImageView mImageAdd;
    @BindView(R.id.textData)
    TextView mTextData;
    private int mMax;
    private int mProgress;
    private GestureDetector mGestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);
        ButterKnife.bind(this);
        mMax = mSeekbar.getMax();

        mProgress = mMax / 2;
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgress = progress;
                int number = progress - mMax / 2;
                mTextData.setText(number + "");


                Log.e("当前进度:", progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick({R.id.image_reduce, R.id.image_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_reduce:
                mProgress -= 1;
                if (mProgress < 0) {
                    mProgress = -45;
                }
                mSeekbar.setProgress(mProgress);
                break;
            case R.id.image_add:
                mProgress += 1;
                if (mProgress > 90) {
                    mProgress = 45;
                }
                mSeekbar.setProgress(mProgress);
                break;
        }
    }


}
