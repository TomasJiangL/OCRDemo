package com.resmed.liangj.ocr;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.resmed.liangj.ocr.app.BaseActivity;
import com.resmed.liangj.ocr.util.Logger;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by LiangJ on 12/03/2018.
 */

public class NeedCameraActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_camera);
        checkCameraPermission();// 检测是否有相机权限
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static final int RC_CAMERA_PERM = 123;

    public void checkCameraPermission() {
        if (hasCameraPermission()) {
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_camera),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA);
        }
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(Logger.LOGTAG, "onPermissionsGranted:" + requestCode + ":" + perms.size() + "同意的权限是 : " + Logger.getPermissionString(perms));
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(Logger.LOGTAG, "onPermissionsDenied:" + requestCode + ":" + perms.size() + " 拒绝的权限是 : " + Logger.getPermissionString(perms));
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        Toast.makeText(getCurrentContext(), "请开启相机权限后再试", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).setTitle("需要的权限").setRationale("APP需要的权限已被您禁止，请您手动开启权限").build().show();
        }
    }

}
