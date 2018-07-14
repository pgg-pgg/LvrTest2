package com.example.pengganggui.lvrtest2.module_essay.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.pengganggui.lvrtest2.module_essay.model.Essay;

/**
 * Created by pengganggui on 2018/7/13.
 * 每日文章实体类
 */

@Entity(tableName = "essays")
public class EssayDayEntity implements Essay{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dataCurr;
    private String author;
    private String title;
    private String digest;
    private String content;
    private long wc;

    public int getId(){return id;}

    public void setId(int id){
        this.id=id;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDigest() {
        return digest;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public long getWc() {
        return wc;
    }

    public String getDataCurr() {
        return dataCurr;
    }

    public void setDataCurr(String dataCurr) {
        this.dataCurr = dataCurr;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWc(long wc) {
        this.wc = wc;
    }
}
