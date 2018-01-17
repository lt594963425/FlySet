package com.mvp.lt.flyset;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by YoKey on 16/11/23.
 */
public class App extends Application {
    public static Context context;
    private static Handler mMainThreadHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        //主线程的Handler
        mMainThreadHandler = new Handler();
        context = getApplicationContext();
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
                // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                // 生产环境时，捕获上述异常（避免crash），会捕获
                // 建议在回调处上传下面异常到崩溃监控服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }
    /**
     * 得到主线程里面的创建的一个hanlder
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }
    public static Context getContext() {
        return context;
    }
}
