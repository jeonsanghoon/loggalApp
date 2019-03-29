package com.altsoft.model.signage;

//// 모바일 사이니지 리스트 조건
public class MOBILE_SIGNAGE_COND {
    public Double LATITUDE;
    public Double LONGITUDE;
    /** AES256으로 암호화된 위도 **/
    public String SEARCH_LAT;
    /** AES256으로 암호화된 경도 **/
    public String SEARCH_LONG;
    public String SIGN_NAME;
    public int PAGE;
    public int PAGE_COUNT;
}
