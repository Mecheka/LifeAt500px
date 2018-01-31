package com.example.suriya.life500px.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.suriya.life500px.R;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListItem extends BaseCustomViewGroup {

    private ImageView ivImg;
    private TextView tvName, tvDis;

    public PhotoListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public PhotoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_photo, this);
    }

    private void initInstances() {
        // findViewById here
        ivImg = (ImageView) findViewById(R.id.ivImg);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDis = (TextView) findViewById(R.id.tvDis);
    }

    public void setIvImg(String url) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading);

        Glide.with(getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivImg);
    }

    public void setTvName(String text) {
        tvName.setText(text);
    }

    public void setTvDis(String text) {
        tvDis.setText(text);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widtg = MeasureSpec.getSize(widthMeasureSpec);
        int heigth = widtg * 2 / 3;
        int newHeigthMeasureSpec = MeasureSpec.makeMeasureSpec(
                heigth,
                MeasureSpec.EXACTLY
        );
        super.onMeasure(widthMeasureSpec, newHeigthMeasureSpec);
        setMeasuredDimension(widtg, heigth);
    }
}
