package com.dan.myarchitectproject.common.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Author: Dan
 * Date: 2017/4/10 上午10:33
 * Description:
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class ToastUtils {
    /**
     * 吐司
     * @param context
     * @param str
     */
    public static void showToast(Context context, String str) {
        Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 线程安全的吐司
     * @param context
     * @param view
     * @param str
     */
    public static void showToastOnUi(final Context context, View view, final String str) {
        view.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, str);
            }
        });
    }
}
