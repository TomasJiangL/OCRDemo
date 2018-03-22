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

public class MultiplePermissionActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_permission);
        storageAndContactsTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static final String[] STORAGE_AND_CONTACTS =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS};

    private static final int RC_STORAGE_CONTACTS_PERM = 124;
    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, STORAGE_AND_CONTACTS);
    }

    public void storageAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            // Have permissions, do the thing!

        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_storage_contacts),
                    RC_STORAGE_CONTACTS_PERM,
                    STORAGE_AND_CONTACTS);
        }
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
        Log.d(Logger.LOGTAG, "onPermissionsDenied:" + requestCode + ":" + perms.size() + "拒绝的权限是 : " + Logger.getPermissionString(perms));
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        Toast.makeText(getCurrentContext(), "请开启SD卡和联系人权限后再试", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
