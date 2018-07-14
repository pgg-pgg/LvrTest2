package com.example.pengganggui.lvrtest2.domain;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

/**
 * Created by pengganggui on 2018/7/13.
 * 数据库资源基类
 */

public abstract class AbsDataSource<ResultType,RequestType> {
    //数据容器
    private final MediatorLiveData<Resource<ResultType>> result=new MediatorLiveData<>();

    //保存请求数据，在子线程执行
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    //判断是否应该拉取
    @MainThread
    protected abstract boolean shouldFetch(@NonNull ResultType data);

    //从数据库中加载数据
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<IRequestApi<RequestType>> createCall();

    //获取失败
    @MainThread
    protected abstract void onFetchFailed();

    @MainThread
    public AbsDataSource(){
        final LiveData<ResultType> dbSource=loadFromDb();
        result.setValue(Resource.loading(dbSource.getValue(),"db load"));

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                result.removeSource(dbSource);
                if (shouldFetch(resultType)){
                    fetchFromNetWork(dbSource);
                }else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            result.setValue(Resource.success(resultType));
                        }
                    });
                }
            }
        });
    }

    private void fetchFromNetWork(final LiveData<ResultType> dbSource) {
        final LiveData<IRequestApi<RequestType>> apiResponse=createCall();

        result.addSource(apiResponse, new Observer<IRequestApi<RequestType>>() {
            @Override
            public void onChanged(@Nullable final IRequestApi<RequestType> requestTypeIRequestApi) {
                result.removeSource(apiResponse);
                //如果请求成功
                if (requestTypeIRequestApi.isSuccessful()){
                    //保存到数据库并且重新初始化
                    saveResultAndReInit(requestTypeIRequestApi);
                }else {
                    //失败
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            result.setValue(Resource.error(requestTypeIRequestApi.getErrorMsg(),resultType));
                        }
                    });
                }
            }
        });
    }

    //保存数据并且重新初始化
    @SuppressLint("StaticFieldLeak")
    private void saveResultAndReInit(final IRequestApi<RequestType> response) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response.getBody());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType resultType) {
                        result.setValue(Resource.success(resultType));
                    }
                });
            }
        }.execute();

    }

    public final MediatorLiveData<Resource<ResultType>> getAsLiveData(){
        return result;
    }
}
