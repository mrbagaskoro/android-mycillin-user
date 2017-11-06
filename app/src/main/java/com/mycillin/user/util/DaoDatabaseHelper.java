package com.mycillin.user.util;

import android.app.Activity;

import com.mycillin.user.database.Banner;
import com.mycillin.user.database.BannerDao;
import com.mycillin.user.database.DaoSession;

import org.greenrobot.greendao.query.Query;

/**
 * Created by 16003041 on 06/11/2017.
 */

public class DaoDatabaseHelper {

    private Activity activity;
    private DaoSession daoSession;

    public DaoDatabaseHelper(Activity activity) {
        this.activity = activity;
        this.daoSession = ((BaseApplication) activity.getApplication()).getDaoSession();
    }

    public Query<Banner> getBanner() {
        BannerDao bannerDao = daoSession.getBannerDao();

        return bannerDao.queryBuilder().build();
    }
}
