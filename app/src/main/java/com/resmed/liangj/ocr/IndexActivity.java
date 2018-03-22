package com.resmed.liangj.ocr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.resmed.liangj.ocr.app.BaseActivity;

import java.util.List;

/**
 * Created by LiangJ on 12/03/2018.
 */

public class IndexActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    public void siglePermission(View view){
        openNewActivity(null, NeedCameraActivity.class);
    }

    public void multiplePermission(View view){
        openNewActivity(null, MultiplePermissionActivity.class);
    }

    public void greenDatabase(View view){
        openNewActivity(null, MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
    }

}
