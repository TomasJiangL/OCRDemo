package com.resmed.liangj.ocr.bean.httpbean;

import com.zhouyou.http.model.ApiResult;

/**
 * Created by LiangJ on 27/03/2018.
 */

public class MyApiResult<T> extends ApiResult<T> {
    int status;
    T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public T getData() {
        return result;
    }

    @Override
    public void setData(T data) {
        result = data;
    }

    @Override
    public boolean isOk() {
        return status == 0;
    }
}
