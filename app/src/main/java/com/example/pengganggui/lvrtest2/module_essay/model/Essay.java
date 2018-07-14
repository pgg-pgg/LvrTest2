package com.example.pengganggui.lvrtest2.module_essay.model;

/**
 * Created by pengganggui on 2018/7/13.
 * 实体类接口
 */

public interface Essay {
    //获取id
    int getId();
    //获取作者
    String getAuthor();
    //获取文章标题
    String getTitle();
    //获取数据
    String getDigest();
    //获取文章内容
    String getContent();
    //获取。。。
    long getWc();
}
