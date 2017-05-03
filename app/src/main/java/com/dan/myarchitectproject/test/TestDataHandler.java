package com.dan.myarchitectproject.test;

import android.content.Context;

import com.dan.myarchitectproject.http.BaseCallBack;
import com.dan.myarchitectproject.http.CommonResultBean;
import com.dan.myarchitectproject.http.HttpCallBack;
import com.dan.myarchitectproject.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Author: Dan
 * Date: 2017/4/14 下午4:30
 * Description:
 * PackageName: com.dan.myarchitectproject.test
 * Copyright: 杭州安存网络科技有限公司
 **/

public class TestDataHandler {
    private static TestDataHandler sTestDataHandler;

    private TestDataHandler() {

    }

    public static TestDataHandler getInstance() {
        if (null == sTestDataHandler) {
            synchronized (TestDataHandler.class) {
                if (null == sTestDataHandler) {
                    sTestDataHandler = new TestDataHandler();
                }
            }
        }
        return sTestDataHandler;
    }

    public void get(Context context, final HttpCallBack callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("code","1234");
        HttpUtils.getInstance().doGet(map, new BaseCallBack<CommonResultBean<String>>() {

            @Override
            protected void onFailure(Call call, Exception e) {
                callBack.onFailed(call, e);
            }

            @Override
            protected void onSuccess(Call call, Response response, CommonResultBean<String> stringCommonResultBean) {
                callBack.onSuccess(call, response, stringCommonResultBean);
            }
        });
    }
}
