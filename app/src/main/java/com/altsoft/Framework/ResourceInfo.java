package com.altsoft.Framework;

import android.content.res.Resources;

import com.altsoft.loggalapp.R;

public class ResourceInfo {
    public String getkakaoNativekey(){return Global.getCurrentActivity().getString(R.string.kakaoNativekey);};
    public String getkakaoRestkey(){  return Global.getCurrentActivity().getString(R.string.kakaoRestkey);};
    public String getkakaoJavascriptkey(){    return Global.getCurrentActivity().getString(R.string.kakaoJavascriptkey);};
    public String getkakaoAdminkey(){    return Global.getCurrentActivity().getString(R.string.kakaoAdminkey); };

    public String getFileHost(){    return Global.getCurrentActivity().getString(R.string.filehost); };

    public String getAesKey(){    return Global.getCurrentActivity().getString(R.string.aeskey); };
}
