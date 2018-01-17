package com.mvp.lt.flyset;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by caoyu on 2018/1/8/008.
 */

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    protected Toolbar getToolbar() {
        return mToolbar;
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

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
    }

    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolbar();

    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initToolbar();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //	加载对话框相关
    private ProgressDialog mProgressDialog;

    public ProgressDialog getProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        } else {
            mProgressDialog = new ProgressDialog(this);
        }
        return mProgressDialog;
    }

    public void showProgressDialog(CharSequence message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(message);
        if (!isFinishing() && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void showProgressDialog(CharSequence message, boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setMessage(message);
        if (!isFinishing() && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void showProgressDialog(int message) {
        showProgressDialog(getText(message));
    }

    public void closeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }
}
