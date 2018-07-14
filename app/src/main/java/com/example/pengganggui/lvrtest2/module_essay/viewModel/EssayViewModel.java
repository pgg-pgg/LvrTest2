package com.example.pengganggui.lvrtest2.module_essay.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.pengganggui.lvrtest2.domain.Resource;
import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuItemEntity;
import com.example.pengganggui.lvrtest2.module_essay.repsitory.EssayRepository;

/**
 * Created by pengganggui on 2018/7/14.
 * ViewModel获取数据源
 */

public class EssayViewModel extends AndroidViewModel {

    private EssayRepository mRepository;
    private MediatorLiveData<Resource<ZhihuItemEntity>> mCache;

    public EssayViewModel(@NonNull Application application) {
        super(application);
        mRepository=new EssayRepository(application);
    }

    public LiveData<Resource<ZhihuItemEntity>> getEssayData(){
        if (mCache==null){
            mCache=mRepository.loadEssayData();
        }
        return mCache;
    }

    public void updateCache(){
        final LiveData<Resource<ZhihuItemEntity>> update=mRepository.update();
        mCache.addSource(update, new Observer<Resource<ZhihuItemEntity>>() {
            @Override
            public void onChanged(@Nullable Resource<ZhihuItemEntity> zhihuItemEntityResource) {
                mCache.setValue(zhihuItemEntityResource);
            }
        });
    }

    public void addMore(){
        //Todo: 加载更多
    }
}
