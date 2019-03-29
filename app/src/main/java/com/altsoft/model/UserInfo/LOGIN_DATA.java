package com.altsoft.model.UserInfo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.altsoft.Framework.DataInfo.SaveSharedPreference;
import com.altsoft.Framework.Global;
import com.altsoft.loggalapp.MainActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class LOGIN_DATA implements Serializable {
    public String ERROR_MESSAGE;
    public int MEMBER_CODE;
    public String USER_ID;
    public String PASSWORD;
    public String USER_NAME;
    public String profileImagePath;

    public void setThumnailPath(String thumnailPath) {
        Global.getSaveSharedPreference().setData(MainActivity.activity, "thumnailPath",thumnailPath);
        this.thumnailPath = thumnailPath;
    }

    public String getThumnailPath() {
        if(Global.getValidityCheck().isEmpty(thumnailPath)) thumnailPath = "";
        return thumnailPath;
    }

    public String thumnailPath;
    public LOGIN_DATA()
    {
        try {
            if(!Global.getValidityCheck().isEmpty(Global.getSaveSharedPreference().getData(MainActivity.activity, "MEMBER_CODE"))) {
                MEMBER_CODE = Integer.parseInt(Global.getSaveSharedPreference().getData(MainActivity.activity, "MEMBER_CODE"));
            }else MEMBER_CODE = 0;
            USER_ID = Global.getSaveSharedPreference().getData(MainActivity.activity, "USER_ID");
            PASSWORD = Global.getSaveSharedPreference().getData(MainActivity.activity, "PASSWORD");
            USER_NAME = Global.getSaveSharedPreference().getData(MainActivity.activity, "USER_NAME");
            profileImagePath = Global.getSaveSharedPreference().getData(MainActivity.activity, "profileImagePath");
            thumnailPath = Global.getSaveSharedPreference().getData(MainActivity.activity, "thumnailPath");
        }catch(Exception ex){}
    }
     public boolean isLogin()
     {

         String USER_ID = Global.getSaveSharedPreference().getData(MainActivity.activity, "USER_ID");
         if(getData() == null ||  Global.getValidityCheck().isEmpty(getData().USER_ID)){
             return false;
         }else return true;

     }
     public LOGIN_DATA getData() {
         LOGIN_DATA rtn = new LOGIN_DATA();
         try {
             rtn.USER_ID = Global.getSaveSharedPreference().getData(MainActivity.activity, "USER_ID");
             rtn.MEMBER_CODE = Integer.parseInt(Global.getSaveSharedPreference().getData(MainActivity.activity, "MEMBER_CODE"));
             if (Global.getValidityCheck().isEmpty(rtn.USER_ID)) {
                 throw new Exception("");
             }
             rtn.PASSWORD = Global.getSaveSharedPreference().getData(MainActivity.activity, "PASSWORD");
             rtn.USER_NAME = Global.getSaveSharedPreference().getData(MainActivity.activity, "USER_NAME");
             rtn.profileImagePath = Global.getSaveSharedPreference().getData(MainActivity.activity, "profileImagePath");
             rtn.thumnailPath = Global.getSaveSharedPreference().getData(MainActivity.activity, "thumnailPath");
             return rtn;
         } catch (Exception ex) {
             return rtn;
         }
    }

    public LOGIN_DATA setData(LOGIN_DATA data)
    {
        if(data == null){Global.getSaveSharedPreference().setData(MainActivity.activity, "USER_ID", null);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "MEMBER_CODE", "0");
            Global.getSaveSharedPreference().setData(MainActivity.activity, "PASSWORD", null);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "USER_NAME", null);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "profileImagePath", null);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "thumnailPath", null);
            ERROR_MESSAGE = "";
            MEMBER_CODE =0;
            USER_ID = null;
            PASSWORD = null;
            USER_NAME = null;
            profileImagePath = null;
            thumnailPath = null;
        }
        else {
            Global.getSaveSharedPreference().setData(MainActivity.activity, "MEMBER_CODE", Integer.toString(data.MEMBER_CODE));
            Global.getSaveSharedPreference().setData(MainActivity.activity, "USER_ID", data.USER_ID);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "PASSWORD", data.PASSWORD);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "USER_NAME", data.USER_NAME);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "profileImagePath", data.profileImagePath);
            Global.getSaveSharedPreference().setData(MainActivity.activity, "thumnailPath", data.thumnailPath);
            ERROR_MESSAGE = "";
            MEMBER_CODE = data.MEMBER_CODE;
            USER_ID = data.USER_ID;
            PASSWORD = data.PASSWORD;
            USER_NAME = data.USER_NAME;
            profileImagePath = data.profileImagePath;
            thumnailPath = data.thumnailPath;
/*
            Toast.makeText(
                   Global.getCurrentActivity(),
                    data.USER_NAME + "님이 로그인하였습니다.",
                    Toast.LENGTH_LONG).show();*/
        }
        return data;
    }

    public void LogOut(){
        setData(null);
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.d("로그아웃","로그아웃되었음");
                Toast.makeText(
                        Global.getCurrentActivity(),
                        "로그아웃되었습니다.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
