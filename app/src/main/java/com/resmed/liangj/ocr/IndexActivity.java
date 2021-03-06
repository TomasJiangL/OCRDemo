package com.resmed.liangj.ocr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.resmed.liangj.ocr.app.BaseActivity;

/**
 * Created by LiangJ on 12/03/2018.
 */

public class IndexActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    public void siglePermission(View view) {
        openNewActivity(null, NeedCameraActivity.class);
    }

    public void multiplePermission(View view) {
        openNewActivity(null, MultiplePermissionActivity.class);
    }

    public void greenDatabase(View view) {
        openNewActivity(null, MainActivity.class);
    }

    public void picPicker(View view) {
        openNewActivity(null, PickerDemo.class);
    }

    public void httpRequest(View view) {
        openNewActivity(null, RxHttpDemo.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
