package com.example.suriya.life500px.manager.http;

import android.support.annotation.Nullable;

import com.example.suriya.life500px.dao.PhotoItemCollectDao;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Suriya on 30/11/2560.
 */

public interface ApiSevice {

    @POST("list")
    Call<PhotoItemCollectDao> loadPhotoList();

    @POST("list/after/{id}")
    Call<PhotoItemCollectDao> loadPhotoListAfterId(@Path("id") int id);

    @POST("list/before/{id}")
    Call<PhotoItemCollectDao> loadPhotoListBeforeId(@Path("id") int id);

}
