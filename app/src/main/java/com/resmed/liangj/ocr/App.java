package com.resmed.liangj.ocr;

import android.app.Application;

import com.resmed.liangj.ocr.greendao.DaoMaster;
import com.resmed.liangj.ocr.greendao.DaoSession;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

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

    @Override
    public void onCreate() {
        super.onCreate();
        //initLoadingDialog();
        initDB();
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "devices-db-encrypted" : "devices-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    private void initLoadingDialog() {//加载dialog初始化
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
