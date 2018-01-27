/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mvp.lt.flyset.blue;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;

    final String CLIENT_CHARACTERISTIC_CONFIG= "00002902-0000-1000-8000-00805f9b34fb";

    public final static String ACTION_GATT_CONNECTED =
            "com.mvp.lt.flyset.blue.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.mvp.lt.flyset.blue.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.mvp.lt.flyset.blue.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.mvp.lt.flyset.blue.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.mvp.lt.flyset.blue.le.EXTRA_DATA";

//  00001800-0000-1000-8000-00805f9b34fb
//  00002a04-0000-1000-8000-00805f9b34fb
//  00001801-0000-1000-8000-00805f9b34fb
//  00002a04-0000-1000-8000-00805f9b34fb
//  0000180a-0000-1000-8000-00805f9b34fb
//  00002a04-0000-1000-8000-00805f9b34fb
//  0000ffe0-0000-1000-8000-00805f9b34fb
//  00002a04-0000-1000-8000-00805f9b34fb


    public final static UUID UUID_NOTIFY =
            //UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
            UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE =
            //UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
            UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");

    public BluetoothGattCharacteristic mNotifyCharacteristic;


    /**
     * hex字符串转byte数组<br/>
     * 2个hex转为一个byte
     * @param str
     * @return
     */
    public static byte[] hexString2Bytes(String str){
        String src = str.replaceAll(" ","");
        byte[] res = new byte[src.length()/2];
        char[] chs = src.toCharArray();
        int[] b = new int[2];

        for(int i=0,c=0; i<chs.length; i+=2,c++){
            for(int j=0; j<2; j++){
                if(chs[i+j]>='0' && chs[i+j]<='9'){
                    b[j] = (chs[i+j]-'0');
                }else if(chs[i+j]>='A' && chs[i+j]<='F'){
                    b[j] = (chs[i+j]-'A'+10);
                }else if(chs[i+j]>='a' && chs[i+j]<='f'){
                    b[j] = (chs[i+j]-'a'+10);
                }
            }

            b[0] = (b[0]&0x0f)<<4;
            b[1] = (b[1]&0x0f);
            res[c] = (byte) (b[0] | b[1]);
        }

        return res;
    }

    public void WriteValue(String strValue) {
        //mNotifyCharacteristic.setValue(strValue.getBytes());
        //mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic);

        //String s="AEA704000509BCB7";
        //String s="4145204137203034203030203035203039204243204237";
//        byte[] b = new BigInteger(s,16).toByteArray();
//        mNotifyCharacteristic.setValue(b);

        /*
         already tested
         "AE A7 04 00 05 09 BC B7"
         "AEA704000509BCB7"
         "41 45 41 37 30 34 30 30 30 35 30 39 42 43 42 37"
         "41454137303430303035303942434237"
         "AE:A7:04:00:05:09:BC:B7"
         "41 45 3a 41 37 3a 30 34 3a 30 30 3a 30 35 3a 30 39 3a 42 43 3a 42 37"
         "41453a41373a30343a30303a30353a30393a42433a4237"
         "®\n" + "\u0007\u0004\u0005\t¼\u000B\u0007"
         "®§\u0004\u0005\t¼·"
         "®\u0007\u0004\u0005\t¼\u0007"
         "A\u0004\u0005A\u0003\u00070\u0003\u00040\u00030\u0003\u00050\u0003\tB\u0004\u0003B\u0003\u0007"
         "A\u0004\u0005:\u0004\u00017\u0003\n" + "0\u0003\u0004:\u00030\u0003\n" + "0\u0003\u0005:\u00039\u0003\n" + "B\u0004\u0003:\u0004\u00027"
          new byte[]{(byte)0xAE, (byte)0xA7, 4, 0, 5, 9, (byte)0xBC, (byte)0xB7}
          */
        byte[] b = new byte[hexString2Bytes(strValue).length];
        b = hexString2Bytes(strValue);

        mNotifyCharacteristic.setValue(b);

//        mNotifyCharacteristic.setValue(new byte[]{
//                (byte) (0xae & 0xff), (byte) (0xa7 & 0xff), 0x04, 0x00, 0x06, 0x0a,
//                (byte) (0xbc & 0xff), (byte) (0xb7 & 0xff)
//        });


        //hexStringToByteArray("AE A7 04 00 05 09 BC B7");
        //mNotifyCharacteristic.setValue(hexStringToByteArray("4145204137203034203030203035203039204243204237"));
        //mNotifyCharacteristic.setValue("AEA704000105BCB7".getBytes());
        // mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic);
        boolean sendStatus = mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic);
        Log.d("TESTING","status= "+sendStatus);
        Log.d("TESTING","mNotifyCharacteristic= "+mNotifyCharacteristic);
       // Log.d("TESTING","mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic)= "+mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic));

    }

    public void findService(List<BluetoothGattService> gattServices) {
        Log.i(TAG, "Count is:" + gattServices.size());
        for (BluetoothGattService gattService : gattServices)
        {
            Log.i(TAG, gattService.getUuid().toString());
            Log.i(TAG, UUID_SERVICE.toString());
            if(gattService.getUuid().toString().equalsIgnoreCase(UUID_SERVICE.toString()))
            {
                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Log.i(TAG, "Count is:" + gattCharacteristics.size());
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    if(gattCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_NOTIFY.toString()))
                    {
                        Log.i(TAG, gattCharacteristic.getUuid().toString());
                        Log.i(TAG, UUID_NOTIFY.toString());
                        mNotifyCharacteristic = gattCharacteristic;
                        setCharacteristicNotification(gattCharacteristic, true);
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                        return;
                    }
                }
            }
        }
    }

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            Log.i(TAG, "oldStatus=" + status + " NewStates=" + newState);
            if(status == BluetoothGatt.GATT_SUCCESS)
            {

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    intentAction = ACTION_GATT_CONNECTED;

                    broadcastUpdate(intentAction);
                    Log.i(TAG, "Connected to GATT server.");
                    // Attempts to discover services after successful connection.
                    Log.i(TAG, "Attempting to start service discovery:" +
                            mBluetoothGatt.discoverServices());
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    intentAction = ACTION_GATT_DISCONNECTED;
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                    Log.i(TAG, "Disconnected from GATT server.");
                    broadcastUpdate(intentAction);
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.w(TAG, "onServicesDiscovered received: " + status);
                findService(gatt.getServices());
            } else {
                if(mBluetoothGatt.getDevice().getUuids() == null)
                    Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
            Log.d("TESTING","characteristic 1= "+characteristic);
            Log.d("TESTING","status 1= "+status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            Log.e(TAG, "OnCharacteristicWrite");
            Log.d("TESTING","characteristic 2= "+characteristic);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
        {
            Log.e(TAG, "OnCharacteristicWrite");
            Log.d("TESTING","status !!!= "+status);
            Log.d("TESTING","gatt !!!= "+gatt);
            Log.d("TESTING","characteristic !!!= "+characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
            Log.e(TAG, "onDescriptorRead");
            Log.d("TESTING","onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
            Log.e(TAG, "onDescriptorWrite");
            Log.d("TESTING","onDescriptorWrite");
            Log.d("TESTING","status= "+status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int a, int b)
        {
            Log.e(TAG, "onReadRemoteRssi");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int a)
        {
            Log.e(TAG, "onReliableWriteCompleted");
            Log.d("TESTING","onReliableWriteCompleted");
        }

    };

    private void broadcastUpdate(final String action) {
        Log.d("TESTING","broadcastUpdate 1");
        Log.d("TESTING","action= "+action);
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {
        Log.d("TESTING","broadcastUpdate 2");
        final Intent intent = new Intent(action);

        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        // For all other profiles, writes the data formatted in HEX.
        final byte[] data = characteristic.getValue();
        Log.d("TESTING","data= "+data);
        Log.d("TESTING","characteristic.getValue()= "+characteristic.getValue());

//
//        StringBuilder sb = new StringBuilder();
//        for (byte b : data) {
//            if (sb.length() > 0) {
//                sb.append(':');
//            }
////            sb.append(String.format("%02x", b));
//            sb.append(String.format("0x2901", b));
//        }
//        Log.d("TESTING","sb= "+sb);
//
//
//
//
//        StringBuilder sb1 = new StringBuilder();
//        for (byte b : data) {
//            if (sb1.length() > 0) {
//                sb1.append(':');
//            }
//
////            sb.append(String.format("%02x", b));
//            sb1.append(String.format("0x2902", b));
//        }
//        Log.d("TESTING","sb1= "+sb1);
//
//
//
//        StringBuilder sb3 = new StringBuilder();
//        for (byte b : data) {
//            if (sb3.length() > 0) {
//                sb3.append(':');
//            }
//
//            sb3.append(String.format("%02x", b));
//        }
//        Log.d("TESTING","sb3= "+sb3);




        if (data != null && data.length > 0) {
            //final StringBuilder stringBuilder = new StringBuilder(data.length);
            //for(byte byteChar : data)
            //    stringBuilder.append(String.format("%02X ", byteChar));
            //intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
           //intent.putExtra(EXTRA_DATA, new String(data)); //org jensenhaw
            intent.putExtra(EXTRA_DATA, data); //jensenhaw
            //Log.d("TESTING","data= "+data);
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    public boolean initialize() {

        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.e(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
/*
        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }
*/
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.e(TAG, "Device not found.  Unable to connect.");
            return false;
        }

        if(mBluetoothGatt != null)
        {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.e(TAG, "Trying to create a new connection.");
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        Log.d("TESTING","setCharacteristicNotification");
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
/*
        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
        */

        for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
            Log.d("TESTING","descriptor");
            Log.d("TESTING","characteristic.getUuid()= "+characteristic.getUuid());
            //find descriptor UUID that matches Client Characteristic Configuration (0x2902)
            // and then call setValue on that descriptor
            if (UUID_NOTIFY.equals(characteristic.getUuid())) {
                Log.d("TESTING","UUID_SERVICE.equals(characteristic.getUuid()");
                descriptor = characteristic.getDescriptor(UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG));
                characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(descriptor);
                Log.d("TESTING","descriptor= "+descriptor);
                Log.d("TESTING","characteristic.getUuid()= "+characteristic.getUuid());
                Log.d("TESTING","UUID_SERVICE= "+UUID_SERVICE);

            }
        }

    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;
        return mBluetoothGatt.getServices();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
