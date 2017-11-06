package com.mycillin.user.util;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.mycillin.user.database.DaoMaster;
import com.mycillin.user.database.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 16003041 on 13/09/2017.
 */

public class BaseApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mycillin-user-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        Stetho.initializeWithDefaults(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
