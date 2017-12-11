package com.mvp.lt.flyset.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 语音提示
 *
 * @author ${LiuTao}
 * @date 2017/12/8/008
 */

public class VoiceHintFragment extends BaseFragment {
    @BindView(R.id.v_voice_hint_switch)
    Switch mVVoiceHintSwitch;
    @BindView(R.id.v_voice_hint_rate_et)
    EditText mVVoiceHintRateEt;
    @BindView(R.id.voice_setting_tv)
    TextView mVoiceSettingTv;
    @BindView(R.id.v_height_hint_switch)
    Switch mVHeightHintSwitch;
    @BindView(R.id.v_direction_hint_switch)
    Switch mVDirectionHintSwitch;
    @BindView(R.id.v_speed_hint_switch)
    Switch mVSpeedHintSwitch;
    @BindView(R.id.v_electric_hint_switch)
    Switch mVElectricHintSwitch;
    @BindView(R.id.v_electric_hint_rate_et)
    EditText mVElectricHintRateEt;
    @BindView(R.id.electric_setting_tv)
    TextView mElectricSettingTv;
    @BindView(R.id.v_low_electric_alarm_value_et)
    EditText mVLowElectricAlarmValueEt;
    @BindView(R.id.low_electric_tv)
    TextView mLowElectricTv;
    @BindView(R.id.v_low_satellite_alarm_value_et)
    EditText mVLowSatelliteAlarmValueEt;
    @BindView(R.id.low_satellite_tv)
    TextView mLowSatelliteTv;
    private Unbinder unbinder;

    public VoiceHintFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.voice_set_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
