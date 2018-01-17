package com.mvp.lt.flyset.task;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.Utils.ToastUtil;
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
 * A simple {@link Fragment} subclass.
 *
 * @author LiuTao
 */
public class TaskPublishFragment extends TaskBaseFragment implements TaskCompleteAdapter.ItemClickListener, TaskCompleteAdapter.ItemOnLongClickListener {
    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    private TaskCompleteAdapter mAdapter = null;
    /**
     * 列表任务集合
     */
    private List<String> mList;
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

    public TaskPublishFragment() {
        // Required empty public constructor
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        try {
            mList = loadData();
            return checkResult(mList);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    public List<String> loadData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据" + i);
        }
        return data;
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_task_completed, null);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLinearLayoutManager);
        mAdapter = new TaskCompleteAdapter(getActivity(), mList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setItemOnLongClickListener(this);
        recyclerview.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onItemClick(int position) {
        ToastUtil.showToast("单击" + position);
    }

    @Override
    public void onItemOnLongClick(int position) {
        ToastUtil.showToast("长按" + position);
    }
}
