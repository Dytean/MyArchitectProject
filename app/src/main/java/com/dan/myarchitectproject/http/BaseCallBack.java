package com.dan.myarchitectproject.http;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Dan
 * Date: 2017/4/6 下午3:54
 * Description:
 * PackageName: com.dan.myarchitectproject.http
 * Copyright: 杭州安存网络科技有限公司
 **/

public abstract class BaseCallBack<T> {
    public Type mType;
    static Type getSuperclassTypeParameter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class){
            throw new RuntimeException("Missing type parameter");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public BaseCallBack(){
        mType = getSuperclassTypeParameter(getClass());
    }

    protected void onRequestBefore(Request request) {}
    protected abstract void onFailure(Call call, Exception e);
    protected abstract void onSuccess(Call call, Response response,T t);
    protected void onProgress(int progress) {}
}
