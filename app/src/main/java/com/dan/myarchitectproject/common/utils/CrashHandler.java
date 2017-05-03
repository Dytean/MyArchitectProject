package com.dan.myarchitectproject.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Dan
 * Date: 2017/4/7 下午11:53
 * Description:
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private Context mContext;
    private static CrashHandler sCrashHandler = new CrashHandler();
    private CrashHandler(){}
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private String path = Environment.getExternalStorageDirectory()+"/CrashLog/";
    public static CrashHandler getInstance(){
        return sCrashHandler;
    }

    public void init(Context context){
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdir();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String curTime = sdf.format(new Date(System.currentTimeMillis()));
            File file = new File(path+"crash"+curTime+".log");
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                pw.println(curTime);
                dumpInfo(pw);
                pw.println();
                e.printStackTrace(pw);
                CloseableUtils.close(pw);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        e.printStackTrace();
        if (mUncaughtExceptionHandler != null)
            mUncaughtExceptionHandler.uncaughtException(t,e);
        else
            Process.killProcess(Process.myPid());
    }

    private void dumpInfo(PrintWriter pw) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
            pw.print("App Version:");
            pw.print(pi.versionName);
            pw.print("_");
            pw.println(pi.versionCode);

            pw.print("OS Version");
            pw.print(Build.VERSION.RELEASE);
            pw.print("_");
            pw.println(Build.VERSION.SDK_INT);

            pw.print("Vendor:");
            pw.println(Build.MANUFACTURER);

            pw.print("Model:");
            pw.println(Build.MODEL);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
