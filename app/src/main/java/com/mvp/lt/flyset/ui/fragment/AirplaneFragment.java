package com.mvp.lt.flyset.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.ui.adapter.SpinnerArrayAdapter;
import com.mvp.lt.flyset.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2017/12/8/008
 */

public class AirplaneFragment extends BaseFragment {
    @BindView(R.id.a_returnHeight_ev)
    EditText mAReturnHeightEv;
    @BindView(R.id.a_maxLimit_height_ev)
    EditText mAMaxLimitHeightEv;
    @BindView(R.id.a_dynamic_return_switch)
    Switch mADynamicReturnSwitch;
    @BindView(R.id.a_calibrated_compass)
    TextView mACalibratedCompass;
    @BindView(R.id.a_photoVideo_preview_spr)
    Spinner mAPhotoVideoPreviewSpr;
    @BindView(R.id.a_auto_takeoff_switch)
    Switch mAAutoTakeoffSwitch;
    @BindView(R.id.a_remote_accuracy_locate_et)
    EditText mARemoteAccuracyLocateEt;
    @BindView(R.id.a_cloud_platform_model_spr)
    Spinner mACloudPlatformModelSpr;
    @BindView(R.id.a_cloud_angle_expand_switch)
    Switch mACloudAngleExpandSwitch;
    @BindView(R.id.a_cloud_vr_control_switch)
    Switch mACloudVrControlSwitch;
    @BindView(R.id.a_use_legacy_mode_switch)
    Switch mAUseLegacyModeSwitch;
    @BindView(R.id.a_front_arm_lamp_switch)
    Switch mAFrontArmLampSwitch;
    Unbinder unbinder;

    public AirplaneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.airplane_set_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initSpinner();

        Log.e("TAG",mAPhotoVideoPreviewSpr.getSelectedItemId()+"");
        Log.e("TAG",mAPhotoVideoPreviewSpr.getSelectedItem()+"");
        Log.e("TAG",mAPhotoVideoPreviewSpr.getSelectedItemPosition()+"");

        return view;
    }

    private void initSpinner() {
        String[] mAPreviewItems = getResources().getStringArray(R.array.SpinnerPreview);
        String[] mAutoTakeOffItems = getResources().getStringArray(R.array.SpinnerAutoTakeOff);
        setSpinnerAdapter(mAPhotoVideoPreviewSpr,mAPreviewItems);
        setSpinnerAdapter(mACloudPlatformModelSpr,mAutoTakeOffItems);
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
