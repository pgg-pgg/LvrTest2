package com.example.pengganggui.lvrtest2.module_essay.net;

import android.support.annotation.StringDef;

import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuItemEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pengganggui on 2018/7/13.
 */

public interface EssayWebService {
    String DAY="today";
    String RANDOM="random";


    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    @StringDef(value = {DAY,RANDOM})
    @interface EssayType{}

    @GET("article/{type}?dev=1")
    Call<ResponseBody> getEssay(@EssayType @Path("type") String type);

    @GET("news/{type}")
    Call<ZhihuItemEntity> getZhihuList(@Path("type") String type);

    @GET("users")
    Call<ResponseBody> getTest();

}
