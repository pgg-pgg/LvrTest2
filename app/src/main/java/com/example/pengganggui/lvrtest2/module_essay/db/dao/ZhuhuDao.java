package com.example.pengganggui.lvrtest2.module_essay.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuItemEntity;

/**
 * Created by pengganggui on 2018/7/13.
 * 知乎文章数据库操作类接口
 */

@Dao
public interface ZhuhuDao {

    @Query("SELECT * FROM zhuhulist order by id desc,id limit 0,1")
    LiveData<ZhihuItemEntity> loadZhihu();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ZhihuItemEntity products);
}
