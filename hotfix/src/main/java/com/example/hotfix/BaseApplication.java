package com.example.hotfix;

import android.app.Application;
import android.content.Context;

import com.example.hotfix.hotfix.DexUtil;

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
        DexUtil.init(this);
    }
}
