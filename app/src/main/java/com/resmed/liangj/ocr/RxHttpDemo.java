package com.resmed.liangj.ocr;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.resmed.liangj.ocr.app.BaseActivity;
import com.resmed.liangj.ocr.bean.httpbean.AqiBean;
import com.resmed.liangj.ocr.bean.httpbean.MyApiResult;
import com.resmed.liangj.ocr.bean.httpbean.TrainInfo;
import com.resmed.liangj.ocr.bean.httpbean.TvProgram;
import com.resmed.liangj.ocr.bean.httpbean.TvProgram.Program;
import com.resmed.liangj.ocr.http.HttpManager;
import com.resmed.liangj.ocr.util.rx.RxDataTool;
import com.vise.log.ViseLog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.util.List;

/**
 * Created by LiangJ on 27/03/2018.
 */

public class RxHttpDemo extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_http_demo);
    }

    public void getHttp(View view) {
        HttpParams params = new HttpParams();
        params.put("start", "杭州");
        params.put("end", "北京");
        params.put("ishigh", "0");
        params.put("appkey", "51fb98a395cdacaf");

        EasyHttp.get("/train/station2s")
                .baseUrl("http://api.jisuapi.com")
                .params(params)//设置参数
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String response) {

                    }
                });
    }


    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(getCurrentContext());
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    public void postHttp(View view) {
        getAQIInfo();
    }

    private void getAQIInfo() {
        HttpManager.post("/aqi/query")
                .baseUrl("http://api.jisuapi.com")
                .timeStamp(true)
                .params("city", "苏州")
                .params("appkey", "51fb98a395cdacaf")
                .execute(new ProgressDialogCallBack<AqiBean>(mProgressDialog, true, true) {//这么实现是不是没有代理了
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);

                    }

                    @Override
                    public void onSuccess(AqiBean bean) {
                        ViseLog.d(bean.getAqiinfo());
                    }
                });
    }

    private void getTrainInfo() {
        EasyHttp.post("/train/station2s")
                .baseUrl("http://api.jisuapi.com")
                .timeStamp(true)
                .params("start", "杭州")
                .params("end", "北京")
                .params("ishigh", "0")
                .params("appkey", "51fb98a395cdacaf")
                .execute(new CallBackProxy<MyApiResult<List<TrainInfo>>, List<TrainInfo>>(new ProgressDialogCallBack<List<TrainInfo>>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                    }

                    @Override
                    public void onSuccess(List<TrainInfo> response) {
                        if (response != null && response.size() > 0) {
                            for (TrainInfo item : response) {
                                ViseLog.d(item.getTrainno());
                            }
                        }
                    }
                }) {
                });
    }

    private void getTVInfo() {
        EasyHttp.post("/tv/query")
                .baseUrl("http://api.jisuapi.com")
                .timeStamp(true)
                .params("tvid", "435")
                .params("date", "2018-03-27")
                .params("appkey", "51fb98a395cdacaf")
                .execute(new CallBackProxy<MyApiResult<TvProgram>, TvProgram>(new ProgressDialogCallBack<TvProgram>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                    }

                    @Override
                    public void onSuccess(TvProgram response) {
                        if (RxDataTool.isEmpty(response)) return;
                        List<Program> programList = response.getProgram();
                        if (RxDataTool.isEmpty(programList)) return;
                        ViseLog.d(programList);
                    }
                }) {
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
