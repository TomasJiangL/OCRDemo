package com.resmed.liangj.ocr.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhouyou.http.EasyHttp;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/**
 * Created by LiangJ on 12/03/2018.
 */

public class BaseActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    public EventBus controlBus;
    public Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
    }

    public void addEventBus() {
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
        if (controlBus != null) {
            controlBus.unregister(this);
        }
        if (mDisposable != null) {// 取消网络请求
            EasyHttp.cancelSubscription(mDisposable);
        }
        super.onDestroy();
    }

}
