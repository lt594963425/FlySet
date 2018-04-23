package com.mvp.lt.flyset.seekbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mvp.lt.flyset.App;
import com.mvp.lt.flyset.BaseActivity;
import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.TTS.AmapTTSController;
import com.mvp.lt.flyset.view.RotationView;

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
    @BindView(R.id.dt_voice)
    EditText mDtVoice;
    private int mMax;
    private int mProgress = 20;
    AmapTTSController mAmapTTSController;
    int i = 0;
    public boolean isOnLongClick = false;
    boolean miusEnable = false;

    MiusThread miusThread;
    PlusThread plusThread;
    TextView tv;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RotationView rotationView = new RotationView(this);
        setContentView(R.layout.activity_seek);
        mAmapTTSController = AmapTTSController.getInstance(App.getContext());
        mAmapTTSController.init();
        ButterKnife.bind(this);
        mMax = mSeekbar.getMax();
        mTextData.setText(mProgress + "");
        mSeekbar.setProgress(mProgress + 45);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int number = progress - mMax / 2;
                mTextData.setText(number + "");
                Log.e("", progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        CompentOnTouch b = new CompentOnTouch();
        Button mButton = (Button) findViewById(R.id.button1);
        mButton.setOnTouchListener(b);

        Button mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnTouchListener(b);

        tv = (TextView) findViewById(R.id.textView1);

        mImageAdd.setOnTouchListener(b);
        mImageReduce.setOnTouchListener(b);


    }


    @OnClick({R.id.image_reduce, R.id.image_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_reduce:
                mProgress -= 1;
                if (mProgress < 0) {
                    mProgress = 0;

                }
                Log.e("减：", mProgress + "");
                mSeekbar.setProgress(mProgress);
                break;
            case R.id.image_add:
                mProgress += 1;
                if (mProgress > 90) {
                    mProgress = 90;
                }
                mSeekbar.setProgress(mProgress);
                Log.e("加：", mProgress + "");
                break;
        }
    }


    public void BoFang(View view) {
        String s = mDtVoice.getText().toString().trim();
        Log.e("语音", "当当" + s);
        mAmapTTSController.onSpeckTextToVice(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAmapTTSController.destroy();
    }


    // Touch事件
    class CompentOnTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                // 这是btnMius下的一个层，为了增强易点击性
                case R.id.button1:
                    onTouchChange("mius", event.getAction());
                case R.id.image_add:
                    onTouchChange("mius", event.getAction());
                    break;
                // 这里也写，是为了增强易点击性
                case R.id.button2:
                    onTouchChange("plus", event.getAction());
                case R.id.image_reduce:
                    onTouchChange("plus", event.getAction());
                    break;
            }
            return true;
        }
    }

    //加
    private void onTouchChange(String methodName, int eventAction) {
        // 按下松开分别对应启动停止减线程方法
        if ("mius".equals(methodName)) {
            if (eventAction == MotionEvent.ACTION_DOWN) {
                miusThread = new MiusThread();
                isOnLongClick = true;
                miusThread.start();
            } else if (eventAction == MotionEvent.ACTION_UP) {
                if (miusThread != null) {
                    isOnLongClick = false;
                }
            } else if (eventAction == MotionEvent.ACTION_MOVE) {
                if (miusThread != null) {
                    isOnLongClick = true;
                }
            }
        }
        // 按下松开分别对应启动停止加线程方法
        else if ("plus".equals(methodName)) {
            if (eventAction == MotionEvent.ACTION_DOWN) {
                plusThread = new PlusThread();
                isOnLongClick = true;
                plusThread.start();
            } else if (eventAction == MotionEvent.ACTION_UP) {
                if (plusThread != null) {
                    isOnLongClick = false;
                }
            } else if (eventAction == MotionEvent.ACTION_MOVE) {
                if (plusThread != null) {
                    isOnLongClick = true;
                }
            }
        }
    }


    // 加操作
    class MiusThread extends Thread {
        @Override
        public void run() {
            while (isOnLongClick) {
                try {
                    Thread.sleep(200);
                    mProgress += 1;
                    if (mProgress > 90) {
                        mProgress = 90;
                    }
                    // myHandler.sendEmptyMessage(1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSeekbar.setProgress(mProgress);
                            Log.d("TAG", "send:" + i++);
                            tv.setText("send:" + i++);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }


    // 减操作
    class PlusThread extends Thread {
        @Override
        public void run() {
            while (isOnLongClick) {
                try {
                    Thread.sleep(200);
                    mProgress -= 1;
                    if (mProgress < 45) {
                        mProgress = 0;
                    }

                    // myHandler.sendEmptyMessage(2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSeekbar.setProgress(mProgress);
                            Log.d("TAG", "send:" + i--);
                            tv.setText("send:" + i--);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
