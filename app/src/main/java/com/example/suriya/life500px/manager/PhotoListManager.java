package com.example.suriya.life500px.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.suriya.life500px.dao.PhotoItemCollectDao;
import com.example.suriya.life500px.dao.PhotoItemDao;
import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

/**
 * Created by Suriya on 31/1/2561.
 */

public class PhotoListManager  {

    private Context mContext;
    private PhotoItemCollectDao dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
        loadCache();
    }

    public PhotoItemCollectDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectDao dao) {
        this.dao = dao;
        saveCache();
    }

    public void insertDaoAtTopPosition(PhotoItemCollectDao newDao){
        if (dao == null){
            dao = new PhotoItemCollectDao();
        }
        if (dao.getData() == null){
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(0, newDao.getData());
        saveCache();
    }

    public void appendDaoAtBottomPosition(PhotoItemCollectDao newDao){
        if (dao == null){
            dao = new PhotoItemCollectDao();
        }
        if (dao.getData() == null){
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(dao.getData().size(), newDao.getData());
        saveCache();
    }

    public int getMaximumId(){
        if (dao == null){
            return 0;
        }
        if (dao.getData() == null){
            return 0;
        }
        if (dao.getData().size() == 0){
            return 0;
        }
        int maxId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        return maxId;
    }

    public int getMinimumId(){
        if (dao == null){
            return 0;
        }
        if (dao.getData() == null){
            return 0;
        }
        if (dao.getData().size() == 0){
            return 0;
        }
        int minId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            minId = Math.min(minId, dao.getData().get(i).getId());
        return minId;
    }

    public int getCount(){
        if (dao == null)
            return 0;

        if (dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

    public Bundle onSaveInstanceState(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){

        dao = savedInstanceState.getParcelable("dao");

    }

    private void saveCache(){
        PhotoItemCollectDao cacheDao = new PhotoItemCollectDao();
        if (dao != null && dao.getData() != null) {
            cacheDao.setData(dao.getData().subList(0, Math.min(20, dao.getData().size())));
        }
        String json = new Gson().toJson(cacheDao);
        SharedPreferences prefs = mContext.getSharedPreferences("photo", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("json", json);
        editor.apply();

    }

    private void loadCache(){
        //TODO: load cache
        SharedPreferences prefs = mContext.getSharedPreferences("photo", mContext.MODE_PRIVATE);
        String json = prefs.getString("json", null);
        if (json == null){
            return;
        }
        dao = new Gson().fromJson(json, PhotoItemCollectDao.class);
    }

}
