package com.dan.myarchitectproject.common;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.dan.myarchitectproject.common.utils.CrashHandler;
import com.dan.myarchitectproject.db.dao.DaoMaster;
import com.dan.myarchitectproject.db.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.IOException;

/**
 * Author: Dan
 * Date: 2017/4/7 下午4:33
 * Description:
 * PackageName: com.dan.myarchitectproject.common
 * Copyright: 杭州安存网络科技有限公司
 **/

public class BaseApplication extends Application {
    private static DaoSession mDaoSession;
    private static Database db;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = initDaoSession(this);
        CrashHandler.getInstance().init(this);
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static Database getDb() {
        return db;
    }

    private static DaoSession initDaoSession(Context context) {
        if (mDaoSession == null) {
            try {
                ContextWrapper wrapper = new ContextWrapper(context) {
                    /**
                     * 获得数据库路径，如果不存在，则创建对象
                     *
                     * @param name
                     */
                    @Override
                    public File getDatabasePath(String name) {
                        String dbDir;
                        // 判断是否存在sd卡
                        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
                        if (!sdExist) {// 如果不存在,
                            dbDir = Environment.getRootDirectory().getAbsolutePath();
                        } else {// 如果存在
                            // 获取sd卡路径
                            dbDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                        }
                        dbDir += "/Android";// 数据库所在目录
                        String dbPath = dbDir + "/" + name;// 数据库路径
                        // 判断目录是否存在，不存在则创建该目录
                        File dirFile = new File(dbDir);
                        if (!dirFile.exists())
                            dirFile.mkdirs();

                        // 数据库文件是否创建成功
                        boolean isFileCreateSuccess = false;
                        // 判断文件是否存在，不存在则创建该文件
                        File dbFile = new File(dbPath);
                        if (!dbFile.exists()) {
                            try {
                                isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else
                            isFileCreateSuccess = true;
                        // 返回数据库文件对象
                        if (isFileCreateSuccess)
                            return dbFile;
                        else
                            return super.getDatabasePath(name);
                    }

                    /**
                     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
                     *
                     * @param name
                     * @param mode
                     * @param factory
                     */
                    @Override
                    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.
                            CursorFactory factory) {
                        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
                    }

                    /**
                     * Android 4.0会调用此方法获取数据库。
                     * @param name
                     * @param mode
                     * @param factory
                     * @param errorHandler
                     */
                    @Override
                    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.
                            CursorFactory factory, DatabaseErrorHandler errorHandler) {
                        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
                    }
                };
                DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(wrapper, "test.db");
                mDaoSession = new DaoMaster(helper.getWritableDb()).newSession();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mDaoSession;
    }
}
