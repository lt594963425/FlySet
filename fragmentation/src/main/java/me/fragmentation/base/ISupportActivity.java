package me.fragmentation.base;

import android.view.MotionEvent;

import me.anim.FragmentAnimator;


/**
 * Created by YoKey on 17/6/13.
 */

public interface ISupportActivity {
    SupportActivityDelegate getSupportDelegate();

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);

    FragmentAnimator onCreateFragmentAnimator();

    void onBackPressed();

    void onBackPressedSupport();

    boolean dispatchTouchEvent(MotionEvent ev);
}
