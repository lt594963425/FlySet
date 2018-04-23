package com.mvp.lt.flyset.Utils;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.lt.flyset.App;


/**
 *
 */

public class ToastUtils {
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
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setText(resId);
                toast.show();
            }
        });

    }


    public static void setResultToToast(final String string) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getContext(), string + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setResultToText(final TextView tv, final String s) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (tv == null) {
                    Toast.makeText(App.getContext(), "tv is null", Toast.LENGTH_SHORT).show();
                } else {
                    tv.setText(s);
                }
            }
        });
    }

}
