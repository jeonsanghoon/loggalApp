package com.altsoft.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

public  class AD_SEARCH_COND implements Serializable
{
    /// 카테고리 멀티 선택시 사용, 검색요청시 5자리로 만듦, 코드가 240 일경우 요청코드 : 00240
    @SerializedName("SEARCH_CATEGORY_CODE")
    public String[] SEARCH_CATEGORY_CODE;
    /// 경도(클라이언트단말기 위치정보) >> 값이 있으면 조건에추가 dbo.FN_TO_DISTANCE(@LATITUDE, @LONGITUDE, C.LATITUDE, C.LONGITUDE,'KM') <= @RADIUS
    public Double LATITUDE;
    /// 위도(클라이언트단말기 위치정보) >> 값이 있으면 조건에추가 dbo.FN_TO_DISTANCE(@LATITUDE, @LONGITUDE, C.LATITUDE, C.LONGITUDE,'KM') <= @RADIUS
    public Double LONGITUDE;
    /// AES256으로 암호화된 위도
    public String SEARCH_LAT;
    /// AES256으로 암호화된 경도
    public String SEARCH_LONG;
    /// 반경(km) : 클라이언트 단말기로 부터 검색 반경
    public Integer RADIUS;
    /// 키워드코드 >> 값이 있으면 조건에추가 IN (5,10)
    public String[] SEARCH_KEYWORD_CODE;
    /// 키워드명 >> 값이 있으면 조건에추가 LIKE '%검색조건%'
    public String KEYWORD_NAME;
    /// 광고코드 >> 값이 잇으면 조건에추가
    public BigInteger AD_CODE;
    /// 비콘기기번호
    public String BEACON_DEVICE_NUMBER;
    /// 카테고리와 키워드 연결 테이블의 기본키
    public Integer CK_CODE;
    public Integer COMPANY_CODE;
    public Integer STORE_CODE;
    public Integer MEMBER_CODE;
    public String USER_ID;
    /// 광고숨김여부 (0:보이기:default, 1:숨김)
    public Boolean HIDE;
    /// 페이지당 건수 (기본 20건)
    public Integer PageCount;
    /// 선택된 페이지 기본 1
    public Integer Page;
    public String SORT_ORDER;
    public Integer nCnt;
}
