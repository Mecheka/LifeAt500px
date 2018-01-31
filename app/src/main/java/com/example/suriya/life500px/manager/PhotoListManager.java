package com.example.suriya.life500px.manager;

import android.content.Context;

import com.example.suriya.life500px.dao.PhotoItemCollectDao;
import com.example.suriya.life500px.dao.PhotoItemDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

/**
 * Created by Suriya on 31/1/2561.
 */

public class PhotoListManager {

    private Context mContext;
    private PhotoItemCollectDao dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemCollectDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectDao dao) {
        this.dao = dao;
    }

    public void insertDaoAtTopPosition(PhotoItemCollectDao newDao){
        if (dao == null){
            dao = new PhotoItemCollectDao();
        }
        if (dao.getData() == null){
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(0, newDao.getData());
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

    public int getCount(){
        if (dao == null)
            return 0;

        if (dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

}
