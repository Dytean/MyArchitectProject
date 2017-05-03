package com.dan.myarchitectproject.http;

/**
 * Author: Dan
 * Date: 2017/4/6 下午4:46
 * Description:
 * PackageName: com.dan.myarchitectproject.http
 * Copyright: 杭州安存网络科技有限公司
 **/
//数据返回格式
//{
//  "code": 200,
//  "result": {},
//  "message": "登录成功"
//}
public class CommonResultBean<T> {
    private String code;
    private T result;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
