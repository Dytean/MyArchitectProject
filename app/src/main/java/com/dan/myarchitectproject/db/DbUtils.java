package com.dan.myarchitectproject.db;

import com.dan.myarchitectproject.common.BaseApplication;
import com.dan.myarchitectproject.db.dao.DaoSession;
import com.dan.myarchitectproject.db.dao.PersonDao;
import com.dan.myarchitectproject.db.entity.Person;

/**
 * Author: Dan
 * Date: 2017/4/7 下午5:27
 * Description:
 * PackageName: com.dan.myarchitectproject.db
 * Copyright: 杭州安存网络科技有限公司
 **/

public class DbUtils<T> {
    public static DaoSession mDaoSession = BaseApplication.getDaoSession();
    private static DbUtils sDbUtils;
    public static DbUtils getInstance(){
        if (sDbUtils == null){
            sDbUtils = new DbUtils();
        }
        return sDbUtils;
    }

    public void insert(T t){
        if (t instanceof Person) {
            mDaoSession.getPersonDao().insert((Person) t);
        }
    }

}
