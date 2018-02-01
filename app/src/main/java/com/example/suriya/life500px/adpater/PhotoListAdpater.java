package com.example.suriya.life500px.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.example.suriya.life500px.R;
import com.example.suriya.life500px.dao.PhotoItemCollectDao;
import com.example.suriya.life500px.dao.PhotoItemDao;
import com.example.suriya.life500px.view.PhotoListItem;

/**
 * Created by Suriya on 30/11/2560.
 */

public class PhotoListAdpater extends BaseAdapter {

    int lastposition = -1;

    PhotoItemCollectDao dao;

    public void setDao(PhotoItemCollectDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null) {
            return 1;
        }
        if (dao.getData() == null) {
            return 1;
        }
        return dao.getData().size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == getCount() -1){
            ProgressBar itemBar;
            if (convertView != null){
                itemBar = (ProgressBar) convertView;
            }else {
                itemBar = new ProgressBar(parent.getContext());
            }
            return itemBar;
        }
        PhotoListItem item;
        if (convertView != null) {
            item = (PhotoListItem) convertView;
        } else {
            item = new PhotoListItem(parent.getContext());
        }
        PhotoItemDao dao = (PhotoItemDao) getItem(position);
        item.setIvImg(dao.getImageUrl().toString());
        item.setTvName(dao.getCaption());
        item.setTvDis(dao.getUserName() + "\n" + dao.getCamera());

        if (position > lastposition){
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastposition = position;
        }
        return item;
    }

    public void increaseLastPosition(int amount){

        lastposition += amount;

    }

}
