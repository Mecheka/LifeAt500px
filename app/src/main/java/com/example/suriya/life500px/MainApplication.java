package com.example.suriya.life500px;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by Suriya on 30/11/2560.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Contextor.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
