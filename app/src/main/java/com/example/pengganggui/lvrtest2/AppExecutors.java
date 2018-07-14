package com.example.pengganggui.lvrtest2;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pengganggui on 2018/7/13.
 * 线程池
 */

public class AppExecutors {

    //本地存储线程池
    private final Executor mDiskIO;
    //网络存储线程池
    private final Executor mNetworkIO;
    //主线程池
    private final Executor mMainThread;

    public AppExecutors(Executor diskIO,Executor netWorkIO,Executor mainThread) {
        this.mDiskIO=diskIO;
        this.mNetworkIO=netWorkIO;
        this.mMainThread=mainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainThreadExecutor());
    }

    public Executor diskIO(){return mDiskIO;}

    public Executor networkIO(){return mNetworkIO;}

    public Executor mainThread(){return mMainThread;}


    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler=new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
