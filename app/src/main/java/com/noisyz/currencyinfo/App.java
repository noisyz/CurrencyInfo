package com.noisyz.currencyinfo;

import android.app.Application;

import com.noisyz.currencyinfo.data.SharedManager;
import com.noisyz.currencyinfo.net.ApiService;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiService.getInstance();
        SharedManager.init(this);
    }

}
