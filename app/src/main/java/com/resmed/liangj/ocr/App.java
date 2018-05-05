package com.resmed.liangj.ocr;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.resmed.liangj.ocr.app.AppConfig;
import com.resmed.liangj.ocr.greendao.DaoMaster;
import com.resmed.liangj.ocr.greendao.DaoSession;
import com.resmed.liangj.ocr.util.rx.RxTool;
import com.vise.log.ViseLog;
import com.vise.log.inner.ConsoleTree;
import com.vise.log.inner.FileTree;
import com.vise.log.inner.LogcatTree;
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
        initGreenDao();// 初始化数据库
        if (AppConfig.ISDEBUG) {// 测试时候打开
            EasyHttp.getInstance().debug("EasyHttp", true);
            ViseLog.getLogConfig()
                    .configAllowLog(true)//是否输出日志
                    .configShowBorders(true)//是否排版显示
                    .configTagPrefix("MyLog")//设置标签前缀
                    .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                    .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE;
            ViseLog.plant(new LogcatTree());
            //ViseLog.plant(new FileTree(this, "Log"));// 将日志写到sd卡

        }
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
