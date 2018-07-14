package com.example.pengganggui.lvrtest2.domain;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.example.pengganggui.lvrtest2.module_essay.db.EssayDbCallback;

import java.util.ArrayList;

/**
 * Created by pengganggui on 2018/7/13.
 * 数据库帮助类
 */

public class DbCallbackHelper {

    //数据库操作集合
    private static ArrayList<AbsDbCallback> mDbCallbacks = new ArrayList<>();

    public static void init() {
        mDbCallbacks.add(new EssayDbCallback());
    }

    /**
     * 一一创建数据库
     *
     * @param db
     */
    public static void dispatchOnCreate(SupportSQLiteDatabase db) {
        for (AbsDbCallback callback : mDbCallbacks) {
            callback.create(db);
        }
    }

    /**
     * 一一更新数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    private static void dispatchUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
        for (AbsDbCallback callback : mDbCallbacks) {
            callback.upgrade(db, oldVersion, newVersion);
        }
    }

    /**
     * 数据迁移
     * @return
     */
    public static Migration[] getUpdateConfig() {
        return new Migration[]{
                new Migration(1, 2) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        dispatchUpgrade(database, 1, 2);
                    }
                },
                new Migration(2, 3) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        dispatchUpgrade(database, 2, 3);
                    }
                }
        };
    }
}
