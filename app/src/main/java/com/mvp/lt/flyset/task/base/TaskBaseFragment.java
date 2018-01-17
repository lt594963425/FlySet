package com.mvp.lt.flyset.task.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.lt.flyset.task.adapter.LoadingPager;

import java.util.List;
import java.util.Map;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2018/1/16/016
 */

public abstract class TaskBaseFragment extends Fragment {
    private LoadingPager mLoadingPager;

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLoadingPager = new LoadingPager(getContext()) {
            @Override
            public LoadedResult initData() {
                return TaskBaseFragment.this.initData();
            }

            @Override
            public View initSuccessView() {
                return TaskBaseFragment.this.initSuccessView();
            }
        };
        return mLoadingPager;
    }

    public abstract LoadingPager.LoadedResult initData();

    public abstract View initSuccessView();

    /**
     * @des 校验请求回来的数据
     */
    public LoadingPager.LoadedResult checkResult(Object resObj) {
        if (resObj == null) {
            return LoadingPager.LoadedResult.EMPTY;
        }
        //resObj -->List
        if (resObj instanceof List) {
            if (((List) resObj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        //resObj -->Map
        if (resObj instanceof Map) {
            if (((Map) resObj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }

}
