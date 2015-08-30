package com.smssender;

import android.app.Application;
import android.content.SharedPreferences;

import io.realm.Realm;

public class SmsSenderApplication extends Application {

    public static SharedPreferences prefs;
    private static Realm realmDb;
    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences("SmsSender", MODE_PRIVATE);
        realmDb = Realm.getInstance(this);
    }

    public static Realm getRealmDb() {
        return realmDb;
    }
}
