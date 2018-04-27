package com.resmed.liangj.ocr;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.resmed.liangj.ocr.greendao.DaoMaster;
import com.resmed.liangj.ocr.greendao.DaoSession;
import com.resmed.liangj.ocr.util.rx.RxTool;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zhouyou.http.EasyHttp;

import org.greenrobot.greendao.database.Database;

/**
 * Created by LiangJ on 9/03/2018.
 */

public class App extends Application {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    private static App app = null;

    public static App getApplication() {
        return app;
    }

    /**
     * 获取Application的Context
     **/
    public static Context getAppContext() {
        if (app == null)
            return null;
        return app.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        RxTool.init(getAppContext());
        EasyHttp.init(app);// 初始化网络请求的库
        EasyHttp.getInstance().debug("EasyHttp", true);
        initGreenDao();// 初始化数据库
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "devices-db-encrypted" : "devices-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public void initLoadingDialog() {//加载dialog初始化
        StyleManager s = new StyleManager();
        //在这里调用方法设置s的属性
        //code here...
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true);
        LoadingDialog.initStyle(s);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
