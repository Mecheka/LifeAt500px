package com.example.suriya.life500px.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Suriya on 30/11/2560.
 */

public class PhotoItemCollectDao {

    @SerializedName("success") private boolean seccess;
    @SerializedName("data") private List<PhotoItemDao> data;

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
