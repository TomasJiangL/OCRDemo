package com.resmed.liangj.ocr.http;

/**
 * Created by LiangJ on 29/03/2018.
 */

import com.resmed.liangj.ocr.bean.httpbean.MyApiResult;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.request.GetRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * <p>描述：扩展GetResult请求，解决自定义ApiResult重复写代理的问题</p>
 * 1.用test2包中实例举例,假如你的自定义ApiResult是TestApiResult2<br>
 * 作者： zhouyou<br>
 * 日期： 2017/7/7 10:23 <br>
 * 版本： v1.0<br>
 */
public class CustomGetRequest extends GetRequest {
    public CustomGetRequest(String url) {
        super(url);
    }

    /**
     * 覆写execute方法指定，代理用TestApiResult2
     *
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T> Observable<T> execute(Type type) {
        return super.execute(new CallClazzProxy<MyApiResult<T>, T>(type) {
        });
    }

    @Override
    public <T> Observable<T> execute(Class<T> clazz) {
        return super.execute(new CallClazzProxy<MyApiResult<T>, T>(clazz) {
        });
    }

    @Override
    public <T> Disposable execute(CallBack<T> callBack) {
        return super.execute(new CallBackProxy<MyApiResult<T>, T>(callBack) {
        });
    }
}
