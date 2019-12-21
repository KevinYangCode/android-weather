package xyz.jianzha.weather.base;

import android.app.Application;

import org.xutils.x;

import xyz.jianzha.weather.db.DBManager;

public class UniteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }
}
