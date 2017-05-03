package com.dan.myarchitectproject.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: Dan
 * Date: 2017/4/6 下午3:45
 * Description:
 * PackageName: com.dan.myarchitectproject.http
 * Copyright: 杭州安存网络科技有限公司
 **/

public class OkHttpManager {
    private static OkHttpManager sOkHttpManager;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;

    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式（OkHttp官方建议如此操作）
     *
     * @return
     */
    public static OkHttpManager getInstance() {
        if (null == sOkHttpManager) {
            sOkHttpManager = new OkHttpManager();
        }
        return sOkHttpManager;
    }

    /**
     * get请求
     *
     * @param params   参数
     * @param callBack 回调
     */
    public void getRequest(Map<String, String> params, final BaseCallBack callBack) {
        String requestUrl = null;
        if (params != null && !params.isEmpty() && params.size() != 0) {
            String param = urlEncode(params);
            requestUrl = HttpConstants.HTTP_SERVIER_URL + "?" + param;
        } else {
            requestUrl = HttpConstants.HTTP_SERVIER_URL;
        }
        Request request = buildRequest(requestUrl, null, HttpMethodType.GET);
        doRequest(request, callBack);
    }

    /**
     * post请求
     *
     * @param params
     * @param callBack
     */
    public void postRequest(Map<String, String> params, final BaseCallBack callBack) {
        Request request = buildRequest(HttpConstants.HTTP_SERVIER_URL, params, HttpMethodType.POST);
        doRequest(request, callBack);
    }

    /**
     * 上传文件
     *
     * @param params
     * @param files
     * @param callback
     */
    public void postUploadFiles(Map<String, String> params, Map<String, File> files, final BaseCallBack callback) {
        try {
            postAsyn(params, files, callback);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //文件上传请求
    private void postAsyn(Map<String, String> params, Map<String, File> files, BaseCallBack callback) throws IOException {
        Request request = buildMultipartFormRequest(params, files);
        doRequest(request, callback);
    }

    /**
     * 下载文件
     *
     * @param url         文件地址
     * @param destFileDir 存储文件目录
     * @param callBack    回调
     */
    public void asynDownloadFile(final String url, final String destFileDir, final BaseCallBack callBack) {
        final Request request = buildRequest(url, null, HttpMethodType.GET);
        callBack.onRequestBefore(request);  //提示加载框
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailed(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[1024 * 2];
                final long fileLength = response.body().contentLength();
                int len = 0;
                long readLength = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        readLength += len;
                        int curProgress = (int) (((float) readLength / fileLength) * 100);
                        callBackProgress(callBack, curProgress);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    callBackSuccess(callBack, call, response, file.getAbsolutePath());
                } catch (IOException e) {
                    callBackFailed(callBack, call, e);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });


    }

    //构造上传文件 Request
    private Request buildMultipartFormRequest(Map<String, String> params, Map<String, File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null && !params.isEmpty() && params.size() != 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, entry.getValue()));
                }
            }
        }
        if (files != null && !files.isEmpty() && files.size() != 0) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    RequestBody fileBody = null;
                    fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), entry.getValue());
                    builder.addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + entry.getValue().getName() + "\""),
                            fileBody);
                }
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(HttpConstants.HTTP_FILE_UPLOAD_URL)
                .post(requestBody)
                .build();
    }

    //Activity页面所有的请求以Activity对象作为tag，可以在onDestory()里面统一取消,this
    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    //去进行网络 异步 请求
    private void doRequest(Request request, final BaseCallBack callBack) {
        callBack.onRequestBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailed(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {

                    if (callBack.mType == String.class) {
                        callBackSuccess(callBack, call, response, result);
                    } else {
                        try {
                            Object object = mGson.fromJson(result, callBack.mType);//自动转化为 泛型对象
                            callBackSuccess(callBack, call, response, object);
                        } catch (JsonParseException e) {
                            //json解析错误时调用
                            callBackFailed(callBack, call, e);
                        }

                    }
                } else {
                    callBackFailed(callBack, call, null);
                }

            }

        });


    }

    //创建 Request对象
    private Request buildRequest(String url, Map<String, String> params, HttpMethodType methodType) {

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody requestBody = buildFormData(params);
            builder.post(requestBody);
        }
        return builder.build();
    }

    //构建请求所需的参数表单
    private RequestBody buildFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("platform", "android");
//        builder.add("version", "1.0");
//        builder.add("key", "123456");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private void callBackSuccess(final BaseCallBack callBack, final Call call, final Response response, final Object object) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(call, response, object);
            }
        });

    }

    private void callBackFailed(final BaseCallBack callBack, final Call call, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });
    }

    private void callBackProgress(final BaseCallBack callBack, final int progress) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onProgress(progress);
            }
        });
    }

    /**
     * 拼接参数
     *
     * @param params
     * @return
     */
    private String urlEncode(Map<String, String> params) {
        StringBuffer sb2 = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                sb2.append(entry.getKey());
                sb2.append("=");
                try {
                    sb2.append(URLEncoder.encode(entry.getValue(), "utf-8").toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb2.append("&");
            }
        }
        String s = "";
        if (sb2.length() != 0) {
            s = sb2.substring(0, sb2.length() - 1);
        }
        return s;
    }

    enum HttpMethodType {
        GET, POST
    }
}
