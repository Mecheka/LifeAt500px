package com.example.suriya.life500px.manager.http;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HttpManage {

    private static HttpManage instance;

    public static HttpManage getInstance() {
        if (instance == null)
            instance = new HttpManage();
        return instance;
    }

    private Context mContext;

    private ApiSevice sevice;

    private HttpManage() {
        mContext = Contextor.getInstance().getContext();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nuuneoi.com/courses/500px/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        sevice = retrofit.create(ApiSevice.class);
    }

    public ApiSevice getSevice(){
        return sevice;
    }

}
