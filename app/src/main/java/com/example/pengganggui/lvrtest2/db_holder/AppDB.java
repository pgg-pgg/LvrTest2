package com.example.pengganggui.lvrtest2.db_holder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.pengganggui.lvrtest2.AppExecutors;
import com.example.pengganggui.lvrtest2.db_holder.converter.DateConverter;
import com.example.pengganggui.lvrtest2.domain.DbCallbackHelper;
import com.example.pengganggui.lvrtest2.module_essay.db.dao.EssayDao;
import com.example.pengganggui.lvrtest2.module_essay.db.dao.ZhuhuDao;
import com.example.pengganggui.lvrtest2.module_essay.db.entity.EssayDayEntity;
import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuItemEntity;

/**
 * Created by pengganggui on 2018/7/14.
 * 数据库实体类
 */
@Database(entities ={EssayDayEntity.class, ZhihuItemEntity.class},version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDB extends RoomDatabase {
    private static AppDB sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME="canking.db";

    public abstract EssayDao essayDao();

    public abstract ZhuhuDao zhuhuDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated=new MutableLiveData<>();

    public static AppDB getsInstance(final Context context, final AppExecutors  executors){
        if (sInstance==null){
            synchronized (AppDB.class){
                if (sInstance==null){
                    sInstance=buildDatabase(context.getApplicationContext(),executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private void updateDatabaseCreated(Context applicationContext) {
        if (applicationContext.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }

    private static AppDB buildDatabase(final Context applicationContext, final AppExecutors executors) {
        return Room.databaseBuilder(applicationContext,AppDB.class,DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                addDelay();
                                AppDB database=AppDB.getsInstance(applicationContext,executors);
                                DbCallbackHelper.dispatchOnCreate(db);
                                database.setDatabaseCreated();
                            }
                        });
                    }
                }).addMigrations(DbCallbackHelper.getUpdateConfig()).build();
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void addDelay() {
        try{
            Thread.sleep(4000);
        }catch (InterruptedException ignored){

        }
    }

    public LiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    }

}
