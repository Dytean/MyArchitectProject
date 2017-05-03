package com.dan.myarchitectproject.http;

import java.io.File;
import java.util.Map;

/**
 * Author: Dan
 * Date: 2017/4/6 下午6:53
 * Description:
 * PackageName: com.dan.myarchitectproject.http
 * Copyright: 杭州安存网络科技有限公司
 **/

public class HttpUtils {
    private static HttpUtils sHttpUtils;
    private HttpUtils(){

    }

    public static HttpUtils getInstance(){
        if (null == sHttpUtils){
            synchronized (HttpUtils.class){
                if (null == sHttpUtils){
                    sHttpUtils = new HttpUtils();
                }
            }
        }
        return sHttpUtils;
    }

    /**
     * get请求
     * @param params
     * @param callBack
     */
    public void doGet(Map<String,String> params, BaseCallBack<?> callBack){
        OkHttpManager.getInstance().getRequest(params,callBack);
    }

    /**
     * post请求
     * @param params
     * @param callBack
     */
    public void doPost(Map<String,String> params, BaseCallBack<?> callBack){
        OkHttpManager.getInstance().postRequest(params,callBack);
    }

    /**
     * 上传文件
     * @param params
     * @param files
     * @param callBack
     */
    public void doUpload(Map<String,String> params, Map<String,File> files, BaseCallBack<?> callBack){
        OkHttpManager.getInstance().postUploadFiles(params,files,callBack);
    }

    /**
     * 下载文件
     * @param url
     * @param destFileDir
     * @param callBack
     */
    public void doDownload(String url, String destFileDir, BaseCallBack<?> callBack){
        OkHttpManager.getInstance().asynDownloadFile(url,destFileDir,callBack);
    }
}
