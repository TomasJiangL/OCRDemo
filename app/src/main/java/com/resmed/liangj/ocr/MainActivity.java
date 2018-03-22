package com.resmed.liangj.ocr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.liyu.sqlitetoexcel.SQLiteToExcel;
import com.resmed.liangj.ocr.adapter.DeviceAdapter;
import com.resmed.liangj.ocr.app.BaseActivity;
import com.resmed.liangj.ocr.bean.Device;
import com.resmed.liangj.ocr.bean.OrcBean;
import com.resmed.liangj.ocr.bean.OrcBean.WordsResult;
import com.resmed.liangj.ocr.bean.eventbus.DataBaseEvent;
import com.resmed.liangj.ocr.bean.eventbus.OcrTokenEvent;
import com.resmed.liangj.ocr.greendao.DaoSession;
import com.resmed.liangj.ocr.greendao.DeviceDao;
import com.resmed.liangj.ocr.util.FileUtil;
import com.resmed.liangj.ocr.util.GsonUtil;
import com.resmed.liangj.ocr.util.Logger;
import com.resmed.liangj.ocr.util.RecognizeService;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
    private static final int REQUEST_CODE_ACCURATE_BASIC = 107;
    private EditText editMachine;
    private EditText editBox;
    private EditText editSim;
    private View addButton;
    private DaoSession daoSession;
    private DeviceDao deviceDao;
    private Query<Device> notesQuery;
    private DeviceAdapter deviceAdapter;
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    private int select = 1;
    private LoadingDialog exportLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        alertDialog = new AlertDialog.Builder(this);
        controlBus.post(new OcrTokenEvent());
        // get the device DAO
        daoSession = ((App) getApplication()).getDaoSession();
        deviceDao = daoSession.getDeviceDao();
        setUpViews();
        queryDeviceData();
    }

    // 查询数据
    private void queryDeviceData() {
        daoSession.startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                Log.d(Logger.LOGTAG, "读取数据库 ThreadName: " + Thread.currentThread().getName() + "  #  ThreadId: " + Thread.currentThread().getId());
                // query all notes, sorted by their date
                notesQuery = deviceDao.queryBuilder().orderAsc(DeviceDao.Properties.Date).build();
                List<Device> devices = notesQuery.list();
                DataBaseEvent data = new DataBaseEvent(devices);
                controlBus.post(data);
            }
        });
    }

    // 插入数据库
    public void insertDevice() {
        final String machineText = editMachine.getText().toString();
        final String boxText = editBox.getText().toString();
        final String simText = editSim.getText().toString();
        if (TextUtils.isEmpty(machineText)) {
            Toast.makeText(getCurrentContext(), "机器码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(boxText)) {
            Toast.makeText(getCurrentContext(), "盒子码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(simText)) {
            Toast.makeText(getCurrentContext(), "sim码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 添加后重置
        editMachine.setText("");
        editBox.setText("");
        editSim.setText("");
        daoSession.startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                Log.d(Logger.LOGTAG, "插入数据库 ThreadName: " + Thread.currentThread().getName() + "  #  ThreadId: " + Thread.currentThread().getId());
                Date date = new Date();
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
                Device device = new Device();
                device.setMachineCode(machineText);
                device.setBoxCode(boxText);
                device.setSimCode(simText);
                device.setDate(date);
                device.setFormateTime(df.format(date));
                deviceDao.insert(device);
                queryDeviceData();
                Log.d("DaoExample", "Inserted new device, ID: " + device.getId());
            }
        });
    }

    // 删除数据库
    DeviceAdapter.DeviceClickListener deviceClickListener = new DeviceAdapter.DeviceClickListener() {
        @Override
        public void onDeviceClick(int position) {
            Device device = deviceAdapter.getDevice(position);
            Log.d(Logger.LOGTAG, device.toString());
//            Long deviceId = device.getId();
//            deviceDao.deleteByKey(deviceId);
//            Log.d("DaoExample", "Deleted note, ID: " + deviceId);
//            updateDevices();
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUIView(DataBaseEvent event) {//更新UI操作
        Log.d(Logger.LOGTAG, "更新UI  ThreadName: " + Thread.currentThread().getName());
        if (event.getDevices() != null && event.getDevices().size() > 0) {
            deviceAdapter.setDevices(event.getDevices());
        }
    }

    // 添加按钮
    public void onAddButtonClick(View view) {
        insertDevice();
    }

    // 导出按钮
    public void onExportButtonClick(View view) {
        exportSqLiteToExcel();
    }

    LoadingDialog.Speed speed = LoadingDialog.Speed.SPEED_TWO;
    private int repeatTime = 0;
    private boolean intercept_back_event = false;

    // 导出数据库到Excel
    public void exportSqLiteToExcel() {
        String databasePath = this.getDatabasePath("devices-db").getAbsolutePath();
        Log.d(Logger.LOGTAG, "app数据库路径: " + databasePath);
        new SQLiteToExcel.Builder(this)
                .setDataBase(databasePath)
                .start(new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                        Log.d(Logger.LOGTAG, "ExportListener onStart");
                        exportLoadingDialog = new LoadingDialog(getCurrentContext());
                        exportLoadingDialog.setLoadingText("导出中")
                                .setSuccessText("导出成功")
                                .setFailedText("导出失败")
                                .setInterceptBack(intercept_back_event)
                                .setLoadSpeed(speed)
                                .closeSuccessAnim()
                                .setRepeatCount(repeatTime)
                                .show();
                    }

                    @Override
                    public void onCompleted(String filePath) {
                        Log.d(Logger.LOGTAG, "ExportListener onCompleted filePath : " + filePath);
                        exportLoadingDialog.loadSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(Logger.LOGTAG, "ExportListener onError" + e.getMessage());
                        exportLoadingDialog.loadFailed();
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void initAccessToken(OcrTokenEvent event) {
        Log.d(Logger.LOGTAG, "初始化Token ThreadName: " + Thread.currentThread().getName() + "  #  ThreadId: " + Thread.currentThread().getId());
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
                Log.d(Logger.LOGTAG, "初始化成功: token==>" + token);
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    // type = 0 通用文字识别  ype = 1 通用文字识别(高精度版)
    public void getOcrResult(int type) {
        if (!hasGotToken) {
            Toast.makeText(getCurrentContext(), "读取配置文件有误,请重启App", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
        switch (type) {
            case 0:
                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
                break;
            case 1:
                startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                        }
                    });
        }

        // 识别成功回调，通用文字识别（高精度版）
        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurateBasic(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                        }
                    });
        }

    }

    private void infoPopText(final String result) {
        Log.d("android_ocr", result);
        OrcBean orc = GsonUtil.GsonToBean(result, OrcBean.class);
        if (orc != null) {
            List<WordsResult> words = orc.getWords_result();
            StringBuffer sb = new StringBuffer();
            if (words != null && words.size() > 0) {
                for (WordsResult word : words) {
                    Log.d("android_ocr", "word: " + word.getWords());
                    sb.append(word.getWords());
                }
                switch (select) {
                    case 1:
                        editMachine.setText(sb.toString());
                        break;
                    case 2:
                        editBox.setText(sb.toString());
                        break;
                    case 3:
                        editSim.setText(sb.toString());
                        break;
                }
            } else {
                Toast.makeText(MainActivity.this, "ORC识别失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deviceAdapter = new DeviceAdapter(deviceClickListener);
        recyclerView.setAdapter(deviceAdapter);

        addButton = findViewById(R.id.buttonAdd);
        //noinspection ConstantConditions
        addButton.setEnabled(true);

        editMachine = (EditText) findViewById(R.id.editMachineCode);
        editBox = (EditText) findViewById(R.id.editBoxCode);
        editSim = (EditText) findViewById(R.id.editSimCode);
        findViewById(R.id.machineCodeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 1;
                getOcrResult(1);
            }
        });
        findViewById(R.id.boxCodeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 2;
                getOcrResult(1);
            }
        });
        findViewById(R.id.simCodeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 3;
                getOcrResult(1);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance().release();
    }

}
