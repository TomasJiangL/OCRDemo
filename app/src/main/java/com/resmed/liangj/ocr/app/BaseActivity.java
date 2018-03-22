package com.resmed.liangj.ocr.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by LiangJ on 12/03/2018.
 */

public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private Context mContext;
    private Activity mActivity;
    public EventBus controlBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        controlBus = new EventBus();
        controlBus.register(this);
    }

    public Context getCurrentContext() {
        return mContext;
    }

    public Context getCurrentActivity() {
        return mActivity;
    }

    public void openNewActivity(Bundle bundle, Class<?> cls) {
        if (mContext == null) return;
        Intent i = new Intent();
        i.setClass(mContext, cls);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivity(i, bundle);
    }

    @Override
    protected void onDestroy() {
        mContext = null;
        mActivity = null;
        controlBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    }
}
