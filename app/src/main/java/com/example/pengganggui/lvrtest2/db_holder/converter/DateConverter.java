package com.example.pengganggui.lvrtest2.db_holder.converter;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuStoriesEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pengganggui on 2018/7/14.
 * 数据转换类（日期），将获取到的日期转换为long存储到数据库，并在取出数据时使用Date格式
 */

public class DateConverter {

    /**
     * 转换日期,将long型转换为日期
     * @param timeStamp
     * @return
     */
    @TypeConverter
    public static Date toDate(Long timeStamp){
        return timeStamp==null?null:new Date(timeStamp);
    }


    /**
     * 将Date转换为long
     * @param date
     * @return
     */
    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date==null?null:date.getTime();
    }

    /**
     * 从Json字符串解析成知乎实体类的集合
     * @param timeStamp
     * @return
     */
    @TypeConverter
    public static List<ZhihuStoriesEntity> toString(String timeStamp){
        List<ZhihuStoriesEntity> tmp=new ArrayList<>();
        Gson gson=new GsonBuilder().create();
        try {
            JSONArray jsonArray=new JSONArray(timeStamp);
            for (int i=0,j=jsonArray.length();i<j;i++){
                ZhihuStoriesEntity entity=gson.fromJson(jsonArray.getJSONObject(i).toString(), ZhihuStoriesEntity.class);
                tmp.add(entity);
            }
        }catch (JSONException e){

        }
        return tmp;
    }

    /**
     * 将知乎实体类转换为String
     * @param list
     * @return
     */
    @TypeConverter
    public static String toZhihuStoriesEntity(List<ZhihuStoriesEntity> list){
        StringBuffer res=new StringBuffer("[");
        Gson gson=new GsonBuilder().create();

        try {
            for (ZhihuStoriesEntity e:list){
                res.append(gson.toJson(e)+",");
            }
        }catch (Exception e){

        }

        if (res.length()>1){
            res.replace(res.length()-1,res.length(),"]");
        }else {
            res.append("]");
        }
        Log.d("changxing",res.toString());
        return res.toString();
    }


}
