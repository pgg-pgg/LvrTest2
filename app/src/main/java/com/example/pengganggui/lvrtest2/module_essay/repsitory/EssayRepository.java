package com.example.pengganggui.lvrtest2.module_essay.repsitory;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.pengganggui.lvrtest2.AppExecutors;
import com.example.pengganggui.lvrtest2.BasicApp;
import com.example.pengganggui.lvrtest2.domain.AbsDataSource;
import com.example.pengganggui.lvrtest2.domain.AbsRepository;
import com.example.pengganggui.lvrtest2.domain.IRequestApi;
import com.example.pengganggui.lvrtest2.domain.Resource;
import com.example.pengganggui.lvrtest2.module_essay.db.dao.ZhuhuDao;
import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuItemEntity;
import com.example.pengganggui.lvrtest2.module_essay.net.EssayWebService;
import com.example.pengganggui.lvrtest2.module_essay.net.NetEngine;

/**
 * Created by pengganggui on 2018/7/13.
 *
 */

public class EssayRepository extends AbsRepository {

    //网络引擎
    private NetEngine webService;
    //知乎文章数据库查询dao
    private ZhuhuDao zhuhuDao;
    //线程池
    private AppExecutors executors;

    public EssayRepository(Application application){
        webService=NetEngine.getInstance();
        zhuhuDao=((BasicApp)application).getDatabase().zhuhuDao();
        this.executors=((BasicApp)application).getmAppExecutors();
    }

    /**
     * 数据库数据更新
     * @return
     */
    public MediatorLiveData<Resource<ZhihuItemEntity>> update(){
        return loadEssayData();
    }

    /**
     * 加载文章数据
     * @return
     */
    public MediatorLiveData<Resource<ZhihuItemEntity>> loadEssayData() {
        return new AbsDataSource<ZhihuItemEntity,ZhihuItemEntity>(){
            @Override
            protected void saveCallResult(@NonNull ZhihuItemEntity item) {
                zhuhuDao.insertItem(item);
            }

            @Override
            protected boolean shouldFetch(@NonNull ZhihuItemEntity data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ZhihuItemEntity> loadFromDb() {
                LiveData<ZhihuItemEntity> entity=zhuhuDao.loadZhihu();
                return entity;
            }

            @NonNull
            @Override
            protected LiveData<IRequestApi<ZhihuItemEntity>> createCall() {
                final MediatorLiveData<IRequestApi<ZhihuItemEntity>> result=new MediatorLiveData<>();
                executors.networkIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LiveData<IRequestApi<ZhihuItemEntity>> netGet=webService.getEssay(EssayWebService.DAY);

                            result.addSource(netGet, new Observer<IRequestApi<ZhihuItemEntity>>() {
                                @Override
                                public void onChanged(@Nullable IRequestApi<ZhihuItemEntity> essayDayEntityIRequestApi) {
                                    result.postValue(essayDayEntityIRequestApi);
                                }
                            });
                        }catch (Exception e) {
                            e.printStackTrace();
                            onFetchFailed();
                        }
                    }
                });
                return result;
            }

            @Override
            protected void onFetchFailed() {

            }
        }.getAsLiveData();
    }

}
