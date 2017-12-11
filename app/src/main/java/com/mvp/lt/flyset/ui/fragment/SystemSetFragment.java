package com.mvp.lt.flyset.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**

 * 系统设置
 *
 * @author ${LiuTao}
 * @date 2017/12/11/011
 */

public class SystemSetFragment extends BaseFragment  {
    private static final String TAG = "SystemSetFragment";

    @BindView(R.id.s_server_ip_et)
    EditText mSServerIpEt;
    @BindView(R.id.s_communication_point_et)
    EditText mSCommunicationPointEt;
    @BindView(R.id.s_user_login_tv)
    TextView mSUserLoginTv;
    @BindView(R.id.s_flight_log_switch)
    Switch mSFlightLogSwitch;
    @BindView(R.id.s_user_secretKey_tv)
    TextView mSUserSecretKeyTv;
    @BindView(R.id.s_input_secretKey_ev)
    EditText mSInputSecretKeyEv;
    @BindView(R.id.s_test_secretKey_btn)
    Button mSTestSecretKeyBtn;
    @BindView(R.id.s_auto_synchro_switch)
    Switch mSAutoSynchroSwitch;
    @BindView(R.id.s_user_count_synchro_tv)
    TextView mSUserCountSynchroTv;
    Unbinder unbinder;

    public SystemSetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.system_set_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.s_server_ip_et, R.id.s_communication_point_et, R.id.s_flight_log_switch, R.id.s_user_secretKey_tv, R.id.s_input_secretKey_ev, R.id.s_test_secretKey_btn, R.id.s_auto_synchro_switch, R.id.s_user_count_synchro_tv, R.id.s_user_login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.s_server_ip_et:
                break;
            case R.id.s_communication_point_et:
                break;
            case R.id.s_flight_log_switch:
                break;
            case R.id.s_user_secretKey_tv:
                break;
            case R.id.s_input_secretKey_ev:
                break;
            case R.id.s_test_secretKey_btn:
                break;
            case R.id.s_auto_synchro_switch:
                break;
            case R.id.s_user_count_synchro_tv:
                break;
            case R.id.s_user_login_tv:
                try {
                    showLoginDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login_dialog, null);
        final EditText mUsername = (EditText) view.findViewById(R.id.id_txt_username);
        final EditText mPassword = (EditText) view.findViewById(R.id.id_txt_password);
        builder.setCancelable(false);
        builder.setView(view).setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.e(TAG, "帐号：" + mUsername.getText().toString() + ",  密码 :" + mPassword.getText().toString());
                                //保存账号密码
                            }
                        }).setNegativeButton("取消", null);
        builder.create();
        builder.show();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
