package com.dan.myarchitectproject.http;

import com.dan.myarchitectproject.common.Constants;

/**
 * Author: Dan
 * Date: 2017/4/6 下午5:59
 * Description:
 * PackageName: com.dan.myarchitectproject.http
 * Copyright: 杭州安存网络科技有限公司
 **/

public class HttpConstants {
    /**
     * 服务端新接口URL
     */
    public final static String HTTP_SERVIER_URL = Constants.isDebug ?"http://115.29.246.94":"http://jz.ancun.com";

    /**
     * 文件上传URL
     */
    public final static String HTTP_FILE_UPLOAD_URL = Constants.isDebug ? "http://115.29.246.94":"http://jz.ancun.com";
}
