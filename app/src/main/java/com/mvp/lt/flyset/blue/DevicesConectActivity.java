package com.mvp.lt.flyset.blue;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.blue.adapter.DeviceListAdapter;

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

public class DevicesConectActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private boolean mScanning;
    private static final long SCAN_PERIOD = 10000;
    private DeviceListAdapter mDeviceListAdapter;

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
        setContentView(R.layout.activity_devices_conect);
        ButterKnife.bind(this);
        mToolbar.setTitle("设备连接");
        initToolbarNav();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "你的手机不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "蓝牙不支持", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mDeviceListAdapter = new DeviceListAdapter(this, mDeviceList);
        mRecyclerview.setAdapter(mDeviceListAdapter);
        mDeviceListAdapter.setOnItemClickListener(new DeviceListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final BluetoothDevice device = mDeviceList.get(position);
                if (device == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("DEVICE_NAME", device.getName());
                bundle.putString("DEVICE_ADDRESS", device.getAddress());
                setResult(RESULT_OK, getIntent().putExtras(bundle));
                if (mScanning) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanning = false;
                }
                finish();
            }
        });
        mDeviceListAdapter.setItemOnLongClickListener(new DeviceListAdapter.ItemOnLongClickListener() {
            @Override
            public void onItemOnLongClick(int position) {

            }
        });
        scanLeDevice(true);
    }

    public void initToolbarNav() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void scanLeDevice(boolean enable) {
        if (enable) {
            if (mScanning) {
                mScanning = false;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
            mScanning = true;
            //F000E0FF-0451-4000-B000-000000000000
            //mDeviceListAdapter.clear();
            mDeviceList.clear();
            mDeviceListAdapter.notifyDataSetChanged();
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            //mDeviceListAdapter.addDevice(device);
            if (!mDeviceList.contains(device)) {
                mDeviceList.add(device);
                mDeviceListAdapter.notifyDataSetChanged();


            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == 1) {
            scanLeDevice(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanLeDevice(false);
    }
}
