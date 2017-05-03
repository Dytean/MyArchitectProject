package com.dan.myarchitectproject.http;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Dan
 * Date: 2017/4/14 下午4:25
 * Description:
 * PackageName: com.dan.myarchitectproject.http
 * Copyright: 杭州安存网络科技有限公司
 **/

public abstract class HttpCallBack<T> {
    protected void onRequestBefore(Request request) {}
    public abstract void onSuccess(Call call, Response response,T t);
    public abstract void onFailed(Call call, Exception e);
    protected void onProgress(int progress) {}
}
