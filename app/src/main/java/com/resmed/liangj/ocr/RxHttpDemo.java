package com.resmed.liangj.ocr;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.resmed.liangj.ocr.app.BaseActivity;
import com.resmed.liangj.ocr.bean.httpbean.AqiBean;
import com.resmed.liangj.ocr.bean.httpbean.MyApiResult;
import com.resmed.liangj.ocr.bean.httpbean.TrainInfo;
import com.resmed.liangj.ocr.bean.httpbean.TvProgram;
import com.resmed.liangj.ocr.util.Logger;
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
                        Log.d(Logger.LOGTAG, e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        Log.d(Logger.LOGTAG, response);
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

    private void getTrainInfo(){
        EasyHttp.post("/train/station2s")
                .baseUrl("http://api.jisuapi.com")
                .timeStamp(true)
                .params("start", "杭州")
                .params("end", "北京")
                .params("ishigh", "0")
                .params("appkey", "51fb98a395cdacaf")
                .execute(new CallBackProxy<MyApiResult<List<TrainInfo>>, List<TrainInfo>>(new ProgressDialogCallBack<List<TrainInfo>>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                    }

                    @Override
                    public void onSuccess(List<TrainInfo> response) {
                        if (response != null && response.size() > 0) {
                            for (TrainInfo item : response) {
                                Log.d(Logger.LOGTAG, item.getTrainno());
                            }
                        }
                    }
                }) {
                });
    }

    private void getTVInfo(){
        EasyHttp.post("/tv/query")
                .baseUrl("http://api.jisuapi.com")
                .timeStamp(true)
                .params("tvid", "435")
                .params("date", "2018-03-27")
                .params("appkey", "51fb98a395cdacaf")
                .execute(new CallBackProxy<MyApiResult<TvProgram>, TvProgram>(new ProgressDialogCallBack<TvProgram>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                    }

                    @Override
                    public void onSuccess(TvProgram response) {
                        Log.d(Logger.LOGTAG, response.toString());
                        List<TvProgram.Program> programList = response.getProgram();
                    }
                }) {
                });
    }

    private void getAQIInfo(){
        EasyHttp.post("/aqi/query")
                .baseUrl("http://api.jisuapi.com")
                .timeStamp(true)
                .params("city", "苏州")
                .params("appkey", "51fb98a395cdacaf")
                .execute(new CallBackProxy<MyApiResult<AqiBean>, AqiBean>(new ProgressDialogCallBack<AqiBean>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                    }

                    @Override
                    public void onSuccess(AqiBean response) {
                        Log.d(Logger.LOGTAG, response.toString());
                    }
                }) {
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
