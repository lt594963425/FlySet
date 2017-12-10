package com.mvp.lt.flyset.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.ui.Base.BaseFragment;
import com.mvp.lt.flyset.ui.adapter.SpinnerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 快捷按键
 *
 * @author ${LiuTao}
 * @date 2017/12/8/008
 */

public class QuickKeyFragment extends BaseFragment {
    @BindView(R.id.q_FPV_left_spr)
    Spinner mQFPVLeftSpr;
    @BindView(R.id.q_FPV_right_spr)
    Spinner mQFPVRightSpr;
    @BindView(R.id.q_airlines_left_spr)
    Spinner mQAirlinesLeftSpr;
    @BindView(R.id.q_airlines_right_spr)
    Spinner mQAirlinesRightSpr;
    @BindView(R.id.q_follow_left_spr)
    Spinner mQFollowLeftSpr;
    @BindView(R.id.q_follow_right_spr)
    Spinner mQFollowRightSpr;
    @BindView(R.id.q_surround_left_spr)
    Spinner mQSurroundLeftSpr;
    @BindView(R.id.q_surround_right_spr)
    Spinner mQSurroundRightSpr;
    @BindView(R.id.q_focus_left_spr)
    Spinner mQFocusLeftSpr;
    @BindView(R.id.q_focus_right_spr)
    Spinner mQFocusRightSpr;
    @BindView(R.id.q_panorama_left_spr)
    Spinner mQPanoramaLeftSpr;
    @BindView(R.id.q_panorama_right_spr)
    Spinner mQPanoramaRightSpr;
    @BindView(R.id.q_track_left_spr)
    Spinner mQTrackLeftSpr;
    @BindView(R.id.q_track_right_spr)
    Spinner mQTrackRightSpr;
    @BindView(R.id.q_longPress_left_spr)
    Spinner mQLongPressLeftSpr;
    @BindView(R.id.q_longPress_right_spr)
    Spinner mQLongPressRightSpr;
    private Unbinder unbinder;

    public QuickKeyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quickkey_set_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initAllSpinnerView();
        return view;
    }

    private void initAllSpinnerView() {
        String[] mQFPVItems = getResources().getStringArray(R.array.SpinnerFPV);
        String[] mQAirlinesItems = getResources().getStringArray(R.array.SpinnerAirlines);
        String[] mQFollowItems = getResources().getStringArray(R.array.SpinnerFollow);
        String[] mQSurroundItems = getResources().getStringArray(R.array.SpinnerSurround);
        String[] mQFocusItems = getResources().getStringArray(R.array.SpinnerFocus);
        String[] mQPanoramaTrackItems = getResources().getStringArray(R.array.SpinnerPanoramaTrack);
        String[] mQLongPressItems = getResources().getStringArray(R.array.SpinnerLongPress);
        setSpinnerAdapter(mQFPVLeftSpr,mQFPVItems);
        setSpinnerAdapter(mQFPVRightSpr,mQFPVItems);
        setSpinnerAdapter(mQAirlinesLeftSpr,mQAirlinesItems);
        setSpinnerAdapter(mQAirlinesRightSpr,mQAirlinesItems);
        setSpinnerAdapter(mQFollowLeftSpr,mQFollowItems);
        setSpinnerAdapter(mQFollowRightSpr,mQFollowItems);
        setSpinnerAdapter(mQSurroundLeftSpr,mQSurroundItems);
        setSpinnerAdapter(mQSurroundRightSpr,mQSurroundItems);
        setSpinnerAdapter(mQFocusLeftSpr,mQFocusItems);
        setSpinnerAdapter(mQFocusRightSpr,mQFocusItems);
        setSpinnerAdapter(mQPanoramaLeftSpr,mQPanoramaTrackItems);
        setSpinnerAdapter(mQPanoramaRightSpr,mQPanoramaTrackItems);
        setSpinnerAdapter(mQTrackLeftSpr,mQPanoramaTrackItems);
        setSpinnerAdapter(mQTrackRightSpr,mQPanoramaTrackItems);
        setSpinnerAdapter(mQLongPressLeftSpr,mQLongPressItems);
        setSpinnerAdapter(mQLongPressRightSpr,mQLongPressItems);
        mQFPVLeftSpr.setSelection(2);

    }

    private void setSpinnerAdapter(Spinner spinner ,String[] items) {
        SpinnerArrayAdapter mAdapter = new SpinnerArrayAdapter(getActivity(), items);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
