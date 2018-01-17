package com.mvp.lt.flyset.task;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.task.adapter.LoadingPager;
import com.mvp.lt.flyset.task.adapter.TaskCompleteAdapter;
import com.mvp.lt.flyset.task.base.TaskBaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 当前任务
 * A simple {@link Fragment} subclass.
 *
 * @author LiuTao
 */
public class TaskMineFragment extends TaskBaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;



    private TaskCompleteAdapter mAdapter = null;
    /**
     * 列表任务集合
     */
    private List<String> mList ;
    /**
     * 页码
     */
    private int current_page;
    /**
     * 完成任务的状态
     */
    private String OTHER_STATUS = "2";
    /**
     * / 标志位，标志已经初始化完成。
     */
    private boolean isPrepared;
    private Map<String, Object> mQueryMaps;

    public TaskMineFragment() {
        // Required empty public constructor
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        try {
            loadData();
            return checkResult(mList);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_task_completed, null);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLinearLayoutManager);
        mAdapter = new TaskCompleteAdapter(getActivity(), mList);
        recyclerview.setAdapter(mAdapter);
        return view;
    }
    public void loadData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("测试数据" + i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
