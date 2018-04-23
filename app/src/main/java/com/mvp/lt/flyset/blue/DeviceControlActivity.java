package com.mvp.lt.flyset.blue;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mvp.lt.flyset.BaseActivity;
import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.blue.bean.LaserData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/1/26/026
 */

public class DeviceControlActivity extends BaseActivity {
    @BindView(R.id.btn_off)
    Button mBtnOff;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.device_info)
    TextView mDeviceInfo;
    @BindView(R.id.device_data)
    TextView mDeviceData;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private String mDeviceName;
    private String mDeviceAddress;
    private boolean mConnected = false;
    private BluetoothLeService mBluetoothLeService;
    private String connectRequest = "AE A7 04 00 06 0A BC B7";

    private String dataStr = "";
    private List<LaserData> mStringList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e("TAG", "不能初始化蓝牙设备");

            }
            Log.e("TAG", "蓝牙服务开启");
            //mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN
                    }
                    , 1);
        }
        setContentView(R.layout.activity_device_control);
        ButterKnife.bind(this);
        setTitle("激光测距");
        initToolbarNav();

        //打开蓝牙服务
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        //注册广播
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

    }

    public void BtnOff(View view) {
        startActivityForResult(new Intent(this, DevicesConectActivity.class), 001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 001) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                mDeviceName = bundle.getString("DEVICE_NAME");
                mDeviceAddress = bundle.getString("DEVICE_ADDRESS");
                mDeviceInfo.setText(mDeviceName + "\n" + mDeviceAddress);
                Log.e("deviceinfo", mDeviceName + "\n" + mDeviceAddress);
                mBluetoothLeService.connect(mDeviceAddress);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.e("BroadcastReceiver", "action= " + action);
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.e("onReceive", "ACTION_GATT_CONNECTED");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.e("onReceive", "ACTION_GATT_DISCONNECTED");
                mConnected = false;
                mBtnOff.setText("连接设备");
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                Log.e("onReceive", "ACTION_GATT_SERVICES_DISCOVERED");
                mConnected = true;
                mBtnOff.setText("已连接");
                //第一步  连接请求
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBluetoothLeService.WriteValue("AE A7 04 00 06 0A BC B7");
                    }
                }, 300);

            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                byte[] data1 = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                String data = bytesToHexString(data1);
                Log.e("data", "data1=" + data1 + "\n data=" + data);
                if (data != null) {
                    if (data.contains("AE A7 04 00 86 8A BC B7")) {
                        Log.e("TAG", "手机发起连接请求成功");
                    } else if (data.contains("AE A7 04 00 08 0C BC B7")) {
                        Log.e("TAG", "回应心跳");
                        mBluetoothLeService.WriteValue("AE A7 04 00 88 8C BC B7");
                    } else {
                        if (dataStr.length() == 81) {
                            dataStr = "";
                            mDeviceData.setText("");
                        }
                        if (data.length() == 60) {
                            // mDeviceData.append("[" + data);
                            dataStr += data;
                        }
                        if (data.length() == 21) {
                            // mDeviceData.append(data + "]\n");
                            dataStr += data;
                            Log.e("datastr", dataStr.length() + "dataStr=" + dataStr + "==");
                            // mDeviceData.setText(dataStr);
                        }
                        if (dataStr.length() >= 81) {
                            //03 C3 00 1A 00 19 FF FE 00 00 0A 66 00 00 00 00
                            String s = dataStr.substring(15, 62);
                            Log.e("s", "s=" + s + "##");
                            String[] strings = s.split(" ");

                            List<Double> integerList = new ArrayList<>();
                            for (int i = 0; i < strings.length / 2; i++) {
                                String string = strings[i * 2] + strings[i * 2 + 1];
                                int x = Integer.parseInt(string, 16);
                                Log.e("TAG", x + "::");
                                integerList.add(x * 0.1);
                            }
                            DecimalFormat df = new DecimalFormat("#.00");
                            LaserData laserData = new LaserData();//(f)
                            laserData.setLevationAngle(Double.parseDouble(df.format(integerList.get(0))));
                            laserData.setSlopeDistance(Double.parseDouble(df.format(integerList.get(1))));
                            laserData.setSineHigh(Double.parseDouble(df.format(integerList.get(2))));
                            laserData.setHorizontalDistance(Double.parseDouble(df.format(integerList.get(3))));
                            laserData.setTwoHight(Double.parseDouble(df.format(integerList.get(4))));
                            laserData.setGuideCorner(Double.parseDouble(df.format(integerList.get(5))));
                            laserData.setHorizontalAngle(Double.parseDouble(df.format(integerList.get(6))));
                            laserData.setSpanDis(Double.parseDouble(df.format(integerList.get(7))));
                            mStringList.add(laserData);
                            Log.e("TAG", laserData.toString());
                            mDeviceData.append(laserData.toString() + "\n");
                        }
                        mScrollView.post(new Runnable() {
                            public void run() {
                                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });

                    }


                }
            }
        }
    };

    /**
     * DecimalFormat转换最简便
     */
    public void m2(double f) {

    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        return intentFilter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGattUpdateReceiver);
        unbindService(mServiceConnection);
        if (mConnected) {
            mBluetoothLeService.disconnect();
            mConnected = false;
        }
        if (mBluetoothLeService != null) {
            mBluetoothLeService.close();
            mBluetoothLeService = null;
        }
    }

}
