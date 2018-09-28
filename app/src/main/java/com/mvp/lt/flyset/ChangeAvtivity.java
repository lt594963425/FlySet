package com.mvp.lt.flyset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvp.lt.flyset.Utils.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/3/24/024
 */

public class ChangeAvtivity extends AppCompatActivity {


    @BindView(R.id.aidehua)
    TextView mAidehua;
    @BindView(R.id.frame_layout_bigView)
    RelativeLayout mFrameLayoutBigView;

    @BindView(R.id.xiaoming)
    TextView mXiaoming;
    @BindView(R.id.frame_layout_littleView)
    RelativeLayout mFrameLayoutLittleView;
    private int defaultLocalHeight = 100;
    private int defaultLocalwidth = 150;
    private int defaultLocalMargin = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);

        defaultLocalMargin = ViewUtil.dp2px(defaultLocalMargin);
        defaultLocalwidth = ViewUtil.dp2px(defaultLocalwidth);
        defaultLocalHeight = ViewUtil.dp2px(defaultLocalHeight);
        // zoomOpera(mFrameLayoutBigView, mAidehua, mFrameLayoutLittleView, mXiaoming);
        zoomOpera(mFrameLayoutLittleView, mXiaoming, mFrameLayoutBigView, mAidehua);
    }

    private boolean status = true;

    @OnClick({R.id.aidehua, R.id.frame_layout_bigView, R.id.xiaoming, R.id.frame_layout_littleView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aidehua:
                if (status) {
                    zoomOpera(mFrameLayoutBigView, mAidehua, mFrameLayoutLittleView, mXiaoming);
                    status = false;

                }

                break;
            case R.id.xiaoming:
                if (!status) {
                    zoomOpera(mFrameLayoutLittleView, mXiaoming, mFrameLayoutBigView, mAidehua);
                    status = true;
                }

                break;

        }
    }

    /**
     * 大小视图切换 （小视图在前面、大视图在后面）
     *
     * @param sourcView  之前相对布局大小
     * @param beforeview 之前surfaceview
     * @param detView    之后相对布局大小
     * @param afterview  之后的surfaceview
     */
    private void zoomOpera(View sourcView, TextView beforeview,
                           View detView, TextView afterview) {
        RelativeLayout paretview = (RelativeLayout) sourcView.getParent();
        paretview.removeView(detView);
        paretview.removeView(sourcView);

        //设置远程大视图RelativeLayout 的属性
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        beforeview.setZOrderMediaOverlay(true);
//        beforeview.getHolder().setFormat(PixelFormat.TRANSPARENT);
        sourcView.setLayoutParams(params1);

        //设置本地小视图RelativeLayout 的属性
        params1 = new RelativeLayout.LayoutParams(defaultLocalwidth, defaultLocalHeight);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params1.setMargins(0, defaultLocalMargin, defaultLocalMargin, 0);

        //在调用setZOrderOnTop(true)之后调用了setZOrderMediaOverlay(true)  遮挡问题
//        afterview.setZOrderOnTop(true);
//        afterview.setZOrderMediaOverlay(true);
//        afterview.getHolder().setFormat(PixelFormat.TRANSPARENT);
        detView.setLayoutParams(params1);
        paretview.addView(sourcView);
        paretview.addView(detView);
    }

    /**
     * 隐藏标题栏
     */
    private void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    /**
     * 显示标题栏
     */
    private void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }
}
