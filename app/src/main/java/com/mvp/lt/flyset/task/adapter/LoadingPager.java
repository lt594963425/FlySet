package com.mvp.lt.flyset.task.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.mvp.lt.flyset.App;
import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.proxy.ThreadPoolProxyFactory;


/**
 * 1.提供视图-->4种视图中的一种(加载中视图,错误视图,空视图,成功视图)-->把自身提供出去就可以
 * 2.加载数据
 * 3.数据和视图的绑定
 */
public abstract class LoadingPager extends FrameLayout {
    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_ERROR = 1;//错误
    public static final int STATE_SUCCESS = 2;//成功
    public static final int STATE_EMPTY = 3;//空
    public int mCurState = STATE_LOADING;//默认是加载中的情况
    private Context mContext;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;
    private LoadDataTask mLoadDataTask;

    public LoadingPager(Context context) {
        super(context);
        this.mContext =context;
        initCommonView();
    }

    /**
     * @des 初始化常规视图(加载中视图, 错误视图, 空视图3个静态视图)
     * @called LoadingPager创建的时候
     */
    public void initCommonView() {
        //加载中视图
        mLoadingView = View.inflate(mContext, R.layout.pager_loading, null);
        this.addView(mLoadingView);

        //错误视图
        mErrorView = View.inflate(mContext, R.layout.pager_error, null);
        this.addView(mErrorView);

        //找到错误视图里面重试按钮,设置点击事件
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新触发加载数据
                triggerLoadData();
            }
        });


        //空视图
        mEmptyView = View.inflate(mContext, R.layout.pager_empty, null);
        this.addView(mEmptyView);

        //为什么在这里不去写成功视图-->loadingPager创建的时候无法决定成功视图
        refreshViewByState();
    }

    /**
     * @des 根据状态刷新ui(决定LoadingPager到底提供四种视图中的哪一种)
     * @called 1.LoadingPager创建的时候
     * @called 2.外界调用了triggerLoadData加载数据, 数据加载之前
     * @called 3.外界调用了triggerLoadData加载数据, 而且数据加载完成了
     */
    private void refreshViewByState() {
        //控制 加载中视图 显示隐藏
        if (mCurState == STATE_LOADING) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
        }

        //控制 错误视图 显示隐藏
        if (mCurState == STATE_ERROR) {
            mErrorView.setVisibility(View.VISIBLE);
        } else {
            mErrorView.setVisibility(View.GONE);
        }

        //控制 空视图 显示隐藏
        if (mCurState == STATE_EMPTY) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
        //这里就可能有成功视图了.因为数据已经加载完成了.而且数据加载成功了
        if (mSuccessView == null && mCurState == STATE_SUCCESS) {
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }
        // 控制 成功视图的 显示隐藏
        if (mSuccessView != null) {
            if (mCurState == STATE_SUCCESS) {
                mSuccessView.setVisibility(View.VISIBLE);
            } else {
                mSuccessView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @des 触发加载数据
     * @called 外界想让LoadingPager触发加载数据的时候调用
     */
    public void triggerLoadData() {
        if (mCurState != STATE_SUCCESS) {
            if (mLoadDataTask == null) {
                mCurState = STATE_LOADING;
                refreshViewByState();
                mLoadDataTask = new LoadDataTask();
                ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadDataTask);
            }
        }
    }

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            LoadedResult loadedResult = initData();
            mCurState = loadedResult.getState();
            App.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    //刷新UI(决定到底提供4种视图中的哪一种视图)
                    refreshViewByState();//mCurState-->Int
                }
            });

            //置空任务
            mLoadDataTask = null;
        }
    }

    public abstract LoadedResult initData();

    public abstract View initSuccessView();

    /**
     * 标识数据加载结果的枚举类
     */
    public enum LoadedResult {
        /**
         * STATE_ERROR = 1;//错误
         * STATE_SUCCESS = 2;//成功
         * STATE_EMPTY = 3;//空
         */
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

        private int state;

        public int getState() {
            return state;
        }

        LoadedResult(int state) {
            this.state = state;
        }
    }
}
