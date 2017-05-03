package com.dan.myarchitectproject.common.utils;

import android.util.Log;

import com.dan.myarchitectproject.common.Constants;

/**
 * Author: Dan
 * Date: 2017/4/10 下午5:02
 * Description:
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class LogUtils {
    private static String generateTag() {
        StackTraceElement caller = (new Throwable()).getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        return tag;
    }

    public static void e(String str) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.e(tag, str);
        }
    }

    public static void e(String str, Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.e(tag, str, e);
        }
    }

    public static void i(String str) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.i(tag, str);
        }
    }

    public static void i(String str, Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.i(tag, str, e);
        }
    }

    public static void d(String str) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.d(tag, str);
        }
    }

    public static void d(String str, Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.d(tag, str, e);
        }
    }

    public static void v(String str) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.v(tag, str);
        }
    }

    public static void v(String str, Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.v(tag, str, e);
        }
    }

    public static void w(Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(e.toString())) {
            String tag = generateTag();
            Log.w(tag, e);
        }
    }

    public static void w(String str) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.w(tag, str);
        }
    }

    public static void w(String str, Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.w(tag, str, e);
        }
    }

    public static void wtf(Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(e.toString())) {
            String tag = generateTag();
            Log.wtf(tag, e);
        }
    }

    public static void wtf(String str) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.wtf(tag, str);
        }
    }

    public static void wtf(String str, Throwable e) {
        if (Constants.isDebug && !StringUtils.isEmpty(str)) {
            String tag = generateTag();
            Log.wtf(tag, str, e);
        }
    }
}
