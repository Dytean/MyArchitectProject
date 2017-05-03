package com.dan.myarchitectproject.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author: Dan
 * Date: 2017/4/7 下午11:49
 * Description:
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class CloseableUtils {
    public static void close(Closeable closeable){
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
