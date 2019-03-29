package com.altsoft.Framework.DataInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.altsoft.loggalapp.MainActivity;

public class SaveSharedPreference {


    private SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public  void setData( String Key, String value) {
        this.setData(MainActivity.activity, Key,value);
    }
    // 계정 정보 저장
    public  void setData(Context ctx, String Key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Key, value);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public  String getData( String Key) {
        return getData(MainActivity.activity, Key);
    }
    // 저장된 정보 가져오기
    public  String getData(Context ctx, String Key) {
        return getSharedPreferences(ctx).getString(Key, "");
    }
    // 로그아웃
    public  void clearData() {
        this.clearData(MainActivity.activity);
    }
    // 로그아웃
    public  void clearData(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
