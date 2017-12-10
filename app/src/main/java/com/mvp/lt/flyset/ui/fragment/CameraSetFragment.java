package com.mvp.lt.flyset.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.ui.Base.BaseFragment;
import com.mvp.lt.flyset.ui.adapter.SpinnerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 相机设置
 *
 * @author ${LiuTao}
 * @date 2017/12/8/008
 */

public class CameraSetFragment extends BaseFragment {
    @BindView(R.id.c_automatic_video_switch)
    Switch mCAutomaticVideoSwitch;
    @BindView(R.id.c_video_file_format_spr)
    Spinner mCVideoFileFormatSpr;
    @BindView(R.id.c_anti_twinkle_spr)
    Spinner mCAntiTwinkleSpr;
    @BindView(R.id.c_show_setting_button_switch)
    Switch mCShowSettingButtonSwitch;
    @BindView(R.id.c_show_drection_map_switch)
    Switch mCShowDrectionMapSwitch;
    @BindView(R.id.c_grid_line_spr)
    Spinner mCGridLineSpr;
    @BindView(R.id.g_reset_all_set_tv)
    TextView mGResetAllSetTv;
    private Unbinder mUnbinder;
    public CameraSetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_set_fragment_layout, null);
        mUnbinder = ButterKnife.bind(this, view);
        initVideoFileFormatSpr();
        initAntiTwinkleSpr();
        initGridLineSpr();
        return view;
    }
    private void initVideoFileFormatSpr() {
        String[] mItems = getResources().getStringArray(R.array.SpinnerVideoFormat);
        SpinnerArrayAdapter mAdapter = new SpinnerArrayAdapter(getActivity(), mItems);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCVideoFileFormatSpr.setAdapter(mAdapter);
    }
    private void initAntiTwinkleSpr() {
        String[] mItems = getResources().getStringArray(R.array.SpinnerAntiTwinkle);
        SpinnerArrayAdapter mAdapter = new SpinnerArrayAdapter(getActivity(), mItems);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCAntiTwinkleSpr.setAdapter(mAdapter);
    }

    private void initGridLineSpr() {
        String[] mItems = getResources().getStringArray(R.array.SpinnerGridReferenceLine);
        SpinnerArrayAdapter mAdapter = new SpinnerArrayAdapter(getActivity(), mItems);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCGridLineSpr.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
