package com.misa.cukcuklite;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class CukCukApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
