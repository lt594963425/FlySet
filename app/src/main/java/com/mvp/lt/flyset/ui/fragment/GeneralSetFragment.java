package com.mvp.lt.flyset.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ScrollView;
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
 * 通用设置
 *
 * @author ${LiuTao}
 * @date 2017/12/7/007
 */

public class GeneralSetFragment extends BaseFragment {
    @BindView(R.id.g_mapType_spr)
    Spinner mGMapTypeSpr;
    @BindView(R.id.g_inch_rb)
    RadioButton mGInchRb;
    @BindView(R.id.g_metric_rb)
    RadioButton mGMetricRb;
    @BindView(R.id.g_battery_switch)
    Switch mGBatterySwitch;
    @BindView(R.id.g_return_direction_switch)
    Switch mGReturnDirectionSwitch;
    @BindView(R.id.g_gps_coordinate_switch)
    Switch mGGpsCoordinateSwitch;
    @BindView(R.id.g_map_scale_switch)
    Switch mGMapScaleSwitch;
    @BindView(R.id.setting_sv)
    ScrollView mSettingSllV;
    @BindView(R.id.setting_tv)
    TextView mSettingTv;
    @BindView(R.id.g_air_last_position_tv)
    TextView mGAirLastPositionTv;
    @BindView(R.id.g_reset_all_set_tv)
    TextView mGResetAllSetTv;
    @BindView(R.id.g_help)
    TextView mGHelp;
    @BindView(R.id.g_language_spr)
    Spinner mGLanguageSpr;
    Unbinder unbinder;
    @BindView(R.id.g_privacy_notice_tv)
    TextView mGPrivacyNoticeTv;
    @BindView(R.id.g_license_agreement_tv)
    TextView mGLicenseAgreementTv;

    public GeneralSetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_set_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initScrollView();
        initMapTypeSpinnerView();
        initLanguageSpinnerView();
        initView();
        return view;
    }

    private void initView() {

    }


    private void initScrollView() {
        mSettingSllV.smoothScrollTo(0, 0);
    }

    private void initMapTypeSpinnerView() {
        String[] mItems = getResources().getStringArray(R.array.SpinnerMapType);
        SpinnerArrayAdapter mAdapter = new SpinnerArrayAdapter(getActivity(), mItems);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mGMapTypeSpr.setAdapter(mAdapter);
    }

    private void initLanguageSpinnerView() {
        String[] mItems = getResources().getStringArray(R.array.SpinnerLanguages);
        SpinnerArrayAdapter mAdapter = new SpinnerArrayAdapter(getActivity(), mItems);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mGLanguageSpr.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
