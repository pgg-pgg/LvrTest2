package com.example.pengganggui.lvrtest2.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.pengganggui.lvrtest2.domain.Resource.Status.ERROR;
import static com.example.pengganggui.lvrtest2.domain.Resource.Status.LOADING;
import static com.example.pengganggui.lvrtest2.domain.Resource.Status.MORE_ADD;
import static com.example.pengganggui.lvrtest2.domain.Resource.Status.SUCCEED;

/**
 * Created by pengganggui on 2018/7/13.
 */

public class Resource<T> {

    //加载状态
    public enum Status{
        LOADING,MORE_ADD,SUCCEED,ERROR
    }
    @NonNull
    public final Status status;

    @NonNull
    public final T data;

    @NonNull
    public final String message;

    private Resource(@NonNull Status status,@NonNull T data,@NonNull String message){
        this.status=status;
        this.data=data;
        this.message=message;
    }

    /**
     * 加载成功无消息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> success(@NonNull T data){
        return new Resource<>(SUCCEED,data,null);
    }

    /**
     * 加载错误有消息
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    /**
     * 正在加载无消息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    /**
     * 加载更多无消息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> moreSucceed(@Nullable T data) {
        return new Resource<>(MORE_ADD, data, null);
    }

    /**
     * 加载成功有消息
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Resource<T> success(@NonNull T data, String msg) {
        return new Resource<>(SUCCEED, data, msg);
    }

    /**
     * 正在加载有消息
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Resource<T> loading(@Nullable T data, String msg) {
        return new Resource<>(LOADING, data, msg);
    }

    /**
     * 加载更多有消息
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Resource<T> moreSucceed(@Nullable T data, String msg) {
        return new Resource<>(MORE_ADD, data, msg);
    }
}
