package com.mvp.lt.flyset.dji;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.lt.flyset.App;
import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.common.util.CommonCallbacks;
import dji.keysdk.AirLinkKey;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.camera.MediaManager;
import dji.sdk.camera.VideoFeeder;
import dji.sdk.sdkmanager.DJISDKManager;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/3/22/022
 */

public class DJIFPVActivity extends AppCompatActivity implements MediaManager.VideoPlaybackStateListener {

    private static final String TAG = DJIFPVActivity.class.getSimpleName();
    @BindView(R.id.primaryVideoFeedTitle)
    TextView mPrimaryVideoFeedTitle;
    @BindView(R.id.tv_mediaPushInfo)
    TextView mTvMediaPushInfo;

    private VideoFeeder.PhysicalSourceListener sourceListener;
    //protected DJICodecManager mCodecManager = null;
    private List<String> missingPermission = new ArrayList<>();
    private AtomicBoolean isRegistrationInProgress = new AtomicBoolean(false);
    private static final String[] REQUIRED_PERMISSION_LIST = new String[]{
            Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };
    private static final int REQUEST_PERMISSION_CODE = 12345;
    private AirLinkKey extEnabledKey;
    private AirLinkKey lbBandwidthKey;
    private AirLinkKey hdmiBandwidthKey;
    private AirLinkKey mainCameraBandwidthKey;
    private SetCallback setBandwidthCallback;
    private SetCallback setExtEnableCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndRequestPermissions();
        setContentView(R.layout.activity_dji_fpv);
        ButterKnife.bind(this);
        initDJIMedia();
    }


    private void checkAndRequestPermissions() {
        // Check for permissions
        for (String eachPermission : REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(this, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission);
            }
        }
        // Request for missing permissions
        if (missingPermission.isEmpty()) {
            startSDKRegistration();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    missingPermission.toArray(new String[missingPermission.size()]),
                    REQUEST_PERMISSION_CODE);
        }

    }

    private void startSDKRegistration() {
        if (isRegistrationInProgress.compareAndSet(false, true)) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    DJISDKManager.getInstance().registerApp(DJIFPVActivity.this.getApplicationContext(), new DJISDKManager.SDKManagerCallback() {
                        @Override
                        public void onRegister(DJIError djiError) {
                            if (djiError == DJISDKError.REGISTRATION_SUCCESS) {
                                DJILog.e("App registration", DJISDKError.REGISTRATION_SUCCESS.getDescription());
                                DJISDKManager.getInstance().startConnectionToProduct();
                                //ToastUtils.setResultToToast("SUCCCESS");
                            } else {
                                // ToastUtils.setResultToToast("flaied");
                            }
                        }

                        @Override
                        public void onProductChange(BaseProduct oldProduct, BaseProduct newProduct) {
                            Log.d(TAG, String.format("onProductChanged oldProduct:%s, newProduct:%s", oldProduct, newProduct));
                            // notifyStatusChange();
                            App.product = newProduct;
                            App.product.setBaseProductListener(mDJIBaseProductListener);
                            initDJIMedia();
                        }
                    });
                }
            });
        }
    }

    private BaseComponent.ComponentListener mDJIComponentListener = new BaseComponent.ComponentListener() {

        @Override
        public void onConnectivityChange(boolean isConnected) {
            Log.e(TAG, "onComponentConnectivityChanged: " + isConnected);
            //notifyStatusChange();
        }
    };
    private BaseProduct.BaseProductListener mDJIBaseProductListener = new BaseProduct.BaseProductListener() {

        @Override
        public void onComponentChange(BaseProduct.ComponentKey key,
                                      BaseComponent oldComponent,
                                      BaseComponent newComponent) {

            if (newComponent != null) {
                newComponent.setComponentListener(mDJIComponentListener);
            }
            Log.e(TAG,
                    String.format("onComponentChange key:%s, oldComponent:%s, newComponent:%s",
                            key,
                            oldComponent,
                            newComponent));

            // notifyStatusChange();
        }

        @Override
        public void onConnectivityChange(boolean isConnected) {

            Log.d(TAG, "onProductConnectivityChanged: " + isConnected);

            //notifyStatusChange();
        }
    };
    private MediaManager mediaManager;

    private boolean initDJIMedia() {
        BaseProduct product;
        try {
            product = App.getProductInstance();
        } catch (Exception exception) {
            product = null;
        }
        if (product == null) {
            //  DJIMediaList.clear();
            //  listAdapter.notifyDataSetChanged();
            // ToastUtils.setResultToToast(getContext().getResources().getString(R.string.playback_disconnected));
            return false;
        } else {
            if (null != App.getProductInstance().getCamera()
                    && App.getProductInstance().getCamera().isMediaDownloadModeSupported()) {
                mediaManager = App.getProductInstance().getCamera().getMediaManager();

                if (null != mediaManager) {
                    if (mediaManager.isVideoPlaybackSupported()) {
                        mediaManager.addMediaUpdatedVideoPlaybackStateListener(this);
                    }
                }

                SettingsDefinitions.CameraMode mode = SettingsDefinitions.CameraMode.MEDIA_DOWNLOAD;

                Log.e(TAG, "Media Test set Camera Mode " + mode);
                App.getProductInstance()
                        .getCamera()
                        .setMode(mode, new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(DJIError error) {

                                if (error == null) {
                                    Log.e(TAG, "Media Test set Camera Mode success");
//                                    handler.sendMessage(handler.obtainMessage(SHOW_PROGRESS_DIALOG, null));
//                                    handler.sendMessageDelayed(handler.obtainMessage(FETCH_FILE_LIST, null),
//                                            2000);
                                } else {
                                    Log.e(TAG, "Media Test set Camera Mode failure");
//                                    handler.sendMessage(handler.obtainMessage(SHOW_TOAST,
//                                            error.getDescription()));
                                }
                            }
                        });
            } else if (null != App.getProductInstance().getCamera()
                    && !App.getProductInstance().getCamera().isMediaDownloadModeSupported()) {
                ToastUtils.setResultToToast("Do not support Media list function");
                return false;
            } else {
                // ToastUtils.setResultToToast(getContext().getString(R.string.playback_disconnected));
                return false;
            }
        }
        return true;
    }

    @Override
    public void onUpdate(MediaManager.VideoPlaybackState videoPlaybackState) {
        updateTextView(videoPlaybackState);
    }


    private void updateTextView(MediaManager.VideoPlaybackState currentVideoPlaybackState) {
        final StringBuilder pushInfo = new StringBuilder();

        addLineToSB(pushInfo, "Video Playback State", null);
        if (currentVideoPlaybackState != null) {
            if (currentVideoPlaybackState.getPlayingMediaFile() != null) {
                addLineToSB(pushInfo, "media index", currentVideoPlaybackState.getPlayingMediaFile().getIndex());
                addLineToSB(pushInfo, "media size", currentVideoPlaybackState.getPlayingMediaFile().getFileSize());
                addLineToSB(pushInfo,
                        "media duration",
                        currentVideoPlaybackState.getPlayingMediaFile().getDurationInSeconds());
                addLineToSB(pushInfo,
                        "media created date",
                        currentVideoPlaybackState.getPlayingMediaFile().getDateCreated());
                addLineToSB(pushInfo,
                        "media orientation",
                        currentVideoPlaybackState.getPlayingMediaFile().getVideoOrientation());
            } else {
                addLineToSB(pushInfo, "media index", "None");
            }
            addLineToSB(pushInfo, "media current position", currentVideoPlaybackState.getPlayingPosition());
            addLineToSB(pushInfo, "media current status", currentVideoPlaybackState.getPlaybackStatus());
            pushInfo.append("\n");
           // setResultToText(pushInfo.toString());
        } else {
           // setResultToText("playbackState is null");
        }
    }

    private static void addLineToSB(StringBuilder sb, String name, Object value) {
        sb.append(name + ": ").
                append(value == null ? "" : value + "").
                append("\n");
    }

    private void setResultToText(String s) {
        final String str = s;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvMediaPushInfo == null) {
                    Log.e(TAG, "tv_playbackInfo = null");
                } else {
                    mTvMediaPushInfo.setText(str);
                }
            }
        });
    }
    /**
     * Result of runtime permission request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check for granted permission and remove from missing list
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = grantResults.length - 1; i >= 0; i--) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    missingPermission.remove(permissions[i]);
                }
            }
        }
        // If there is enough permission, we will start the registration
        if (missingPermission.isEmpty()) {
            startSDKRegistration();
        } else {
            Toast.makeText(getApplicationContext(), "Missing permissions!!!", Toast.LENGTH_LONG).show();
        }
    }
}
