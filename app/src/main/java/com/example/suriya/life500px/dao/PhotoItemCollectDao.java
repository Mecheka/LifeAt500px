package com.example.suriya.life500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Suriya on 30/11/2560.
 */

public class PhotoItemCollectDao implements Parcelable {

    @SerializedName("success") private boolean seccess;
    @SerializedName("data") private List<PhotoItemDao> data;

    public PhotoItemCollectDao(){

    }

    protected PhotoItemCollectDao(Parcel in) {
        seccess = in.readByte() != 0;
        data = in.createTypedArrayList(PhotoItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (seccess ? 1 : 0));
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemCollectDao> CREATOR = new Creator<PhotoItemCollectDao>() {
        @Override
        public PhotoItemCollectDao createFromParcel(Parcel in) {
            return new PhotoItemCollectDao(in);
        }

        @Override
        public PhotoItemCollectDao[] newArray(int size) {
            return new PhotoItemCollectDao[size];
        }
    };

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
