package com.mvp.lt.flyset;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mvp.lt.flyset.ui.activity.Setting2Activity;
import com.mvp.lt.flyset.ui.activity.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LiuTao
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.albumAppButton)
    Button mAlbumAppButton;
    @BindView(R.id.cameraAppButton)
    Button mCameraAppButton;
    @BindView(R.id.imageView)
    ImageView mImageView;
    private final int ACTION_CAMERA_REQUEST_CODE = 100;
    private final int ACTION_ALBUM_REQUEST_CODE = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void clickHorizontal(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }


    public void clickVertical(View view) {
        startActivity(new Intent(this, Setting2Activity.class));
    }

    @OnClick({R.id.albumAppButton, R.id.cameraAppButton, R.id.imageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.albumAppButton:
                takeImageFromAlbumWithIntent();

                break;
            case R.id.cameraAppButton:
                takeImageFromCameraWithIntent();
                break;
            case R.id.imageView:
                break;
        }
    }

    private void takeImageFromAlbumWithIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, ACTION_ALBUM_REQUEST_CODE);
    }

    private void takeImageFromCameraWithIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, ACTION_CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_ALBUM_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {


                }
                break;
            case ACTION_CAMERA_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {

                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayImage(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }
}
