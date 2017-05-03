package com.dan.myarchitectproject.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Author: Dan
 * Date: 2017/4/10 上午10:07
 * Description:
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class SystemUtils {
    /**
     * 验证联网状态
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (null == info || !info.isConnected())
                return false;
        }
        return true;
    }

    /**
     * 获取sim卡状态
     * @param context
     * @return
     */
    public static boolean getSimStates(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int state = telephonyManager.getSimState();
        if (TelephonyManager.SIM_STATE_ABSENT == state) {
            return false;
        }
        return true;
    }

    /**
     * 获取应用程序版本号
     * @param ctx
     * @return
     */
    public static String getAppVersionName(Context ctx) {
        final String pkgName = ctx.getPackageName();
        try {
            PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(pkgName, 0);
            return pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取meta data里的数据
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaData(Context context, String metaKey) {
        String retStr = "";
        try {
            PackageManager packageInfo = context.getPackageManager();
            String packageName = context.getPackageName();
            retStr = packageInfo.getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA).metaData
                    .getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    /**
     * 获取imei号
     * @param context
     * @return
     */
    public static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        if (null == imei || "".equals(imei))
            return "0";
        else
            return imei;
    }
}
