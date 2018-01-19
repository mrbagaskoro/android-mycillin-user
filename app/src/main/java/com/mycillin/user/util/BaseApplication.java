package com.mycillin.user.util;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.facebook.stetho.Stetho;
import com.mycillin.user.database.DaoMaster;
import com.mycillin.user.database.DaoSession;

import io.fabric.sdk.android.Fabric;

import org.greenrobot.greendao.database.Database;

public class BaseApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mycillin-user-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        Stetho.initializeWithDefaults(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
