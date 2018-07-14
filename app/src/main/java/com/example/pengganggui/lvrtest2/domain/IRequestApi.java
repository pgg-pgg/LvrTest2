package com.example.pengganggui.lvrtest2.domain;

/**
 * Created by pengganggui on 2018/7/13.
 *
 * 请求后的接口
 */

public interface IRequestApi<ResultType> {
    //获取响应
    ResultType getBody();

    //请求错误消息
    String getErrorMsg();

    //请求成功
    boolean isSuccessful();
}
