package com.mvp.lt.flyset.coner;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mvp.lt.flyset.BaseActivity;
import com.mvp.lt.flyset.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/1/29/029
 */

public class CompassActivity extends BaseActivity {
    @BindView(R.id.text_compass)
    TextView mTextCompass;
    /** 传感器管理器 */
    private SensorManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        setTitle("指南针");
        ButterKnife.bind(this);
        //获取系统服务（SENSOR_SERVICE)返回一个SensorManager 对象
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }
    @Override
    protected void onResume() {
        /**
         * 获取方向传感器
         * 通过SensorManager对象获取相应的Sensor类型的对象
         */
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //应用在前台时候注册监听器
        manager.registerListener(new MySensorEventListener(), sensor,
                SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }
    class MySensorEventListener implements SensorEventListener {
        private float predegree = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                /**
                 *  values[0]: x-axis 方向加速度
                 　  values[1]: y-axis 方向加速度
                 　　values[2]: z-axis 方向加速度
                 */
                float degree = event.values[0];// 存放了方向值
                predegree=-degree;
                mTextCompass.setText(""+((int)degree)+"°");

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {


        }

    }
}
