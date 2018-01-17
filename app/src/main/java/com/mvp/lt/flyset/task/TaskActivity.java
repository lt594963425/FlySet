package com.mvp.lt.flyset.task;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.mvp.lt.flyset.BaseActivity;
import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.task.adapter.LoadingPager;
import com.mvp.lt.flyset.task.base.TaskBaseFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 踏查
 *
 * @author LiuTao
 */
public class TaskActivity extends BaseActivity {

    @BindView(R.id.tablatout)
    TabLayout tablatout;
    @BindView(R.id.vp)
    ViewPager vp;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private TaskPublishFragment taskPublishFragment;
    private TaskMineFragment taskMineFragment;
    private TaskCompletedFragment taskCompletedFragment;
    private Map<Integer, TaskBaseFragment> mFragmentList = new HashMap<>();
    /**
     * 适配器
     */
    private DemandAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        setTitle("任务管理");
        initToolbarNav();
        initFragment();
        initData();
    }

    private void initFragment() {
        //初始化各fragment
        taskPublishFragment = new TaskPublishFragment();
        taskMineFragment = new TaskMineFragment();
        taskCompletedFragment = new TaskCompletedFragment();
        //将fragment装进列表中
        mFragmentList.put(0, taskPublishFragment);
        mFragmentList.put(1, taskMineFragment);
        mFragmentList.put(2, taskCompletedFragment);

    }

    private void initData() {
        tablatout.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new DemandAdapter(getSupportFragmentManager());
        vp.setOffscreenPageLimit(0);
        vp.setAdapter(mAdapter);
        //TabLayout加载viewpager
        tablatout.setupWithViewPager(vp);
        tablatout.getTabAt(0).setText("已发布");
        tablatout.getTabAt(1).setText("执行中");
        tablatout.getTabAt(2).setText("已完成");
        //监听ViewPager页面的切换
        final TaskVpChangeListener taskVpChangeListener = new TaskVpChangeListener();
        vp.addOnPageChangeListener(taskVpChangeListener);
        vp.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                taskVpChangeListener.onPageSelected(0);
                vp.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    class TaskVpChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // 如果根据position找到对应页面的Fragment
            TaskBaseFragment baseFragment = mFragmentList.get(position);
            // 拿到Fragment里面的LoadingPager
            LoadingPager loadingPager = baseFragment.getLoadingPager();
            // 触发加载数据
            loadingPager.triggerLoadData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    /**
     * 适配器
     */
    public class DemandAdapter extends FragmentStatePagerAdapter {
        public DemandAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
