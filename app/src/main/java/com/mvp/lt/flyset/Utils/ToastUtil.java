package com.mvp.lt.flyset.Utils;

import android.view.Gravity;
import android.widget.Toast;

import com.mvp.lt.flyset.App;

/**
 * Created by caoyu on 2017/12/1/001.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        toast.show();
    }

    public static void showToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(resId);
        toast.show();
    }

    public static void cancleToast() {
        if (toast != null) {
            toast.cancel();
        }

    }

}
