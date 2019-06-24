package com.altsoft.model;

import java.io.Serializable;
import java.math.BigInteger;

public class DEVICE_LOCATION  implements Serializable {
    public String SEARCH_CODE;
    public String LOCATION_NAME;
    public Long DEVICE_CODE;
    public String DEVICE_NAME;
    public Integer STATION_CODE;
    public BigInteger SIGN_CODE;
    public String DEVICE_DESC;
    public String ADDRESS1;
    public String ADDRESS2;
    public String ZIP_CODE;
    public Double LATITUDE;
    public Double LONGITUDE;
    public BigInteger AUTH_NUMBER;
    public String COMPANY_NAME;
    public String USER_NAME;
    public String CONTACT_COMPANY_NAME;
    public String CONTACT_NAME;
    /// <summary>
    /// 검색기준 위치로부터 거리
    /// </summary>
    public Double DISTANCE;
    public Boolean BOOKMARK_YN;
    public Boolean FAVORITE_YN;
    public String LOGO_URL;

}
