package com.resmed.liangj.ocr.bean.httpbean;

import com.zhouyou.http.model.ApiResult;

public class CommonApiResult <T> extends ApiResult<T> {
    Boolean error;
    T results;

    public Boolean getStatus() {
        return error;
    }

    public void setStatus(Boolean status) {
        this.error = status;
    }

    @Override
    public T getData() {
        return results;
    }

    @Override
    public void setData(T data) {
        results = data;
    }

    @Override
    public boolean isOk() {
        return error == false;
    }
}
