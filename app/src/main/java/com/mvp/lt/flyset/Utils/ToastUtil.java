package com.mvp.lt.flyset.Utils;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.mvp.lt.flyset.App;

/**
 * Created by caoyu on 2017/12/1/001.
 */

public class ToastUtil {
    private static Toast toast;
    private static Handler mUIHandler = new Handler(Looper.getMainLooper());
    public static void showToast(final String msg) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(App.getContext(), msg + "", Toast.LENGTH_SHORT);
                }
                toast.setText(msg);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static void showToast(final int resId) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(App.getContext(), resId + "", Toast.LENGTH_SHORT);
                }
                toast.setText(resId);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static void cancleToast() {
        if (toast != null) {
            toast.cancel();
        }

    }

}
