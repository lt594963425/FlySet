package com.mvp.lt.flyset.task;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.task.adapter.LoadingPager;
import com.mvp.lt.flyset.task.adapter.TaskCompleteAdapter;
import com.mvp.lt.flyset.task.base.TaskBaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author LiuTao
 */
public class TaskCompletedFragment extends TaskBaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private TaskCompleteAdapter mAdapter = null;
    /**
     * 列表任务集合
     */
    private List<String> mList;
    /**
     * 完成任务的状态
     */
    private String OTHER_STATUS = "2";
    Unbinder unbinder;

    public TaskCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        LoadingPager.LoadedResult loadedResult;
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

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

    }

}
