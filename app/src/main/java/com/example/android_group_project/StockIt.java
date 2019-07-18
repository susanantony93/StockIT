package com.example.android_group_project;

import android.app.Application;

public class StockIt extends Application {
    public static ProductdbHelper db;
    @Override
    public void onCreate() {
        super.onCreate();
        if(db==null){
            db=new ProductdbHelper(this);
        }
    }
}
