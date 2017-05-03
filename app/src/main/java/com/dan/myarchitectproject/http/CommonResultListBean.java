package com.dan.myarchitectproject.http;

import java.util.List;

/**
 * Author: Dan
 * Date: 2017/4/6 下午4:49
 * Description:
 * PackageName: com.dan.myarchitectproject.http
 * Copyright: 杭州安存网络科技有限公司
 **/

public class CommonResultListBean<T> {
    private String code;
    private List<T> result;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
