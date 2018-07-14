package com.example.pengganggui.lvrtest2;

import android.app.Application;

import com.example.pengganggui.lvrtest2.db_holder.AppDB;

/**
 * Created by pengganggui on 2018/7/13.
 */

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors=new AppExecutors();
    }

    public AppDB getDatabase(){
        return AppDB.getsInstance(this,mAppExecutors);
    }

    public AppExecutors getmAppExecutors(){
        return mAppExecutors;
    }


}
