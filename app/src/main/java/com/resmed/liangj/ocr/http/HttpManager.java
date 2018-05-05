package com.resmed.liangj.ocr.http;

/**
 * Created by LiangJ on 29/03/2018.
 */


import com.zhouyou.http.request.GetRequest;
import com.zhouyou.http.request.PostRequest;

/**
 * <p>描述：根据自定义请求写一个请求的管理类方便调用</p>
 * 作者： zhouyou<br>
 * 日期： 2017/7/7 10:31 <br>
 * 版本： v1.0<br>
 */
public class HttpManager {
    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return new CustomGetRequest(url);
    }

    /**
     * post请求
     */
    public static PostRequest post(String url) {
        return new CustomPostRequest(url);
    }
}
