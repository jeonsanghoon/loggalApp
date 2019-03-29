package com.altsoft.model.UserInfo;

import java.io.Serializable;

public class T_MEMBER_COND implements Serializable {
    /// 사용자아이디(E-Mail)
    public String USER_ID;
    public Integer USER_TYPE;
    public String MOBILE;
    public String PHONE;
    public Boolean EMPLOYEE_YN;
    public String PASSWORD;
    public String PASSWORD_CHANGE_URL;
    /// 숨김여부(1:숨김 0:보임)
    public Boolean HIDE;
    public Long MEMBER_CODE;
}
