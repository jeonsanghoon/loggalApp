package com.altsoft.model.signage;

import java.io.Serializable;

/// 사이니지 조회리스트
public class MOBILE_SIGNAGE_LIST implements Serializable {
    public Long SIGN_CODE;
    public String SIGN_NAME;
    public Double DISTANCE;
    public Double PLACE_DISTINCE;
    public String REMARK;
    public String COMPANY_NAME;
    public Double LATITUDE;
    public Double LONGITUDE;
    public Integer RADIUS;
}
