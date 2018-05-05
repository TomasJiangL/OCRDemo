package com.resmed.liangj.ocr.http;

/**
 * Created by LiangJ on 29/03/2018.
 */

import com.resmed.liangj.ocr.bean.httpbean.MyApiResult;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.request.PostRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * <p>描述：扩展PostRequest请求，解决自定义ApiResult重复写代理的问题</p>
 * 1.用test2包中实例举例,假如你的自定义ApiResult是TestApiResult2<br>
 * 作者： zhouyou<br>
 * 日期： 2017/7/7 10:23 <br>
 * 版本： v1.0<br>
 */
public class CustomPostRequest extends PostRequest {
    public CustomPostRequest(String url) {
        super(url);
    }

    /*public PostRequest upJson(Object objBean) {//若果你的是obj,你想很容易转json,可以自己新增一个方法obj转json的
        return super.upJson(StringUtil.getJson(objBean));
    }*/

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
