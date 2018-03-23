package com.resmed.liangj.ocr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.resmed.liangj.ocr.app.BaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by LiangJ on 12/03/2018.
 */

public class MultiplePermissionActivity extends BaseActivity {
    private RxPermissions rxPermissions;
    private boolean showPermissionDialog = false;
    private String[] permissionString = new String[]{Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    public void storageAndContactsTask() {
        rxPermissions = new RxPermissions(this);
        // requestEachCombined 多个权限请求的话accept permission.granted 也只回调一次
        rxPermissions.requestEachCombined(permissionString).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    // permission.name is granted !
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // Denied permission without ask never again
                    if (!showPermissionDialog) showMissingPermissionDialog();
                } else {
                    // Denied permission with ask never again  Need to go to the settings
                    if (!showPermissionDialog) showMissingPermissionDialog();
                }
            }
        });
    }

    // 显示缺失权限提示
    public void showMissingPermissionDialog() {
        showPermissionDialog = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.basic_help);
        builder.setMessage(R.string.basic_string_help_text);
        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.basic_quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.basic_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    // 启动应用的设置(去本应用的设置界面)
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

}
