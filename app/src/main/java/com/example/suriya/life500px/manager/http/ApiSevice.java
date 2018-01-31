package com.example.suriya.life500px.manager.http;

import com.example.suriya.life500px.dao.PhotoItemCollectDao;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Suriya on 30/11/2560.
 */

public interface ApiSevice {

    @POST("list")
    Call<PhotoItemCollectDao> loadPhotoList();

}
