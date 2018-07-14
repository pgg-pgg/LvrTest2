package com.example.pengganggui.lvrtest2.domain;

import android.arch.persistence.db.SupportSQLiteDatabase;

/**
 * Created by pengganggui on 2018/7/13.
 * 数据库操作接口
 */

public abstract class AbsDbCallback {

    //创建
    public abstract void create(SupportSQLiteDatabase db);

    //打开
    public abstract void open();

    //更新
    public abstract void upgrade(SupportSQLiteDatabase db,int oldVersion,int newVersion);
}
