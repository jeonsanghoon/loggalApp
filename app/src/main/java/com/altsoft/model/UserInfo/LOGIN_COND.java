package com.altsoft.model.UserInfo;

import java.io.Serializable;

public class LOGIN_COND implements Serializable {
    public String USER_ID;
    public String PASSWORD;
    public String NEW_PASSWORD;
    public String KAKAO_ID;
    public String GOOGLE_ID;
    public String NAVER_ID;
    public String FACEBOOK_ID;
    public Boolean bSnsLogin = false;

    public String profileImagePath;
    public String thumnailPath;

}
